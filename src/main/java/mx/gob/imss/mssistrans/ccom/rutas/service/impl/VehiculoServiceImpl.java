package mx.gob.imss.mssistrans.ccom.rutas.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosUsuario;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Respuesta;
import mx.gob.imss.mssistrans.ccom.rutas.dto.VehiculoResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.ModuloAmbulancia;
import mx.gob.imss.mssistrans.ccom.rutas.model.Vehiculos;
import mx.gob.imss.mssistrans.ccom.rutas.model.ZonaAtencion;
import mx.gob.imss.mssistrans.ccom.rutas.util.VehiculoMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.repository.ModuloAmbulanciaRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.VehiculosRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.ZonaAtencionRepository;
import mx.gob.imss.mssistrans.ccom.rutas.service.VehiculoService;

@Service
@AllArgsConstructor
@Slf4j
public class VehiculoServiceImpl implements VehiculoService {
@Autowired
private VehiculosRepository vehiculosRepository;
@Autowired
private ModuloAmbulanciaRepository moAmbulanciaRepository;
@Autowired
private ZonaAtencionRepository zonaAtencionRepository;

	@Override
	public Respuesta<List<VehiculoResponse>> findVehiculoAsignables() {
		 Respuesta<List<VehiculoResponse>> response = new Respuesta<>();
			
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
				
	        	 log.info("consultando los vehiculos disponibles, {}", datosUsuarios.getIDOOAD());
	        	 
	        	Optional<ModuloAmbulancia> modOp= moAmbulanciaRepository.findByIdOOADAndActivoEquals(datosUsuarios.getIDOOAD(), true);
	        	if(modOp.isPresent()) {
	        	//ZonaAtencion zona=	modOp.get().getZona();
	        	
	        	Optional<ZonaAtencion> zonaOp=   zonaAtencionRepository.findByIdModuloAndActivoEquals(modOp.get().getIdModulo(),true);
				if(zonaOp.isPresent()) {
					ZonaAtencion zona=zonaOp.get();
					log.info("zona atencion del modulo  , {}", zona.getIdZona());
		            
		             List<Vehiculos> result1 = vehiculosRepository.findVehiculoAsignables(zona.getIdZona());
		             List<Vehiculos> result2 = vehiculosRepository.findVehiculoArrendadosAsignables(zona.getIdZona());

		             List<Vehiculos> result = new ArrayList<>();
		             result.addAll(result1);
		             result.addAll(result2);

		            log.info("vehiculos obtenidos , {}", result.size());
		            List<VehiculoResponse> content = new ArrayList<VehiculoResponse>();
		            
		            if(!result.isEmpty()) {
		            	content = result
			                    .stream()
			                    .map(VehiculoMapper.INSTANCE::vehiculoEntityToJsonTo)
			                    .collect(Collectors.toList());
		            }
		            
		            response.setDatos(content);
		            response.setMensaje("Exito");
		            response.setCodigo(HttpStatus.OK.value());
				}
		         
	        		   
	        	}else {
	        		   response.setDatos(new ArrayList<>());
		 	           response.setError(false);
		 	           response.setMensaje("Exito");
		 	           response.setCodigo(HttpStatus.OK.value()); 
	        	}
	        } catch (Exception exception) {
	               
	            log.error("Ha ocurrido un error al consultar los vehiculo, {}", ExceptionUtils.getStackTrace(exception));
	            response.setError(true);
	            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
	            response.setMensaje(exception.getMessage());
	        }
	        return response;
	}

}
