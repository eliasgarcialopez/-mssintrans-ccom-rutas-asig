package mx.gob.imss.mssistrans.asignaciones.service;

import java.util.List;

import mx.gob.imss.mssistrans.asignaciones.dto.*;

public interface AsignacionesService {

	/**
	 * Búsqueda de asignaciones por Ooad
	 */
	RespuestaTO busquedaAsignacionesByOoad(Integer pagina, Integer tamanio, String orden, Integer idOoad);
	
    /**
    * Búsqueda de asignaciones por folio
    */
	Respuesta<List<DatosAsignacion>> busquedaAsignacionesByNum(String numAsignacion, Integer idOoad);
		
    /**
     * Busqueda de vehiculos asignables
     */
    Respuesta<List<DatosVehiculo>> busquedaVehiculosAsignables(Integer idOoad);

 	/**
 	 * Busqueda de choferes por OOAD 
 	 */
 	Respuesta<List<CatalogoGenerico>> busquedaChoferes(Integer idOoad);
 	
 	/**
 	 * Busqueda de rutas por OOAD
 	 */
 	Respuesta<List<CatalogoGenerico>> busquedaRutas(Integer idOoad);
 	
    /**
     * Búsqueda de tarjeta digital por OOAD
     */
 	Respuesta<List<CatalogoGenerico>> busquedaTarjetasDigitales(Integer idOoad);
	
	/**
     * Alta de asignación
     */
 	Respuesta<Asignacion> registraAsignacion(Asignacion asignacion);
 	
 	/**
     * Obtiene de asignación por id
     */
 	Respuesta<Asignacion> getAsignacionById(Integer idAsignacion);
	
	/**
     * Actualización de asignación
     */
 	Respuesta<Asignacion> actualizaAsignacion(Asignacion asignacion);
 	
	/**
     * Borrado de asignación
     */
 	Respuesta<Integer> eliminaAsignacion(Integer idAsignacion);

}
