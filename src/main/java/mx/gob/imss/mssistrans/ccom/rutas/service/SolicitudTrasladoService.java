package mx.gob.imss.mssistrans.ccom.rutas.service;

import java.util.List;

import mx.gob.imss.mssistrans.ccom.rutas.dto.Respuesta;
import mx.gob.imss.mssistrans.ccom.rutas.dto.SolicitudTrasladoResponse;


public interface SolicitudTrasladoService {
	  /**
     * Consulta todas las solicitudes de traslado aceptadas
     *
     * @param idControlRuta
     * @return
     */
	  Respuesta<List<SolicitudTrasladoResponse>> consultarSolicitudesByEstatus();

}
