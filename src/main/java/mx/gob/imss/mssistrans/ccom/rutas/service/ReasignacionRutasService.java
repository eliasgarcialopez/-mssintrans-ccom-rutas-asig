package mx.gob.imss.mssistrans.ccom.rutas.service;

import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;

@SuppressWarnings("rawtypes")
public interface ReasignacionRutasService {

	<T>Response<?> consultaVistaRapida (Integer pagina, Integer tamanio, String orden, String columna, String idAsignacion, String idSolicitud);
	Response delete ( String id );
	
	
	<T>Response getRutas(Integer idOoad, String rol);
	<T>Response getSolicitudTraslado(Integer idUnidadAdscripcion, Integer idVehiculo);
	<T>Response getDatosAsignacion(Integer idVehiculo, Integer idRuta, Integer idSolicitud);
	<T>Response getTripulacionAsignada(Integer idRuta, Integer idVehiculo, Integer idSolicitud);
	<T>Response getSiniestro();
	<T>Response update(String idVehiculo, String idNuevoVehiculo, String idRuta, String idNuevaRuta, String idSolicitud, String idNuevaSolicitud, String desEstatus);
	
 /* 
	<T>Response generaPDF (Integer idOoad, Integer idUnidad, Integer idArrendatario);
	<T>Response save ( VehiculosCromaticaResponse vehiculo );*/
}
