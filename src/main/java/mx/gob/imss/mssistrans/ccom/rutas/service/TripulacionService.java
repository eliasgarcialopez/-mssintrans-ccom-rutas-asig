package mx.gob.imss.mssistrans.ccom.rutas.service;

import mx.gob.imss.mssistrans.ccom.rutas.dto.Respuesta;
import mx.gob.imss.mssistrans.ccom.rutas.dto.TripulacionResponse;


public interface TripulacionService {
	  /**
     * Consulta la tripulacion por IdVehiculo
     *
     * @param idControlRuta
     * @return
     */
	Respuesta<TripulacionResponse> findByIdVehiculo(Integer idVehiculo);
	  

}
