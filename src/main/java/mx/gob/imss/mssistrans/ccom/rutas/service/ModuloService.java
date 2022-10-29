package mx.gob.imss.mssistrans.ccom.rutas.service;

import mx.gob.imss.mssistrans.ccom.rutas.dto.ModuloResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Respuesta;


public interface ModuloService {
	  /**
     * Consulta el modulo disponible por idOOAD
     *
     * @return
     */
	Respuesta<ModuloResponse> busquedaModulo();
}
