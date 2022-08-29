package mx.gob.imss.mssistrans.ccom.rutas.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import mx.gob.imss.mssistrans.ccom.rutas.util.Utility;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.dto.ControlRutasRequest;
import mx.gob.imss.mssistrans.ccom.rutas.dto.ControlRutasResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.ControlRutasTablaResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.ControlRutasTotalesResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosUsuario;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Respuesta;
import mx.gob.imss.mssistrans.ccom.rutas.dto.TripulacionResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.ControlRutas;
import mx.gob.imss.mssistrans.ccom.rutas.model.ModuloAmbulancia;
import mx.gob.imss.mssistrans.ccom.rutas.model.Ooad;
import mx.gob.imss.mssistrans.ccom.rutas.model.Rutas;
import mx.gob.imss.mssistrans.ccom.rutas.model.RutasDestinos;
import mx.gob.imss.mssistrans.ccom.rutas.model.SolicitudTraslado;
import mx.gob.imss.mssistrans.ccom.rutas.model.Tripulacion;
import mx.gob.imss.mssistrans.ccom.rutas.model.UnidadAdscripcion;
import mx.gob.imss.mssistrans.ccom.rutas.model.Usuario;
import mx.gob.imss.mssistrans.ccom.rutas.model.Vehiculos;
import mx.gob.imss.mssistrans.ccom.rutas.repository.ControlRutasRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.ModuloAmbulanciaRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.RutasDestinosRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.RutasRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.SolicitudTrasladoRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.TripulacionRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.UnidadAdscripcionRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.UsuarioRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.VehiculosRepository;
import mx.gob.imss.mssistrans.ccom.rutas.service.ControlRutasService;


@Service
@AllArgsConstructor
@Slf4j
public class ControlRutasServiceImpl implements ControlRutasService {
	@Autowired
	private ControlRutasRepository controlRutasRepository;
	@Autowired
	private RutasRepository rutasRepository;
	@Autowired
	private RutasDestinosRepository destinosRepository;
	
	@Autowired
	private VehiculosRepository vehiculoRepository;
	
	@Autowired
	private UnidadAdscripcionRepository unidadAdscripcionRepository;
	@Autowired
	private ModuloAmbulanciaRepository moAmbulanciaRepository;
	@Autowired
	private SolicitudTrasladoRepository solRepository;
	@Autowired
	private TripulacionRepository tripulacionRepository;
	@Autowired
	 private UsuarioRepository usuarioRepository;

	@Override
	public Respuesta<Page<ControlRutasTablaResponse>> consultarRutas(Pageable pageable) {
		Respuesta<Page<ControlRutasTablaResponse>> response = new Respuesta<>();
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
			final Page<ControlRutas> result = datosUsuarios.getRol().equals("Administrador") ?  controlRutasRepository.findAll(pageable) : controlRutasRepository.findAll(pageable,datosUsuarios.getIDOOAD());
		
			
			log.info("las rutas, {}", result.getContent().size());

			List<ControlRutasTablaResponse> content = new ArrayList<>();
			for (ControlRutas rutas : result) {
				ControlRutasTablaResponse rutasTabla = new ControlRutasTablaResponse();
				rutasTabla.setEcco(rutas.getIdVehiculo().getCveEcco());
				rutasTabla.setIdControlRuta(rutas.getIdControlRuta());
				rutasTabla.setIdRuta(rutas.getRuta().getIdRuta());
				rutasTabla.setFolioRuta(rutas.getRuta().getNumFolioRuta());
				rutasTabla.setIdSolicitudTraslado(rutas.getIdSolcitud().getIdSolicitud());
				rutasTabla.setModulo(rutas.getModulo().getDesNombre());
				
				Optional<UnidadAdscripcion> origen = unidadAdscripcionRepository
						.findByIdUnidadAdscripcionAndActivoEquals(rutas.getIdSolcitud().getDesAreaOrigen(), true);
				
				if(origen.isPresent())
				rutasTabla.setOrigen(origen.get().getNomUnidadAdscripcion());
				content.add(rutasTabla);
				}

			Page<ControlRutasTablaResponse> objetoMapeado = new PageImpl<>(content, pageable, result.getTotalElements());
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
	public Respuesta<ControlRutasResponse> consultarRutas(Integer idControlRuta) {
		Respuesta<ControlRutasResponse> response = new Respuesta<>();
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
            Gson gson = new Gson();
			DatosUsuario datosUsuarios = gson.fromJson(user, DatosUsuario.class);
		    Integer idOOAD= datosUsuarios.getIDOOAD();
		    
		   Optional<ModuloAmbulancia> opModulo=moAmbulanciaRepository.findByIdOOADAndActivoEquals(idOOAD, true);
		    
		    
		     
             Optional<ControlRutas>  result=controlRutasRepository.findByIdControlRuta(idControlRuta);            
             ControlRutasResponse rutasResponse=new ControlRutasResponse();
             if(result.isPresent()) {
            	 ControlRutas ruta=result.get();
            	 
            	 
            	 Optional<UnidadAdscripcion> origen = unidadAdscripcionRepository
 						.findByIdUnidadAdscripcionAndActivoEquals(ruta.getIdSolcitud().getDesAreaOrigen(), true);
 				if(origen.isPresent())    	 rutasResponse.setOrigen(origen.get());
 				
 				 Optional<UnidadAdscripcion> destino = unidadAdscripcionRepository
  						.findByIdUnidadAdscripcionAndActivoEquals(ruta.getIdSolcitud().getDesAreaDestino(), true);
  				if(destino.isPresent())    	 rutasResponse.setDestino(destino.get());
  				
  				rutasResponse.setFechaRuta(ruta.getFechaInicioAsigna().toString());
  				rutasResponse.setHoraRuta(ruta.getTimInicioAsigna().toString());
  				rutasResponse.setIdSolicitudTraslado(ruta.getIdSolcitud());
  				rutasResponse.setModulo(ruta.getModulo());
  				rutasResponse.setNumFolioTarjeta(ruta.getNumFolioTarjetaCombustible());
  				rutasResponse.setRuta(ruta.getRuta());
  				
  				//
  				
  				if(opModulo.isPresent()) {
  					
  					ModuloAmbulancia moduloAmbulancia=opModulo.get();
  					Integer idZona=moduloAmbulancia.getZona().getIdZona();
  					
  					Integer totalVA=   vehiculoRepository.countTotalVehiculoAsignados(idZona);
  					
  	  				rutasResponse.setTotalVehiculosAsignados(totalVA);
  	  				
  	  				Integer totalVD=   vehiculoRepository.countTotalVehiculoDisponibles(idZona);
					
  	  				rutasResponse.setTotalVehiculosDisponibles(totalVD);
  	  				
  	  				Integer totalMan=   vehiculoRepository.countTotalVehiculoMantenimiento(idZona);
				
	  				rutasResponse.setTotalVehiculosMantenimiento(totalMan);
	  				
	  				
	  				Integer totalSin=   vehiculoRepository.countTotalVehiculoSiniestrados(idZona);
					
	  				rutasResponse.setTotalVehiculosSiniestrados(totalSin);
  				}
  				
  				TripulacionResponse tripRes=new TripulacionResponse();
  				if(ruta.getTripulacion()!=null) {
  				tripRes.setCveMatriculaCamillero1(ruta.getTripulacion().getPersonalCamillero1().getCveMatriculaPersonal());
  				tripRes.setCveMatriculaCamillero2(ruta.getTripulacion().getPersonalCamillero2().getCveMatriculaPersonal());
  				tripRes.setCveMatriculaChofer(ruta.getTripulacion().getPersonalChofer().getCveMatriculaPersonal());
  				
  				Usuario camillero1=usuarioRepository.getUsuario(ruta.getTripulacion().getPersonalCamillero1().getCveMatriculaPersonal());
	        	tripRes.setNombreCamillero1(camillero1.getNOM_USUARIO()+ " "+camillero1.getNOM_APELLIDO_PATERNO() + " "+ camillero1.getNOM_APELLIDO_MATERNO());

	        	Usuario  camillero2=usuarioRepository.getUsuario(ruta.getTripulacion().getPersonalCamillero2().getCveMatriculaPersonal());
	        	tripRes.setNombreCamillero2(camillero2.getNOM_USUARIO()+ " "+camillero2.getNOM_APELLIDO_PATERNO() + " "+ camillero2.getNOM_APELLIDO_MATERNO());
	        	
	        	Usuario  chofer=usuarioRepository.getUsuario(ruta.getTripulacion().getPersonalChofer().getCveMatriculaPersonal());
	        	tripRes.setNombreChofer(chofer.getNOM_USUARIO()+ " "+chofer.getNOM_APELLIDO_PATERNO() + " "+ chofer.getNOM_APELLIDO_MATERNO());
	        	
	        	
  				tripRes.setIdTripulacion(ruta.getTripulacion().getIdTripulacion());
  				tripRes.setIdVehiculo(ruta.getTripulacion().getIdVehiculo());
  				tripRes.setFecFecha(ruta.getTripulacion().getFecFecha().toString());;
  				rutasResponse.setTripulacion(tripRes);
  				}
  				//06.01 a 14:00, vespertino horario del turno 14.01 a 19:00,nocturno o especial horario del turno 19.01 a 06:00 
  				if(ruta.getIdSolcitud()!=null && ruta.getIdSolcitud().getTimSolicitud()!=null ) {
  					//String turno=Utility.getTurnoByHr(ruta.getIdSolcitud().getTimSolicitud());
  	  				rutasResponse.setTurno(ruta.getIdSolcitud().getTimSolicitud());
  				}
  				
  				rutasResponse.setVehiculo(ruta.getIdVehiculo());
            	 
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
            log.info("Ha ocurrido un error al consultar la ruta con Id {}, error: {}", idControlRuta, ExceptionUtils.getStackTrace(exception));
            response.setError(true);
            response.setMensaje(exception.getMessage());
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
	}
    @Transactional

	public Respuesta<Integer> crearRuta(ControlRutasRequest rutas) {
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
			//Creando la ruta
			
			Rutas ruta=new Rutas();
			ruta.setActivo(true);
			ruta.setCveMatricula(rutas.getCveMatricula());			
			ruta.setFechaAlta(LocalDate.now());
			ruta.setIndiceSistema(true);
			ArrayList<String> horas= Utility.getHorarioStringByTurno(rutas.getTurno());
			if(horas.size()==2) {
				ruta.setTimHorarioInicial(horas.get(0));
				ruta.setTimHorarioFinal(horas.get(1));
			}
			
			
			//Obtenemos solicitud
			
		Optional<SolicitudTraslado>	solicitud=   solRepository.findById(rutas.getIdSolicitudTraslado());
		if(solicitud.isPresent()) {
			SolicitudTraslado solicitudTraslado=solicitud.get();
			ruta.setIdOrigen(solicitudTraslado.getCveOrigen());
			ruta.setIdUnidadDestino(solicitudTraslado.getCveDestino());
			ruta.setIdUnidadSolcitante(solicitudTraslado.getIdUnidadSolicitante());
			
			log.info("Actualizando estatus a asignada ");
			solicitudTraslado.setDesEstatusSolicitud("4");
			solRepository.save(solicitudTraslado);
		} else log.info("Solicitud no encontrado"+rutas.getIdSolicitudTraslado());
		//pendiente
		//ruta.setNumFolioRuta(user)
		
		RutasDestinos destinos=new RutasDestinos();
		destinos.setActivo(true);
		destinos.setIdUnidadDestino(ruta.getIdUnidadDestino());
		destinos.setIndiceSistema(true);
	
		if(horas.size()==2) {
			destinos.setTimHoraInicio(horas.get(0));
			destinos.setTimHoraFin(horas.get(1));
		}
	
		destinos.setRuta(ruta);
		
		ruta.getDestinos().add(destinos);
		
			rutasRepository.save(ruta);
			
			
			Gson gson = new Gson();
			DatosUsuario datosUsuarios = gson.fromJson(user, DatosUsuario.class);
			Optional<UnidadAdscripcion> unidadAds=unidadAdscripcionRepository.findByIdUnidadAdscripcionAndActivoEquals(datosUsuarios.getIdUnidadAdscripcion(), true);
			
			Ooad ooad= unidadAds.get().getOoad();
			//Asignadmos el OOAD ala que pertence el ususario
			
			ruta.setIdOOADFiltro(datosUsuarios.getIDOOAD());
			
			//Se obtiene el folio
			
			
			Utility utility = new Utility();
			//pendiente definir las siglas ooad
			String folio=utility.generateFolio("", ruta.getIdRuta());
			//Actualizamos folio
		
			ruta.setNumFolioRuta(folio);
			rutasRepository.save(ruta);
			// Generamos registro de control de ruta
			
			ControlRutas controlRutas=new ControlRutas();
			controlRutas.setActivo(true);
			controlRutas.setCveMatricula(ruta.getCveMatricula());
			controlRutas.setFechaAlta(LocalDate.now());
			
			 if(solicitud.isPresent()) 	controlRutas.setIdSolcitud(solicitud.get());
			 
			 else log.info("Solicitud no encontrado");
			
			 
			 Optional<Vehiculos> veOp=   vehiculoRepository.findById(rutas.getIdVehiculo());
			 if(veOp.isPresent()) {
				 controlRutas.setIdVehiculo(veOp.get());
				 Vehiculos ve=veOp.get();
				 
				 log.info("ajustando vehiculo a asignado");
				 ve.setDesEstatusVehiculo("9");
				 vehiculoRepository.save(ve);
				 log.info(" vehiculo a asignado");
				 
			 }
			 else log.info("Vehiculo no encontrado"+rutas.getIdVehiculo());
			 //pendiente ver el catalog de status asignado
			 controlRutas.setDesEstatusAsigna("1");
			 controlRutas.setIndiceSistema(true);
			 
			 Optional<ModuloAmbulancia> moduloOp= moAmbulanciaRepository.findByIdOOADAndActivoEquals(rutas.getIdModulo(), true);
			 if(moduloOp.isPresent())
			 controlRutas.setModulo(moduloOp.get());
			 else log.info("modulo no encontrado"+rutas.getIdModulo());
			 log.info("folio.."+rutas.getNumFolioTarjeta());
			 controlRutas.setNumFolioTarjetaCombustible(""+rutas.getNumFolioTarjeta());
			 controlRutas.setRuta(ruta);
			 controlRutas.setFechaInicioAsigna(LocalDate.parse(rutas.getFechaRuta()));;
			 controlRutas.setTimInicioAsigna(LocalTime.parse(rutas.getHoraRuta()));
			 
			 Optional<Tripulacion> tripOp=tripulacionRepository.findById(rutas.getIdTripulacion());
			 if(tripOp.isPresent()) controlRutas.setTripulacion(tripOp.get());
			 else log.info("Tripulacion no encontrado"+rutas.getIdTripulacion());
			 
			//Guardarmos finalmente el control
			 
			 controlRutasRepository.save(controlRutas);
			
			 log.info("Ruta asignada..");
			// As 
			 
			
			response.setCodigo(HttpStatus.OK.value());
			response.setMensaje("Exito");
			response.setError(false);
			response.setDatos(controlRutas.getIdControlRuta());
		} catch (Exception exception) {
			exception.printStackTrace();
			log.error("Ha ocurrido un error al guardar la ruta, error: {}", ExceptionUtils.getStackTrace(exception));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setMensaje(exception.getMessage());
			response.setError(true);
		}
		return response;
	}
@Transactional
	public Respuesta<Integer> editarRuta(Integer idControlRuta, ControlRutasRequest rutaDTO) {
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
			Optional<ControlRutas> crutaOp=   controlRutasRepository.findByIdControlRuta(idControlRuta);
			
			if(crutaOp.isPresent()) {
			ControlRutas contRuta= crutaOp.get();
			
			Rutas ruta=  contRuta.getRuta();
			
			ruta.setActivo(true);
			ruta.setCveMatricula(rutaDTO.getCveMatricula());			
			ruta.setFechaActualizacion(LocalDate.now());
			ruta.setIndiceSistema(true);
			ArrayList<String> horas= Utility.getHorarioStringByTurno(rutaDTO.getTurno());
			if(horas.size()==2) {
				ruta.setTimHorarioInicial(horas.get(0));
				ruta.setTimHorarioFinal(horas.get(1));
			}
			
			Optional<SolicitudTraslado>	solicitud=   solRepository.findById(rutaDTO.getIdSolicitudTraslado());
			if(solicitud.isPresent()) {
				SolicitudTraslado solicitudTraslado=solicitud.get();
				ruta.setIdOrigen(solicitudTraslado.getCveOrigen());
				ruta.setIdUnidadDestino(solicitudTraslado.getCveDestino());
				ruta.setIdUnidadSolcitante(solicitudTraslado.getIdUnidadSolicitante());
				contRuta.setIdSolcitud(solicitudTraslado);
				
				//Ponemos esta solicitud en asiganda
				
				solicitudTraslado.setDesEstatusSolicitud("4");
				solRepository.save(solicitudTraslado);
				
				
			}
			//borrando  destinos existentes				
			for (RutasDestinos destino : ruta.getDestinos()) {
				ruta.setIdRuta(ruta.getIdRuta());
				destino.setRuta(ruta);
				destinosRepository.delete(destino);
				
			}
			log.info("Rutas borradas");
		
			RutasDestinos destinos=new RutasDestinos();
			destinos.setActivo(true);
			destinos.setIdUnidadDestino(ruta.getIdUnidadDestino());
			;
			destinos.setIndiceSistema(true);
			if(horas.size()==2) {
				destinos.setTimHoraInicio(horas.get(0));
				destinos.setTimHoraFin(horas.get(1));
			}
			destinos.setRuta(ruta);
			//destinosRepository.save(destinos);
			log.info("destinos agregados");
			ruta.getDestinos().clear();
			ruta.getDestinos().add(destinos);
			ruta.setFechaActualizacion(LocalDate.now());
			rutasRepository.save(ruta);
			log.info("Ruta Actualizada");
			
            contRuta.setRuta(ruta);
			 Optional<Vehiculos> veOp=   vehiculoRepository.findById(rutaDTO.getIdVehiculo());
			 if(veOp.isPresent()) { 
				 
				 Vehiculos vehiculo=veOp.get();
				 contRuta.setIdVehiculo(vehiculo);
				 vehiculo.setDesEstatusVehiculo("9");//en transito.
				 vehiculoRepository.save(vehiculo);
				 log.info("Vehiculo Asignado"+ vehiculo);
				 
				
			 
			 }
			 
			 contRuta.setCveMatricula(rutaDTO.getCveMatricula());
			 contRuta.setFechaActualizacion(LocalDate.now());
			 contRuta.setNumFolioTarjetaCombustible(""+rutaDTO.getNumFolioTarjeta());
			 contRuta.setTimInicioAsigna(LocalTime.parse(rutaDTO.getHoraRuta()));
			 contRuta.setFechaInicioAsigna(LocalDate.parse(rutaDTO.getFechaRuta()));
			 
			 
			 Optional<ModuloAmbulancia> moduloOp= moAmbulanciaRepository.findByIdOOADAndActivoEquals(rutaDTO.getIdModulo(), true);
			 if(moduloOp.isPresent())
				 contRuta.setModulo(moduloOp.get());
			
			 Optional<Tripulacion> tripOp=tripulacionRepository.findById(rutaDTO.getIdTripulacion());
			 if(tripOp.isPresent()) contRuta.setTripulacion(tripOp.get());
			
			 
			 
			      controlRutasRepository.save(contRuta);
			 log.info("Asignacion Actualizada");
					
			response.setCodigo(HttpStatus.OK.value());
			response.setMensaje("Exito");
			response.setError(false);
			response.setDatos(contRuta.getIdControlRuta());
			
				
			}else {
				response.setDatos(null);
				response.setError(false);
				response.setMensaje("Exito");
				response.setCodigo(HttpStatus.OK.value());
			}
			

		} catch (Exception exception) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("Ha ocurrido un error al actualizar la ruta, error: {}", ExceptionUtils.getStackTrace(exception));
			response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setMensaje(exception.getMessage());
			response.setError(true);
		}
		return response;
	}
@Transactional
	
	public Respuesta<Integer> eliminarRutas(Integer idControlRuta) {
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
			log.info("Eliminando la asignacion ruta");
			ControlRutas rutas = controlRutasRepository.findById(idControlRuta).orElseThrow(Exception::new);
			rutas.setFechaBaja(LocalDate.now());
			rutas.setActivo(false);

			Gson gson = new Gson();
			DatosUsuario datosUsuarios = gson.fromJson(user, DatosUsuario.class);
			rutas.setCveMatricula(datosUsuarios.getMatricula());
			
			
			controlRutasRepository.save(rutas);
			
			log.info("Eliminando la  ruta");
			Rutas ruta = rutasRepository.findById(rutas.getRuta().getIdRuta()).orElseThrow(Exception::new);
			ruta.setFechaBaja(LocalDate.now());
			ruta.setActivo(false);	
			ruta.setCveMatricula(datosUsuarios.getMatricula());
			
			
			rutasRepository.save(ruta);
			
			response.setCodigo(HttpStatus.OK.value());
			response.setMensaje("Exito");
			response.setError(false);
			response.setDatos(rutas.getIdControlRuta());
		} catch (Exception exception) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("Ha ocurrido un error al eliminar la ruta, error: {}", ExceptionUtils.getStackTrace(exception));
			response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setMensaje(exception.getMessage());
			response.setError(true);
		}
		return response;
	}

@Override
public Respuesta<ControlRutasTotalesResponse> consultarTotalesVehiculos() {
	Respuesta<ControlRutasTotalesResponse> response = new Respuesta<>();
    try {
    	String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		log.info("usuario {}", user);
		if (user.equals("denegado")) {
			
			response.setError(false);
			response.setCodigo(HttpStatus.UNAUTHORIZED.value());
			response.setMensaje(user);
			return response;
		}
        log.info("Consultando el modulo por idOOAD");
        Gson gson = new Gson();
		DatosUsuario datosUsuarios = gson.fromJson(user, DatosUsuario.class);
	    Integer idOOAD= datosUsuarios.getIDOOAD();
	    ControlRutasTotalesResponse rutasResponse=new ControlRutasTotalesResponse();
	   Optional<ModuloAmbulancia> opModulo=moAmbulanciaRepository.findByIdOOADAndActivoEquals(idOOAD, true);
		if(opModulo.isPresent()) {
			
			ModuloAmbulancia moduloAmbulancia=opModulo.get();
			Integer idZona=moduloAmbulancia.getZona().getIdZona();
			
			Integer totalVA=   vehiculoRepository.countTotalVehiculoAsignados(idZona);
			
				rutasResponse.setTotalVehiculosAsignados(totalVA);
				
				Integer totalVD=   vehiculoRepository.countTotalVehiculoDisponibles(idZona);
		
				rutasResponse.setTotalVehiculosDisponibles(totalVD);
				
				Integer totalMan=   vehiculoRepository.countTotalVehiculoMantenimiento(idZona);
	
			rutasResponse.setTotalVehiculosMantenimiento(totalMan);
			
			
			Integer totalSin=   vehiculoRepository.countTotalVehiculoSiniestrados(idZona);
		
			rutasResponse.setTotalVehiculosSiniestrados(totalSin);

			   
        	response.setDatos(rutasResponse);
	            response.setError(false);
	            response.setMensaje("Exito");
	            response.setCodigo(HttpStatus.OK.value());
		}
	    else {
        	 response.setDatos(null);
 	            response.setError(false);
 	            response.setMensaje("Exito");
 	            response.setCodigo(HttpStatus.OK.value()); 
         }
       
    } catch (Exception exception) {
        log.info("Ha ocurrido un error al consultar totales de vehiculos error: "+ExceptionUtils.getStackTrace(exception));
        response.setError(true);
        response.setMensaje(exception.getMessage());
        response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
    return response;
}

}
