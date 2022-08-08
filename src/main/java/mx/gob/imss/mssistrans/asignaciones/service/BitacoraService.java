package mx.gob.imss.mssistrans.asignaciones.service;

import mx.gob.imss.mssistrans.asignaciones.dto.BitacoraServicio;
import mx.gob.imss.mssistrans.asignaciones.dto.Respuesta;

public interface BitacoraService {
	
 	/**
 	 * Genera bitacora de servcios
 	 */
 	Respuesta<byte[]> generaBitacora(BitacoraServicio bitacoraServicio);
 	
}
