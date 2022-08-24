package mx.gob.imss.mssintetrans.ccom.rutas.service;

import java.util.List;

import org.springframework.data.domain.Page;

import mx.gob.imss.mssintetrans.ccom.rutas.dto.CatalogoGenerico;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.Respuesta;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.TripulacionResponse;



public interface TripulacionService {
	  /**
     * Consulta la tripulacion por IdVehiculo
     *
     * @param idControlRuta
     * @return
     */
	Respuesta<TripulacionResponse> findByIdVehiculo(Integer idVehiculo);
	  

}
