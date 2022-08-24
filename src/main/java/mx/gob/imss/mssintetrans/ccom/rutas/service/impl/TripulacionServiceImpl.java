package mx.gob.imss.mssintetrans.ccom.rutas.service.impl;

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
import mx.gob.imss.mssintetrans.ccom.rutas.dto.TripulacionResponse;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.VehiculoResponse;
import mx.gob.imss.mssintetrans.ccom.rutas.model.ModuloAmbulancia;
import mx.gob.imss.mssintetrans.ccom.rutas.model.Tripulacion;
import mx.gob.imss.mssintetrans.ccom.rutas.model.Usuario;
import mx.gob.imss.mssintetrans.ccom.rutas.model.Vehiculos;
import mx.gob.imss.mssintetrans.ccom.rutas.model.ZonaAtencion;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.ControlRutasRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.TarjetasElectronicasRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.TripulacionRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.repository.UsuarioRepository;
import mx.gob.imss.mssintetrans.ccom.rutas.service.TripulacionService;
import mx.gob.imss.mssintetrans.ccom.rutas.util.VehiculoMapper;

@Service
@AllArgsConstructor
@Slf4j
public class TripulacionServiceImpl implements TripulacionService {
@Autowired	
 private TripulacionRepository triRepository;
@Autowired
 private UsuarioRepository usuarioRepository;
 
	@Override
	public Respuesta<TripulacionResponse> findByIdVehiculo(Integer idVehiculo) {
		 Respuesta<TripulacionResponse> response = new Respuesta<>();
			
	        try {
	       	 String usuario = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				log.info("usuario {}", usuario);
				
				if (usuario.equals("denegado")) {
				
					response.setError(false);
					response.setCodigo(HttpStatus.UNAUTHORIZED.value());
					response.setMensaje(usuario);
					return response;
				}
	        	 log.info("consultando la tripulacion por idVehiculo, {}", idVehiculo);
	        	 
	        	Optional<Tripulacion> tripOp= triRepository.findByIdVehiculo(idVehiculo);
	        	if(tripOp.isPresent()) {
	        		Tripulacion tripulacion=	tripOp.get();
	        	TripulacionResponse tripRes=new TripulacionResponse();
	        	if(tripulacion.getFecFecha()!=null)
	        	tripRes.setFecFecha(tripulacion.getFecFecha().toString());
	        	
	        	tripRes.setIdTripulacion(tripulacion.getIdTripulacion());
	        	tripRes.setIdVehiculo(tripulacion.getIdVehiculo());
	        	
	        	
	        	tripRes.setCveMatriculaCamillero1(tripulacion.getCveMatriculaCamillero1());
	        	tripRes.setCveMatriculaCamillero2(tripulacion.getCveMatriculaCamillero2());
	        	tripRes.setCveMatriculaChofer(tripulacion.getCveMatriculaChofer());
	        	
	        	Usuario  camillero1=usuarioRepository.getUsuario(tripulacion.getCveMatriculaCamillero1());
	        	tripRes.setNombreCamillero1(camillero1.getNOM_USUARIO()+ " "+camillero1.getNOM_APELLIDO_PATERNO() + " "+ camillero1.getNOM_APELLIDO_MATERNO());

	        	Usuario  camillero2=usuarioRepository.getUsuario(tripulacion.getCveMatriculaCamillero2());
	        	tripRes.setNombreCamillero2(camillero2.getNOM_USUARIO()+ " "+camillero2.getNOM_APELLIDO_PATERNO() + " "+ camillero2.getNOM_APELLIDO_MATERNO());
	        	
	        	Usuario  chofer=usuarioRepository.getUsuario(tripulacion.getCveMatriculaChofer());
	        	tripRes.setNombreChofer(chofer.getNOM_USUARIO()+ " "+chofer.getNOM_APELLIDO_PATERNO() + " "+ chofer.getNOM_APELLIDO_MATERNO());
	        	
	        	
	        	
	        	
	        	   log.info("tripulacion  , {}", tripulacion.getIdTripulacion());
		       
	            response.setDatos(tripRes);
	            response.setMensaje("Exito");
	            response.setCodigo(HttpStatus.OK.value());
		         
	        		   
	        	}else {
	        		 response.setDatos(null);
		 	            response.setError(false);
		 	            response.setMensaje("Exito");
		 	            response.setCodigo(HttpStatus.OK.value()); 
	        	}
	        } catch (Exception exception) {
	               
	            log.error("Ha ocurrido un error al consultar la tripulacion, {}", ExceptionUtils.getStackTrace(exception));
	            response.setError(true);
	            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
	            response.setMensaje(exception.getMessage());
	        }
	        return response;
	}

}
