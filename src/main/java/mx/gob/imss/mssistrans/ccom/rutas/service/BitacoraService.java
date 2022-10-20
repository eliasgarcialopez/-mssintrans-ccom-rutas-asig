package mx.gob.imss.mssistrans.ccom.rutas.service;

import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosBitacora;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;

import java.io.IOException;

public interface BitacoraService {
	
	/**
 	 * Busca vehiculo por id
 	 */
	Response<DatosBitacora> buscaVehiculo(String ecco, Integer idOoad);
	
	/**
 	 * Genera bitacora de servcios
 	 */
 	Response<byte[]> generaBitacora(Integer idOoad, Integer idControlRuta, String fechaResg, String matricula) throws IOException;

}
