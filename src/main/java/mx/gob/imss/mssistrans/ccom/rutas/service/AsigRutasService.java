package mx.gob.imss.mssistrans.ccom.rutas.service;

import mx.gob.imss.mssistrans.ccom.rutas.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface AsigRutasService {

	<T>Response<?> getControlRutas (Integer pagina, Integer tamanio, String orden, String columna, String idAsignacion, String idSolicitud);
	Response delete ( String id );
	
	
	<T>Response getRutas(Integer idOoad, String rol);
	<T>Response getSolicitudTraslado(DatosUsuarioDTO datosUsuario, Integer idRuta);
	<T>Response getEcco(DatosUsuarioDTO datosUsuarios, Integer idRuta);
	<T>Response getDatosAsignacion(Integer idControlRuta, Integer idRuta, Integer idSolicitud, Integer idVehiculo);
	<T>Response getTripulacionAsignada(Integer idControlRuta, Integer idRuta, Integer idSolicitud, Integer idVehiculo);
	<T>Response getRegistroRecorrido(Integer idControlRuta, Integer idRuta, Integer idSolicitud, Integer idVehiculo);

	/**
	 * Actualiza el recorrido de la ruta seleccionada
	 *
	 * @param datosRecorrido
	 * @return
	 */
	Response<?> update(ActualizarControlRutaRequest datosRecorrido);
	
	<T>Response getDetalleRutasyAsig(Integer idControlRuta);
	
	<T>Response<?> consultaVistaRapida (Integer pagina, Integer tamanio, String orden, String columna, String idAsignacion, String idSolicitud);
	<T>Response getDatosControlRutaById(Integer idControlRuta);
}
