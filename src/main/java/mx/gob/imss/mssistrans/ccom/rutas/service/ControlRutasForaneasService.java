package mx.gob.imss.mssistrans.ccom.rutas.service;

import mx.gob.imss.mssistrans.ccom.rutas.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ControlRutasForaneasService {
	 /**
     * Consulta todas las rutas
     *
     * @return
     */
    Response<Page<ControlRutasTablaResponse>> consultarRutas(DatosUsuario usuario, Pageable pageable);

    /**
     * Consulta una ruta y su control por el Identificador
     *
     * @param idControlRuta
     * @return
     */
    Respuesta<ControlRutasForaneasResponse> consultarRutas(Integer idControlRuta);

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
