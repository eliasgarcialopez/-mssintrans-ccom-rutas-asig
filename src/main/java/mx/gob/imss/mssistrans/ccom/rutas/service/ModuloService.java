package mx.gob.imss.mssistrans.ccom.rutas.service;

import mx.gob.imss.mssistrans.ccom.rutas.dto.ModuloResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;


public interface ModuloService {
	  /**
     * Consulta el modulo disponible por idOOAD
     *
     * @return
     */
	Response<ModuloResponse> busquedaModulo();
}
