package mx.gob.imss.mssistrans.ccom.rutas.service;

import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosBitacora;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Respuesta;

import java.io.IOException;

public interface BitacoraService {
	
	/**
 	 * Busca vehiculo por id
 	 */
	Respuesta<DatosBitacora> buscaVehiculo(String ecco, Integer idOoad);
	
	/**
 	 * Genera bitacora de servcios
 	 */
 	Respuesta<byte[]> generaBitacora(Integer idOoad, Integer idControlRuta, String fechaResg, String matricula) throws IOException;

}
