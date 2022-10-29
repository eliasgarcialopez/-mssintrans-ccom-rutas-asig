package mx.gob.imss.mssistrans.ccom.rutas.service;

import java.util.List;

import org.springframework.data.domain.Page;

import mx.gob.imss.mssistrans.ccom.rutas.dto.Asignacion;
import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosUsuario;
import mx.gob.imss.mssistrans.ccom.rutas.dto.RespuestaAsig;
import mx.gob.imss.mssistrans.ccom.rutas.model.AsignacionesEntity;

public interface AsignacionesService {
	/**
     * Alta de asignación
     */
	RespuestaAsig<AsignacionesEntity> registraAsignacion(Asignacion asignacion, DatosUsuario datosUsuarios);
 	
	/**
     * Actualización de asignación
     */
	RespuestaAsig<Asignacion> actualizaAsignacion(Asignacion asignacion, String matricula);
 
}
