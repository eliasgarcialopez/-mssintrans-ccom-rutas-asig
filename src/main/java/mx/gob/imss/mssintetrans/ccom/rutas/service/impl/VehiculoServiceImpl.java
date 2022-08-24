package mx.gob.imss.mssintetrans.ccom.rutas.service.impl;

import java.time.LocalTime;
import java.util.ArrayList;
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
import mx.gob.imss.mssintetrans.ccom.rutas.dto.Respuesta;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.VehiculoResponse;
import mx.gob.imss.mssintetrans.ccom.rutas.model.ModuloAmbulancia;
import mx.gob.imss.mssintetrans.ccom.rutas.model.Vehiculos;
import mx.gob.imss.mssintetrans.ccom.rutas.model.ZonaAtencion;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.ModuloAmbulanciaRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.VehiculosRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.service.VehiculoService;
import mx.gob.imss.mssintetrans.ccom.rutas.util.VehiculoMapper;

@Service
@AllArgsConstructor
@Slf4j
public class VehiculoServiceImpl implements VehiculoService {
@Autowired
private VehiculosRepository vehiculosRepository;
@Autowired
private ModuloAmbulanciaRepository moAmbulanciaRepository;

	@Override
	public Respuesta<List<VehiculoResponse>> findVehiculoAsignables(Integer idOOAD) {
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
	        	 log.info("consultando los vehiculos disponibles, {}", idOOAD);
	        	 
	        	Optional<ModuloAmbulancia> modOp= moAmbulanciaRepository.findByIdOOADAndActivoEquals(idOOAD, true);
	        	if(modOp.isPresent()) {
	        	ZonaAtencion zona=	modOp.get().getZona();
	        	
	        	   log.info("zona atencion del modulo  , {}", zona.getIdZona());
		            
	             List<Vehiculos> result1 = vehiculosRepository.findVehiculoAsignables(zona.getIdZona());
	             List<Vehiculos> result2 = vehiculosRepository.findVehiculoArrendadosAsignables(zona.getIdZona());

	             List<Vehiculos> result =new ArrayList<>();
	             result.addAll(result1);
	             result.addAll(result2);

	            log.info("vehiculos obtenidos , {}", result.size());
	            
	            final List<VehiculoResponse> content = result
	                    .stream()
	                    .map(VehiculoMapper.INSTANCE::vehiculoEntityToJsonTo)
	                    .collect(Collectors.toList());

	            response.setDatos(content);
	            response.setMensaje("Exito");
	            response.setCodigo(HttpStatus.OK.value());
		         
	        		   
	        	}else {
	        		 response.setDatos(null);
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
