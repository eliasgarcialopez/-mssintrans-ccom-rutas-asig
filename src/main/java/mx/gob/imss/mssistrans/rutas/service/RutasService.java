package mx.gob.imss.mssistrans.rutas.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.gob.imss.mssistrans.rutas.dto.Respuesta;
import mx.gob.imss.mssistrans.rutas.dto.RutasRequest;
import mx.gob.imss.mssistrans.rutas.dto.RutasResponse;
import mx.gob.imss.mssistrans.rutas.dto.RutasTablaResponse;
import mx.gob.imss.mssistrans.rutas.model.Rutas;


public interface RutasService {
	 /**
     * Consulta todas las rutas
     *
     * @return
     */
    Respuesta<Page<RutasTablaResponse>> consultarRutas(Pageable pageable);
    
    /**
     * Consulta todas las rutas por Filtro
     *
     * @return
     */
    Respuesta<Page<RutasTablaResponse>> consultarRutasPorFiltro(String folioRuta, Pageable pageable);

    /**
     * Consulta una ruta por el Identificador
     *
     * @param idRuta
     * @return
     */
    Respuesta<RutasResponse> consultarRutas(Integer idRutas);

    /**
     * Crea un nueva Ruta
     */
    Respuesta<Integer> crearRuta(RutasRequest rutas);

    /**
     * Edita una ruta de acuerdo al Identificador
     *
     * @param idRuta
     * @param RutasDTO
     */
    Respuesta<Integer> editarRuta(Integer idRuta, RutasRequest rutaDTO);

    /**
     * Elimina una Ruta, la eliminaci&oacute;n es l&oacute;gica;
     *
     * @param idRuta
     */
    Respuesta<Integer> eliminarRutas(Integer idRuta); 


}
