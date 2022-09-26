package mx.gob.imss.mssistrans.ccom.rutas.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Respuesta;
import mx.gob.imss.mssistrans.ccom.rutas.dto.SolicitudTrasladoResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.SolicitudTraslado;
import mx.gob.imss.mssistrans.ccom.rutas.model.UnidadAdscripcion;
import mx.gob.imss.mssistrans.ccom.rutas.repository.SolicitudTrasladoRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.UnidadAdscripcionRepository;
import mx.gob.imss.mssistrans.ccom.rutas.service.SolicitudTrasladoService;
import mx.gob.imss.mssistrans.ccom.rutas.util.Utility;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


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


				List<SolicitudTraslado> result = new ArrayList<SolicitudTraslado>();
				final LocalDate fechaActual = LocalDate.now();
				if (turno == 3) {
					ArrayList<String> hours = Utility.getHorarioStringByTurno(turno);
					log.info("horario, {}", hours.size());

					List<SolicitudTraslado> result1 = solicitudTrasladoRepository.findSolicitudTrasladoAceptadas(hours.get(0), hours.get(1), fechaActual);
					List<SolicitudTraslado> result2 = solicitudTrasladoRepository.findSolicitudTrasladoAceptadas(hours.get(2), hours.get(3), fechaActual);
					result.addAll(result1);
					result.addAll(result2);
				} else {
					ArrayList<String> hours = Utility.getHorarioStringByTurno(turno);
					log.info("horario, {}", hours.size());
					result = solicitudTrasladoRepository.findSolicitudTrasladoAceptadas(hours.get(0), hours.get(1), fechaActual);

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
