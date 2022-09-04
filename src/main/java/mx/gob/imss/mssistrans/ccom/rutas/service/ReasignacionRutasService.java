package mx.gob.imss.mssistrans.ccom.rutas.service;

import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosUsuarioDTO;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;

@SuppressWarnings("rawtypes")
public interface ReasignacionRutasService {

	<T>Response<?> consultaVistaRapida (Integer pagina, Integer tamanio, String orden, String columna, String idAsignacion, String idSolicitud);
	Response delete ( String id );
	
	
	//<T>Response getRutas(Integer idOoad, String rol);
	//<T>Response getSolicitudTraslado(DatosUsuarioDTO datosUsuario, Integer idRuta);
	<T>Response getEcco(DatosUsuarioDTO datosUsuarios, Integer idRuta);
	//<T>Response getDatosReAsignacion(Integer idVehiculo, Integer idRuta, Integer idSolicitud);
	<T>Response getTripulacionAsignada(Integer idControlRuta, Integer idRuta, Integer idSolicitud, Integer idVehiculo);
	<T>Response getSiniestro();
	<T>Response update(String idVehiculo, String idNuevoVehiculo, String idRuta, String idNuevaRuta, String idSolicitud, String idNuevaSolicitud, String desEstatus);

	<T>Response save(Integer idVehiculo, Integer idRuta, Integer idChofer, String desMotivoReasig,
			String desSiniestro, Integer idVehiculoSust, Integer idChoferSust, Integer idAsignacion
			, String cveMatricula);
	
	<T>Response getDetalleReAsignacion(Integer idControlRuta);

}
