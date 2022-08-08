package mx.gob.imss.mssistrans.rutas.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.rutas.dto.OOADResponse;
import mx.gob.imss.mssistrans.rutas.dto.Respuesta;
import mx.gob.imss.mssistrans.rutas.dto.UnidadesAdscripcionResponse;
import mx.gob.imss.mssistrans.rutas.model.CodigoPostal;
import mx.gob.imss.mssistrans.rutas.model.OOAD;
import mx.gob.imss.mssistrans.rutas.model.Rutas;
import mx.gob.imss.mssistrans.rutas.model.UnidadAdscripcion;
import mx.gob.imss.mssistrans.rutas.repository.CodigoPostalRepository;
import mx.gob.imss.mssistrans.rutas.repository.MunicipioRepository;
import mx.gob.imss.mssistrans.rutas.repository.OOADRepository;
import mx.gob.imss.mssistrans.rutas.repository.RutasDestinosRepository;
import mx.gob.imss.mssistrans.rutas.repository.RutasRepository;
import mx.gob.imss.mssistrans.rutas.repository.UnidadAdscripcionRepository;
import mx.gob.imss.mssistrans.rutas.repository.UsuarioRepository;
import mx.gob.imss.mssistrans.rutas.service.UnidadesAdscripcionService;
import mx.gob.imss.mssistrans.rutas.util.OOADObjectMapper;
import mx.gob.imss.mssistrans.rutas.util.UnidadesAdscripcionObjectMapper;

@Service
@AllArgsConstructor
@Slf4j
public class UnidadesAdscripcionServiceImpl implements UnidadesAdscripcionService{
	@Autowired
	private UnidadAdscripcionRepository uAdscripcionRepository;
	@Autowired
	private OOADRepository ooadRepository;
	@Autowired
	private CodigoPostalRepository cPostalRepository;
	@Autowired
	private MunicipioRepository municipioRepository;

	
	@Override
	public Respuesta<List<UnidadesAdscripcionResponse>> consultarUnidadesAdscripcionByIdOOA(Integer idOOAD,
			boolean pernocta) {
		Respuesta<List<UnidadesAdscripcionResponse>> response= new Respuesta();
		try {
			
			String usuario = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			log.info("usuario {}", usuario);
			
			if (usuario.equals("denegado")) {
			
				response.setError(false);
				response.setCodigo(HttpStatus.UNAUTHORIZED.value());
				response.setMensaje(usuario);
				return response;
			}
			log.info("Consultando unidades pernocta?"+pernocta);
			List<UnidadAdscripcion> unidades=null;
			
			
			if(pernocta) 
				unidades=uAdscripcionRepository.findAllUnidadAdscripcionPernoctaActivo(idOOAD);
			else
				unidades=uAdscripcionRepository.findAllUnidadAdscripcionActivo(idOOAD);	
			
			final List<UnidadesAdscripcionResponse> content = unidades
                    .stream()
                    .map(UnidadesAdscripcionObjectMapper.INSTANCE::entityToDto)
                    .collect(Collectors.toList());
		
			 	response.setDatos(content);
	            response.setMensaje("Exito");
	            response.setCodigo(HttpStatus.OK.value());
				
				
			
		} catch (Exception exception) {
			log.error("Ha ocurrido un error al consultar las unidades de adscripcion, error: {}", ExceptionUtils.getStackTrace(exception));
			response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setMensaje(exception.getMessage());
			response.setError(true);
		}
		return response;
	}

	@Override
	public Respuesta<List<UnidadesAdscripcionResponse>> consultarUnidadesAdscripcion() {
		Respuesta<List<UnidadesAdscripcionResponse>> response= new Respuesta();
		try {
			String usuario = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			log.info("usuario {}", usuario);
			
			if (usuario.equals("denegado")) {
			
				response.setError(false);
				response.setCodigo(HttpStatus.UNAUTHORIZED.value());
				response.setMensaje(usuario);
				return response;
			}
			log.info("Consultando todas las unidades ");
			List<UnidadAdscripcion> unidades=uAdscripcionRepository.findAllUnidadAdscripcionActivo();
			
			final List<UnidadesAdscripcionResponse> content = unidades
                    .stream()
                    .map(UnidadesAdscripcionObjectMapper.INSTANCE::entityToDto)
                    .collect(Collectors.toList());
		
			 	response.setDatos(content);
	            response.setMensaje("Exito");
	            response.setCodigo(HttpStatus.OK.value());
				
				
			
		} catch (Exception exception) {
			log.error("Ha ocurrido un error al consultar las unidades de adscripcion, error: {}", ExceptionUtils.getStackTrace(exception));
			response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setMensaje(exception.getMessage());
			response.setError(true);
		}
		return response;
	}

	@Override
	public Respuesta<List<OOADResponse>> consultarOOADS() {
		Respuesta<List<OOADResponse>> response= new Respuesta();
		try {
			String usuario = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			log.info("usuario {}", usuario);
			
			if (usuario.equals("denegado")) {
			
				response.setError(false);
				response.setCodigo(HttpStatus.UNAUTHORIZED.value());
				response.setMensaje(usuario);
				return response;
			}
			log.info("Consultando todas las OOADS ");
			List<OOAD> ooads=ooadRepository.findAllOOADActivo();
			
			final List<OOADResponse> content = ooads
                    .stream()
                    .map(OOADObjectMapper.INSTANCE::entityToDto)
                    .collect(Collectors.toList());
		
			 	response.setDatos(content);
	            response.setMensaje("Exito");
	            response.setCodigo(HttpStatus.OK.value());
				
				
			
		} catch (Exception exception) {
			log.error("Ha ocurrido un error al consultar las OOADS de adscripcion, error: {}", ExceptionUtils.getStackTrace(exception));
			response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setMensaje(exception.getMessage());
			response.setError(true);
		}
		return response;
	}

	

}
