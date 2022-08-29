package mx.gob.imss.mssintetrans.ccom.rutas.service;

import mx.gob.imss.mssintetrans.ccom.rutas.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ControlRutasForaneasService {
	 /**
     * Consulta todas las rutas
     *
     * @return
     */
    Respuesta<Page<ControlRutasTablaResponse>> consultarRutas(Pageable pageable);
    /**
     * Consulta los totales del vehiculos por modulo 
     *
     * @param idControlRuta
     * @return
     */
    Respuesta<ControlRutasTotalesResponse> consultarTotalesVehiculos();
    
    /**
     * Consulta una ruta y su control por el Identificador
     *
     * @param idControlRuta
     * @return
     */
    Respuesta<ControlRutasResponse> consultarRutas(Integer idControlRuta);

    /**
     * Crea un nueva Ruta
     */
    Respuesta<Integer> crearRuta(ControlRutasForaneasRequest rutas);

    /**
     * Edita una ruta de acuerdo al Identificador
     *
     * @param idRuta
     * @param RutasDTO
     */
    Respuesta<Integer> editarRuta(Integer idControlRuta, ControlRutasForaneasRequest rutaDTO);

    /**
     * Elimina una asingacion Ruta, la eliminaci&oacute;n es l&oacute;gica;
     *
     * @param idControlRuta
     */
    Respuesta<Integer> eliminarRutas(Integer idControlRuta); 


}
