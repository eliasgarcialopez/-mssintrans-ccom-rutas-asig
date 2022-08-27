package mx.gob.imss.mssintetrans.ccom.rutas.service;

import java.util.List;

import org.springframework.data.domain.Page;

import mx.gob.imss.mssintetrans.ccom.rutas.dto.ControlRutasResponse;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.Respuesta;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.SolicitudTrasladoResponse;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.VehiculoResponse;


public interface VehiculoService {
	  /**
     * Consulta todas las vehiculos disponibles 
     *
     * @param idControlRuta
     * @return
     */
	  Respuesta<List<VehiculoResponse>>findVehiculoAsignables();

}
