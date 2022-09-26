package mx.gob.imss.mssistrans.ccom.rutas.service;

import java.util.List;

import mx.gob.imss.mssistrans.ccom.rutas.dto.Respuesta;
import mx.gob.imss.mssistrans.ccom.rutas.dto.VehiculoResponse;


public interface VehiculoService {
	  /**
     * Consulta todas las vehiculos disponibles 
     *
     * @param idControlRuta
     * @return
     */
	  Respuesta<List<VehiculoResponse>> findVehiculoAsignables();

}
