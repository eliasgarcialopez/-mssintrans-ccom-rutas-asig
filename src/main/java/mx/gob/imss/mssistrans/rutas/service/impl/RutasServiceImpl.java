package mx.gob.imss.mssistrans.rutas.service.impl;

import static java.util.Collections.emptyList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.rutas.dto.DatosUsuario;
import mx.gob.imss.mssistrans.rutas.dto.Respuesta;
import mx.gob.imss.mssistrans.rutas.dto.RutasRequest;
import mx.gob.imss.mssistrans.rutas.dto.RutasResponse;
import mx.gob.imss.mssistrans.rutas.dto.RutasTablaResponse;
import mx.gob.imss.mssistrans.rutas.model.Asignaciones;
import mx.gob.imss.mssistrans.rutas.model.CodigoPostal;
import mx.gob.imss.mssistrans.rutas.model.OOAD;
import mx.gob.imss.mssistrans.rutas.model.Rutas;
import mx.gob.imss.mssistrans.rutas.model.RutasDestinos;
import mx.gob.imss.mssistrans.rutas.model.UnidadAdscripcion;
import mx.gob.imss.mssistrans.rutas.model.Usuario;
import mx.gob.imss.mssistrans.rutas.repository.AsignacionesRepository;
import mx.gob.imss.mssistrans.rutas.repository.CodigoPostalRepository;
import mx.gob.imss.mssistrans.rutas.repository.OOADRepository;
import mx.gob.imss.mssistrans.rutas.repository.RutasDestinosRepository;
import mx.gob.imss.mssistrans.rutas.repository.RutasRepository;
import mx.gob.imss.mssistrans.rutas.repository.UnidadAdscripcionRepository;
import mx.gob.imss.mssistrans.rutas.repository.UsuarioRepository;
import mx.gob.imss.mssistrans.rutas.service.RutasService;
import mx.gob.imss.mssistrans.rutas.util.RutasObjectMapper;
import mx.gob.imss.mssistrans.rutas.util.Utility;

@Service
@AllArgsConstructor
@Slf4j
public class RutasServiceImpl implements RutasService {
	@Autowired
	private RutasRepository rutasRepository;
	@Autowired
	private UnidadAdscripcionRepository unAdscripcionRepository;
	@Autowired
	private OOADRepository ooadRepository;
	@Autowired
	private CodigoPostalRepository coPostalRepository;
	@Autowired
	private RutasDestinosRepository ruDestinosRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private AsignacionesRepository asignacionesRepository;
	
	@Override
	public Respuesta<Page<RutasTablaResponse>> consultarRutas(Pageable pageable) {
		Respuesta<Page<RutasTablaResponse>> response = new Respuesta<>();
		try {
			String usuario = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			log.info("usuario {}", usuario);
			
			if (usuario.equals("denegado")) {
			
				response.setError(false);
				response.setCodigo(HttpStatus.UNAUTHORIZED.value());
				response.setMensaje(usuario);
				return response;
			}
			log.info("las rutas page number, {}", pageable.getPageNumber());

			log.info("los rutas, page size {}", pageable.getPageSize());
			Gson gson = new Gson();
			DatosUsuario datosUsuarios = gson.fromJson(usuario, DatosUsuario.class);
			//Validar si es adminsitrador...
			final Page<Rutas> result = datosUsuarios.getRol().equals("Administrador") ?  rutasRepository.findAll(pageable) : rutasRepository.findAll(pageable,datosUsuarios.getIDOOAD());
		
			
			log.info("las rutas, {}", result.getContent().size());

			List<RutasTablaResponse> content = new ArrayList<>();
			for (Rutas rutas : result) {
				RutasTablaResponse rutasTabla = new RutasTablaResponse();
				rutasTabla.setIdRuta(rutas.getIdRuta());
				rutasTabla.setNumFolioRuta(rutas.getNumFolioRuta());
				
				rutasTabla.setDesServicio(rutas.getDesServicio());
				Optional<UnidadAdscripcion> origen = unAdscripcionRepository
						.findByIdUnidadAdscripcionAndActivoEquals(rutas.getIdOrigen(), true);
				StringBuilder destinosStr = new StringBuilder();
				for (RutasDestinos destinos : rutas.getDestinos()) {
					Optional<UnidadAdscripcion> destinoU = unAdscripcionRepository
							.findByIdUnidadAdscripcionAndActivoEquals(destinos.getIdUnidadDestino(), true);

					if (destinoU.isPresent()) {
						destinosStr.append(destinoU.get().getNomUnidadAdscripcion() + ",");
					}

				}
				rutasTabla.setDestino(destinosStr.substring(0, destinosStr.length() - 2));

				if (origen.isPresent()) {
					rutasTabla.setUnidadSolicitante(origen.get().getNomUnidadAdscripcion());
				}
				content.add(rutasTabla);
			}

			Page<RutasTablaResponse> objetoMapeado = new PageImpl<>(content, pageable, result.getTotalElements());
			response.setDatos(objetoMapeado);
			response.setMensaje("Exito");
			response.setCodigo(HttpStatus.OK.value());
		} catch (Exception exception) {

			log.error("Ha ocurrido un error al consultar las rutas, {}", ExceptionUtils.getStackTrace(exception));
			response.setError(true);
			response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setMensaje(exception.getMessage());
		}
		return response;
	}

	@Override
	public Respuesta<Page<RutasTablaResponse>> consultarRutasPorFiltro(String folioRuta, Pageable pageable) {
		Respuesta<Page<RutasTablaResponse>> response = new Respuesta<>();
		try {
			String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			log.info("usuario {}", user);
			if (user.equals("denegado")) {
				
				response.setError(false);
				response.setCodigo(HttpStatus.UNAUTHORIZED.value());
				response.setMensaje(user);
				return response;
			}
			Gson gson = new Gson();
			DatosUsuario datosUsuarios = gson.fromJson(user, DatosUsuario.class);
			final Page<Rutas> result = datosUsuarios.getRol().equals("Administrador") ?  rutasRepository.findAll(folioRuta, pageable) : rutasRepository.findAll(folioRuta,datosUsuarios.getIDOOAD(), pageable);
			
			
			log.info("las rutas, {}", result.getContent().size());
			List<RutasTablaResponse> content = new ArrayList<>();
			for (Rutas rutas : result) {
				RutasTablaResponse rutasTabla = new RutasTablaResponse();
				rutasTabla.setIdRuta(rutas.getIdRuta());
				rutasTabla.setDesServicio(rutas.getDesServicio());
				rutasTabla.setNumFolioRuta(rutas.getNumFolioRuta());
				Optional<UnidadAdscripcion> origen = unAdscripcionRepository
						.findByIdUnidadAdscripcionAndActivoEquals(rutas.getIdOrigen(), true);
				
				
				
				 StringBuilder destinosStr=new StringBuilder();
	            	for (RutasDestinos destinos : rutas.getDestinos()) {
	            		  Optional<UnidadAdscripcion> destinoU=   unAdscripcionRepository.findByIdUnidadAdscripcionAndActivoEquals(destinos.getIdUnidadDestino(), true);
	  	                
	  	                if(destinoU.isPresent()) {
	  	                  destinosStr.append(destinoU.get().getNomUnidadAdscripcion()+",");
	  	                }
	                	
						
					}
	            	rutasTabla.setDestino(destinosStr.substring(0, destinosStr.length()-2));
	            	
				
				if (origen.isPresent()) {
					rutasTabla.setUnidadSolicitante(origen.get().getNomUnidadAdscripcion());
				}
				content.add(rutasTabla);
			}
			Page<RutasTablaResponse> objetoMapeado = new PageImpl<>(content, pageable, result.getTotalElements());
			response.setDatos(objetoMapeado);
			response.setMensaje("Exito");
			response.setCodigo(HttpStatus.OK.value());
		} catch (Exception exception) {
			log.error("Ha ocurrido un error al consultar las rutas, {}", ExceptionUtils.getStackTrace(exception));
			response.setError(true);
			response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setMensaje(exception.getMessage());
		}
		return response;
	}

	@Override
	public Respuesta<RutasResponse> consultarRutas(Integer idRutas) {
		 Respuesta<RutasResponse> response = new Respuesta<>();
	        try {
	        	String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				log.info("usuario {}", user);
				if (user.equals("denegado")) {
					
					response.setError(false);
					response.setCodigo(HttpStatus.UNAUTHORIZED.value());
					response.setMensaje(user);
					return response;
				}
	            log.info("Consultando la ruta");
	             Optional<Rutas>  result=rutasRepository.findByIdRutaAndActivoEquals(idRutas, true);            
	             RutasResponse rutasResponse=new RutasResponse();
	             if(result.isPresent()) {
	            	 Rutas ruta=result.get();
	            	 rutasResponse=RutasObjectMapper.INSTANCE.entityToDTO(ruta);
	            	
	            	  for (RutasDestinos destinos : ruta.getDestinos()) {
	            		  Optional<UnidadAdscripcion> destinoU=   unAdscripcionRepository.findByIdUnidadAdscripcionAndActivoEquals(destinos.getIdUnidadDestino(), true); 	                
		  	                if(destinoU.isPresent()) {
		  	                 rutasResponse.getDestinos().add(destinoU.get());
		  	                }						
					}
	            	 if(ruta.getIdOOAD()!=null) {
	            		Optional<OOAD> ooAD=  ooadRepository.findByIdOOADAndActivoEquals(ruta.getIdOOAD(), true);
	            		 if(ooAD.isPresent())
	            		 rutasResponse.setIdOOAD(ooAD.get());
	            	 
	            	 }
	            	 rutasResponse.setDesCodigoPostal(ruta.getDesCodigoPostal());
	            	 rutasResponse.setDesEntidad(ruta.getDesEntidad());	            	 
	            	 rutasResponse.setDesMunicipio(ruta.getDesMunicipio());
	            	 
	           
	            	 if(ruta.getIdUnidadDestino()!=null) {
	            		 Optional<UnidadAdscripcion> unidadDestinoOp=   unAdscripcionRepository.findByIdUnidadAdscripcionAndActivoEquals(ruta.getIdUnidadDestino(), true); 	                
		  	             if(unidadDestinoOp.isPresent()) rutasResponse.setUnidadDestino(unidadDestinoOp.get());
	            	 }
	            	
	            	 Optional<UnidadAdscripcion> unidadOrigen=   unAdscripcionRepository.findByIdUnidadAdscripcionAndActivoEquals(ruta.getIdOrigen(), true); 	                
		  	         if(unidadOrigen.isPresent())      	 rutasResponse.setUnidadOrigen(unidadOrigen.get());
	            	
	            	            	 
	            	 Optional<UnidadAdscripcion> unidadSolicitante=   unAdscripcionRepository.findByIdUnidadAdscripcionAndActivoEquals(ruta.getIdUnidadSolcitante(), true); 	                
	            	 if(unidadSolicitante.isPresent())
	            	 rutasResponse.setUnidadSolicitante(unidadSolicitante.get());
	            	 
	            	response.setDatos(rutasResponse);
	 	            response.setError(false);
	 	            response.setMensaje("Exito");
	 	            response.setCodigo(HttpStatus.OK.value());
	             }else {
	            	 response.setDatos(null);
		 	            response.setError(false);
		 	            response.setMensaje("Exito");
		 	            response.setCodigo(HttpStatus.OK.value()); 
	             }
	           
	        } catch (Exception exception) {
	            log.info("Ha ocurrido un error al consultar la ruta con Id {}, error: {}", idRutas, ExceptionUtils.getStackTrace(exception));
	            response.setError(true);
	            response.setMensaje(exception.getMessage());
	            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
	        }
	        return response;
	}

	@Override
	public Respuesta<Integer> crearRuta(RutasRequest rutas) {
		Respuesta<Integer> response = new Respuesta<>();
		try {// Pendiente crear el campo foliador....
			String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			log.info("usuario {}", user);
			if (user.equals("denegado")) {
				
				response.setError(false);
				response.setCodigo(HttpStatus.UNAUTHORIZED.value());
				response.setMensaje(user);
				return response;
			}
			Rutas ruta = RutasObjectMapper.INSTANCE.dtoToEntity(rutas);
			List<RutasDestinos> destinos = ruta.getDestinos();
			for (RutasDestinos rutasDestinos : destinos) {
				rutasDestinos.setRuta(ruta);
			}
			ruta.setFechaAlta(LocalDate.now());
			ruta.setActivo(true);
			ruta.setIndiceSistema(false);
			rutasRepository.save(ruta);
			
			
			Gson gson = new Gson();
			DatosUsuario datosUsuarios = gson.fromJson(user, DatosUsuario.class);
			Optional<UnidadAdscripcion> unidadAds=unAdscripcionRepository.findByIdUnidadAdscripcionAndActivoEquals(datosUsuarios.getIdUnidadAdscripcion(), true);
			
			OOAD ooad= unidadAds.get().getOoad();
			//Asignadmos el OOAD ala que pertence el ususario
			Integer idOoad=ooad.getIdOOAD();
			ruta.setIdOOADFiltro(idOoad);
			
			String clavePresupuestal=unidadAds.get().getNumCC();
			//Se obtiene el folio
			if(clavePresupuestal==null)clavePresupuestal="";
			
			Utility utility = new Utility();
			String folio=utility.generateFolio(ruta.getDesServicio(), ruta.getIdRuta(), clavePresupuestal);
			//Actualizamos folio
		
			ruta.setNumFolioRuta(folio);
			rutasRepository.save(ruta);
			
			response.setCodigo(HttpStatus.OK.value());
			response.setMensaje("Exito");
			response.setError(false);
			response.setDatos(ruta.getIdRuta());
		} catch (Exception exception) {
			exception.printStackTrace();
			log.error("Ha ocurrido un error al guardar la ruta, error: {}", ExceptionUtils.getStackTrace(exception));
			response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setMensaje(exception.getMessage());
			response.setError(true);
		}
		return response;
	}
    @Transactional
	@Override
	public Respuesta<Integer> editarRuta(Integer idRuta, RutasRequest rutaDTO) {
		Respuesta<Integer> response = new Respuesta<>();
		
		try {
			
			String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			log.info("usuario {}", user);
			if (user.equals("denegado")) {
				
				response.setError(false);
				response.setCodigo(HttpStatus.UNAUTHORIZED.value());
				response.setMensaje(user);
				return response;
			}
			log.info("Actualizando ruta");
			Optional<Rutas> rutaOp = rutasRepository.findByIdRutaAndActivoEquals(idRuta, true);
			

			if (rutaOp.isPresent()) {

							
				Rutas rutas = rutaOp.get();
				
			Optional<Asignaciones> asigOpt=	asignacionesRepository.findByIdAsignacionAndActivoEquals(idRuta, true);
		     if(asigOpt.isPresent()) {
		    		response.setDatos(null);
					response.setError(true);
					response.setMensaje("Ruta Asignada");
					response.setCodigo(HttpStatus.OK.value());
					return response;
		    	 
		     }
			
			//borrando  destinos existentes				
				for (RutasDestinos destino : rutas.getDestinos()) {
					rutas.setIdRuta(idRuta);
					destino.setRuta(rutas);
					ruDestinosRepository.delete(destino);
					
				}
				log.info("Rutas borradas");
				RutasObjectMapper.INSTANCE.updateFromDto(rutaDTO, rutas);
				
				for (RutasDestinos destino : rutas.getDestinos()) {
						destino.setRuta(rutas);	
				}
						
				Gson gson = new Gson();
				DatosUsuario datosUsuarios = gson.fromJson(user, DatosUsuario.class);
				
				rutas.setIdOOADFiltro(datosUsuarios.getIDOOAD());
				
				rutas.setFechaActualizacion(LocalDate.now());
				rutasRepository.save(rutas);
				log.info("Actualizada");
				response.setCodigo(HttpStatus.OK.value());
				response.setMensaje("Exito");
				response.setError(false);
				response.setDatos(rutas.getIdRuta());

			} else {
				response.setDatos(null);
				response.setError(false);
				response.setMensaje("Exito");
				response.setCodigo(HttpStatus.OK.value());
			}

		} catch (Exception exception) {

			log.error("Ha ocurrido un error al actualizar la ruta, error: {}", ExceptionUtils.getStackTrace(exception));
			response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setMensaje(exception.getMessage());
			response.setError(true);
		}
		return response;
	}

	@Override
	public Respuesta<Integer> eliminarRutas(Integer idRuta) {
		Respuesta<Integer> response = new Respuesta<>();
		try {
			String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			log.info("usuario {}", user);
			if (user.equals("denegado")) {
				
				response.setError(false);
				response.setCodigo(HttpStatus.UNAUTHORIZED.value());
				response.setMensaje(user);
				return response;
			}
			log.info("Eliminando la ruta");
			Rutas rutas = rutasRepository.findById(idRuta).orElseThrow(Exception::new);
			rutas.setFechaBaja(LocalDate.now());
			rutas.setActivo(false);

			Gson gson = new Gson();
			DatosUsuario datosUsuarios = gson.fromJson(user, DatosUsuario.class);
			rutas.setCveMatricula(datosUsuarios.getMatricula());
			
			
			rutasRepository.save(rutas);
			response.setCodigo(HttpStatus.OK.value());
			response.setMensaje("Exito");
			response.setError(false);
			response.setDatos(rutas.getIdRuta());
		} catch (Exception exception) {
			log.error("Ha ocurrido un error al eliminar la ruta, error: {}", ExceptionUtils.getStackTrace(exception));
			response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setMensaje(exception.getMessage());
			response.setError(true);
		}
		return response;
	}

}
