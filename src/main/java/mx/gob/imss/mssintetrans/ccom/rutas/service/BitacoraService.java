package mx.gob.imss.mssintetrans.ccom.rutas.service;

import java.io.IOException;

import mx.gob.imss.mssintetrans.ccom.rutas.dto.DatosBitacora;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.Respuesta;

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
