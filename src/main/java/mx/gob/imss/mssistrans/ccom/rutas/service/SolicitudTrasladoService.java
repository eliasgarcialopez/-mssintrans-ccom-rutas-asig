package mx.gob.imss.mssistrans.ccom.rutas.service;

import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;
import mx.gob.imss.mssistrans.ccom.rutas.dto.SolicitudTrasladoResponse;

import java.util.List;


public interface SolicitudTrasladoService {
    /**
     * Consulta todas las solicitudes de traslado aceptadas
     *
     * @return
     */
    Response<List<SolicitudTrasladoResponse>> consultarSolicitudesByEstatus();

}
