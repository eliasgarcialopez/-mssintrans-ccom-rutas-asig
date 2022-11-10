package mx.gob.imss.mssistrans.ccom.rutas.service;

import mx.gob.imss.mssistrans.ccom.rutas.dto.Respuesta;
import mx.gob.imss.mssistrans.ccom.rutas.dto.SolicitudTrasladoResponse;

import java.util.List;


public interface SolicitudTrasladoService {
    /**
     * Consulta todas las solicitudes de traslado aceptadas rutas locales
     *
     * @return
     */
    Respuesta<List<SolicitudTrasladoResponse>> obtenerSolicitudesByEstatus(Integer ruta);
}
