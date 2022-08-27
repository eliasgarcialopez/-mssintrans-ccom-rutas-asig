package mx.gob.imss.mssintetrans.ccom.rutas.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.DatosUsuario;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.ModuloResponse;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.Respuesta;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.SolicitudTrasladoResponse;
import mx.gob.imss.mssintetrans.ccom.rutas.model.ModuloAmbulancia;
import mx.gob.imss.mssintetrans.ccom.rutas.model.SolicitudTraslado;
import mx.gob.imss.mssintetrans.ccom.rutas.model.UnidadAdscripcion;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.ControlRutasRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.ModuloAmbulanciaRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.RutasDestinosRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.RutasRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.SolicitudTrasladoRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.TripulacionRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.UnidadAdscripcionRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.UsuarioRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.VehiculosRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.service.ModuloService;
import mx.gob.imss.mssintetrans.ccom.rutas.util.Utility;

@Service
@AllArgsConstructor
@Slf4j
public class ModuloServiceImpl implements ModuloService {
	@Autowired
	private ModuloAmbulanciaRepository moAmbulanciaRepository;

	@Override
	public Respuesta<ModuloResponse> busquedaModulo() {
		 Respuesta<ModuloResponse> response = new Respuesta<>();
			
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
	 			DatosUsuario datosUsuarios = gson.fromJson(usuario, DatosUsuario.class);
	 			
	 			 log.info("consultando modulo  de ambulancia , {}", datosUsuarios.getIDOOAD());
	 			 Optional<ModuloAmbulancia>modOpt= moAmbulanciaRepository.findByIdOOADAndActivoEquals(datosUsuarios.getIDOOAD(), true);
	        if(modOpt.isPresent()) {
	        	
	        	ModuloResponse mod=new ModuloResponse();
	        	mod.setDesNombre(modOpt.get().getDesNombre());
	        	mod.setIdModulo(modOpt.get().getIdModulo());
	        	mod.setDesTipoModulo(modOpt.get().getDesTipoModulo());

	            response.setDatos(mod);
	            response.setMensaje("Exito");
	            response.setCodigo(HttpStatus.OK.value());
	        	
	        }else {
	        	response.setDatos(null);
				response.setError(false);
				response.setMensaje("Exito");
				response.setCodigo(HttpStatus.OK.value());
	        }

	            
	        } catch (Exception exception) {
	               
	            log.error("Ha ocurrido un error al consultar el modulo, {}", ExceptionUtils.getStackTrace(exception));
	            response.setError(true);
	            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
	            response.setMensaje(exception.getMessage());
	        }
	        return response;
	}

}
