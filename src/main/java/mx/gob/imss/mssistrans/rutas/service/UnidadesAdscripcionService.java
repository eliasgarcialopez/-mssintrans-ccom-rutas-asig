package mx.gob.imss.mssistrans.rutas.service;

import java.util.List;
import mx.gob.imss.mssistrans.rutas.dto.OOADResponse;
import mx.gob.imss.mssistrans.rutas.dto.Respuesta;

import mx.gob.imss.mssistrans.rutas.dto.UnidadesAdscripcionResponse;


public interface UnidadesAdscripcionService {
	
	
	 /**
     * Consulta todas las unidades de adscripcion por OOAD con/sin pernocta
     *@param idOOAD
     *@param percnota
     * @return
     */
    Respuesta<List<UnidadesAdscripcionResponse>> consultarUnidadesAdscripcionByIdOOA(Integer idOOAD, boolean pernocta);
    
    /**
     * Consulta todas las unidades de adscripcion del sistema
  
     * @return
     */
    Respuesta<List<UnidadesAdscripcionResponse>> consultarUnidadesAdscripcion();
    

    /**
     * Consulta todas las OOAD  del sistema
  
     * @return
     */
      Respuesta<List<OOADResponse>> consultarOOADS();
    

}
