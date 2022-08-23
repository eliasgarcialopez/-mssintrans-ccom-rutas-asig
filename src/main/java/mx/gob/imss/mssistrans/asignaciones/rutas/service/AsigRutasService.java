package mx.gob.imss.mssistrans.asignaciones.rutas.service;

import mx.gob.imss.mssistrans.asignaciones.rutas.dto.Response;

@SuppressWarnings("rawtypes")
public interface AsigRutasService {
	<T>Response<?> consultaGeneral (Integer pagina, Integer tamanio, String orden, String columna);
	<T>Response<?> consultaById (Integer pagina, Integer tamanio, String idAsignacion, String idSolicitud);
	Response delete ( String id );
	<T>Response getRutas(Integer idOoad);
	<T>Response getSolicitudTraslado(String idrutaTraslado);

	<T>Response getDatosAsignacion(String idVehiuclo, Integer idOoad );
	<T>Response getTripulacionAsignada(String idVehiuclo, Integer idOoad );
	
/*	<T>Response consultaUnidades ();
	<T>Response generaPDF (Integer idOoad, Integer idUnidad, Integer idArrendatario);
	<T>Response save ( VehiculosCromaticaResponse vehiculo );*/
}
