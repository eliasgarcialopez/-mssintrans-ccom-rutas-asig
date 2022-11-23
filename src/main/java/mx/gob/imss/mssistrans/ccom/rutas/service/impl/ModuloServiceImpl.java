package mx.gob.imss.mssistrans.ccom.rutas.service.impl;

import java.util.Optional;

import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosUsuario;
import mx.gob.imss.mssistrans.ccom.rutas.dto.ModuloResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Respuesta;
import mx.gob.imss.mssistrans.ccom.rutas.model.ModuloAmbulancia;
import mx.gob.imss.mssistrans.ccom.rutas.model.Usuario;
import mx.gob.imss.mssistrans.ccom.rutas.repository.UsuarioRepository;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.repository.ModuloAmbulanciaRepository;
import mx.gob.imss.mssistrans.ccom.rutas.service.ModuloService;

@Service
@AllArgsConstructor
@Slf4j
public class ModuloServiceImpl implements ModuloService {
	@Autowired
	private ModuloAmbulanciaRepository moAmbulanciaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

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
				Usuario usrEntity=usuarioRepository.getUsuario(datosUsuarios.matricula);
				ModuloAmbulancia modAmb=null;
				if(usrEntity!=null && usrEntity.getIdUsuario()!=null){
					modAmb=moAmbulanciaRepository.findByIdUnidad(usrEntity.getIdUnidad());
				}
				Optional<ModuloAmbulancia> modOpt=null;
				if(modAmb!=null && modAmb.getIdModulo()!=null){
					modOpt = moAmbulanciaRepository.findByIdModulosActivo(modAmb.getIdModulo());
				} else {
					modOpt = moAmbulanciaRepository.findByIdOOADAndActivoEquals(datosUsuarios.getIDOOAD(), true);
				}
	 	   ModuloResponse mod=new ModuloResponse(); 
	 	   if(modOpt.isPresent()) {
	        	mod.setDesNombre(modOpt.get().getDesNombre());
	        	mod.setIdModulo(modOpt.get().getIdModulo());
	        	mod.setDesTipoModulo(modOpt.get().getDesTipoModulo());

	            response.setDatos(mod);
	            response.setMensaje("Exito");
	            response.setCodigo(HttpStatus.OK.value());
	        	
	        }else {
	        	response.setDatos(mod);
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
