package mx.gob.imss.mssintetrans.ccom.rutas.service;

import java.io.IOException;

import mx.gob.imss.mssintetrans.ccom.rutas.dto.DatosBitacora;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.Respuesta;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.BitacoraServicio;

public interface BitacoraService {
	
	/**
 	 * Busca vehiculo por id
 	 */
	Respuesta<DatosBitacora> buscaVehiculo(String ecco, Integer idOoad);
	
	/**
 	 * Genera bitacora de servcios
 	 */
 	Respuesta<byte[]> generaBitacora(BitacoraServicio bitacoraServicio, String matricula) throws IOException;

}
