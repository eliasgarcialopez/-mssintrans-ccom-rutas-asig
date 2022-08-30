package mx.gob.imss.mssistrans.asignaciones.rutas.service;

import mx.gob.imss.mssistrans.asignaciones.rutas.dto.Response;

@SuppressWarnings("rawtypes")
public interface ReasignacionRutasService {
	<T>Response<?> consultaGeneral (Integer pagina, Integer tamanio, String orden, String columna);
	<T>Response<?> consultaById (Integer pagina, Integer tamanio, String idAsignacion, String idSolicitud);
	Response delete ( String id );
	
	
	<T>Response getRutas(Integer idOoad);
	<T>Response getSolicitudTraslado(Integer idUnidadAdscripcion, Integer idVehiculo);
	<T>Response getDatosAsignacion(Integer idVehiculo, Integer idRuta, Integer idSolicitud);
	<T>Response getTripulacionAsignada(Integer idRuta, Integer idVehiculo, Integer idSolicitud);
	<T>Response getSiniestro();
	<T>Response update(String idVehiculo, String idNuevoVehiculo, String idRuta, String idNuevaRuta, String idSolicitud, String idNuevaSolicitud, String desEstatus);
	
 /* 
	<T>Response generaPDF (Integer idOoad, Integer idUnidad, Integer idArrendatario);
	<T>Response save ( VehiculosCromaticaResponse vehiculo );*/
}
