package mx.gob.imss.mssintetrans.ccom.rutas.service;

import java.util.List;

import org.springframework.data.domain.Page;

import mx.gob.imss.mssintetrans.ccom.rutas.dto.ControlRutasResponse;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.Respuesta;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.SolicitudTrasladoResponse;


public interface SolicitudTrasladoService {
	  /**
     * Consulta todas las solicitudes de traslado aceptadas
     *
     * @param idControlRuta
     * @return
     */
	  Respuesta<List<SolicitudTrasladoResponse>>consultarSolicitudesByEstatusAndTurno(Integer turno);

}
