package mx.gob.imss.mssintetrans.ccom.rutas.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.gob.imss.mssintetrans.ccom.rutas.dto.ControlRutasResponse;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.ControlRutasTablaResponse;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.ControlRutasTotalesResponse;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.Respuesta;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.ControlRutasRequest;





public interface ControlRutasService {
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
    Respuesta<Integer> crearRuta(ControlRutasRequest rutas);

    /**
     * Edita una ruta de acuerdo al Identificador
     *
     * @param idRuta
     * @param RutasDTO
     */
    Respuesta<Integer> editarRuta(Integer idControlRuta, ControlRutasRequest rutaDTO);

    /**
     * Elimina una asingacion Ruta, la eliminaci&oacute;n es l&oacute;gica;
     *
     * @param idControlRuta
     */
    Respuesta<Integer> eliminarRutas(Integer idControlRuta); 


}
