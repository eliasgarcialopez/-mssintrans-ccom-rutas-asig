package mx.gob.imss.mssintetrans.ccom.rutas.service.impl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.Respuesta;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.SolicitudTrasladoResponse;
import mx.gob.imss.mssintetrans.ccom.rutas.model.ControlRutas;
import mx.gob.imss.mssintetrans.ccom.rutas.model.SolicitudTraslado;
import mx.gob.imss.mssintetrans.ccom.rutas.model.UnidadAdscripcion;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.ControlRutasRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.ModuloAmbulanciaRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.RutasDestinosRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.RutasRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.SolicitudTrasladoRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.TripulacionRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.UnidadAdscripcionRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.VehiculosRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.service.SolicitudTrasladoService;
import mx.gob.imss.mssintetrans.ccom.rutas.util.SolicitudTrasladoMapper;
import mx.gob.imss.mssintetrans.ccom.rutas.util.Utility;


@Service
@AllArgsConstructor
@Slf4j
public class SolicitudTrasladoServiceImpl implements SolicitudTrasladoService {
	@Autowired
	private SolicitudTrasladoRepository solicitudTrasladoRepository;
   @Autowired
   private UnidadAdscripcionRepository uAdscripcionRepository;
	@Override
	public Respuesta<List<SolicitudTrasladoResponse>> consultarSolicitudesByEstatusAndTurno(Integer turno) {
		 Respuesta<List<SolicitudTrasladoResponse>> response = new Respuesta<>();
			
	        try {
	       	 String usuario = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				log.info("usuario {}", usuario);
				
				if (usuario.equals("denegado")) {
				
					response.setError(false);
					response.setCodigo(HttpStatus.UNAUTHORIZED.value());
					response.setMensaje(usuario);
					return response;
				}
	        	 log.info("consultando solicitudes de traslado aceptadas, {}", turno);
	        	 
	        	 
	        
	        	 
	        	  List<SolicitudTraslado> result =new ArrayList<SolicitudTraslado>();
	        	if(turno ==3) {
	        		ArrayList<String> hours= Utility.getHorarioStringByTurno(turno);
	        		log.info("horario, {}", hours.size());
	        		List<SolicitudTraslado> result1= solicitudTrasladoRepository.findSolicitudTrasladoAceptadas(hours.get(0),hours.get(1));
	        		List<SolicitudTraslado> result2=solicitudTrasladoRepository.findSolicitudTrasladoAceptadas(hours.get(2),hours.get(3));
	        	    result.addAll(result1);
	        	    result.addAll(result2);
	        	}else {
	        		ArrayList<String> hours= Utility.getHorarioStringByTurno(turno);
	        		log.info("horario, {}", hours.size());
	           result = solicitudTrasladoRepository.findSolicitudTrasladoAceptadas(hours.get(0),hours.get(1)
	                    ); 
	        
	        	}
	            log.info("solicitudes, {}", result.size());
	            List<SolicitudTrasladoResponse> content = new ArrayList<>();
	            for (SolicitudTraslado solicitudTraslado : result) {
	            	SolicitudTrasladoResponse r =new SolicitudTrasladoResponse();
	            	r.setIdSolicitud(solicitudTraslado.getIdSolicitud());
	            	
	            	log.info("origen id "+solicitudTraslado.getCveOrigen());

	            	Optional<UnidadAdscripcion> opUdes= uAdscripcionRepository.findById(solicitudTraslado.getCveDestino());
	            	if(opUdes.isPresent()) {
	            		log.info("destino id "+opUdes.get().getNomUnidadAdscripcion());
	            		r.setCveDestino(opUdes.get());
	            	}
	            	else log.info("No se encuentra id unidad adscripcion"+ solicitudTraslado.getCveDestino());
	            	
	            	Optional<UnidadAdscripcion> opUOrig= uAdscripcionRepository.findById(solicitudTraslado.getCveOrigen());
	            	if(opUOrig.isPresent()) {
	            		log.info("destino id "+opUOrig.get().getNomUnidadAdscripcion());
	            		r.setCveOrigen(opUOrig.get());
	            	}
	            	else log.info("No se encuentra id unidad adscripcion"+ solicitudTraslado.getCveOrigen());
	            	content.add(r);
					
				}

	            
	            response.setDatos(content);
	            response.setMensaje("Exito");
	            response.setCodigo(HttpStatus.OK.value());
	        } catch (Exception exception) {
	               
	            log.error("Ha ocurrido un error al consultar las solicitudes, {}", ExceptionUtils.getStackTrace(exception));
	            response.setError(true);
	            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
	            response.setMensaje(exception.getMessage());
	        }
	        return response;
	}

}
