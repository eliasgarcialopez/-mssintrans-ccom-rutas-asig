package mx.gob.imss.mssistrans.ccom.rutas.service.impl;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosUsuario;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;
import mx.gob.imss.mssistrans.ccom.rutas.dto.SolicitudTrasladoResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.SolicitudTraslado;
import mx.gob.imss.mssistrans.ccom.rutas.model.UnidadAdscripcion;
import mx.gob.imss.mssistrans.ccom.rutas.repository.SolicitudTrasladoRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.UnidadAdscripcionRepository;
import mx.gob.imss.mssistrans.ccom.rutas.service.SolicitudTrasladoService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
	public Response<List<SolicitudTrasladoResponse>> consultarSolicitudesByEstatus() {
		 Response<List<SolicitudTrasladoResponse>> response = new Response<>();

	        try {
	       	 String usuario = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				log.info("usuario {}", usuario);
				
				if (usuario.equals("denegado")) {
				
					response.setError(false);
					response.setCodigo(HttpStatus.UNAUTHORIZED.value());
					response.setMensaje(usuario);
					return response;
				}
				Gson gson = new Gson();
				DatosUsuario datosUsuario = gson.fromJson(usuario, DatosUsuario.class);
	        	 log.info("consultando solicitudes de traslado aceptadas.");

				List<SolicitudTraslado> result = datosUsuario.rol.equals("Administrador") || datosUsuario.rol.equals("Normativo") || datosUsuario.idOoad == 9 || datosUsuario.idOoad == 39 ?
						solicitudTrasladoRepository.findSolicitudTrasladoAceptadasAdmin() :
						solicitudTrasladoRepository.findSolicitudTrasladoAceptadas(datosUsuario.idOoad);

	            log.info("solicitudes, {}", result.size());
	            List<SolicitudTrasladoResponse> content = new ArrayList<>();
	            for (SolicitudTraslado solicitudTraslado : result) {
	            	SolicitudTrasladoResponse r =new SolicitudTrasladoResponse();
	            	r.setIdSolicitud(solicitudTraslado.getIdSolicitud());
	            	
	            	log.info("origen id "+solicitudTraslado.getCveOrigen());

	            	if (solicitudTraslado.getCveDestino() != null) {
		            	Optional<UnidadAdscripcion> opUdes= uAdscripcionRepository.findById(solicitudTraslado.getCveDestino());
		            	if(opUdes.isPresent()) {
		            		log.info("destino id "+opUdes.get().getNomUnidadAdscripcion());
		            		r.setCveDestino(opUdes.get());
		            	}
		            	else log.info("No se encuentra id unidad adscripcion"+ solicitudTraslado.getCveDestino());
	            	}
	            	if (solicitudTraslado.getCveOrigen() != null) {
		            	Optional<UnidadAdscripcion> opUOrig= uAdscripcionRepository.findById(solicitudTraslado.getCveOrigen());
		            	if(opUOrig.isPresent()) {
		            		log.info("destino id "+opUOrig.get().getNomUnidadAdscripcion());
		            		r.setCveOrigen(opUOrig.get());
		            	}
		            	else log.info("No se encuentra id unidad adscripcion"+ solicitudTraslado.getCveOrigen());
	            	}
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
