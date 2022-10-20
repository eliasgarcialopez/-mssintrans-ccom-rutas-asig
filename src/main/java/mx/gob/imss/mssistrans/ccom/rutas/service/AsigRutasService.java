package mx.gob.imss.mssistrans.ccom.rutas.service;

import mx.gob.imss.mssistrans.ccom.rutas.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface AsigRutasService {

    Response<Page<AsigRutasResponse>> getControlRutas(Pageable pageable, String idAsignacion, String idSolicitud);

    Response<Integer> delete(String id);


    /**
     * Recupera una lista de Rutas.
     *
     * @param idOoad
     * @param rol
     * @return
     */
    Response<List<DatosAsigRutasResponse>> getRutas(Integer idOoad, String rol);

    /**
     * Recupera la solicitud de traslado.
     *
     * @param datosUsuario
     * @param idRuta
     * @return
     */
    Response<List<SolTrasladoResponse>> getSolicitudTraslado(DatosUsuarioDTO datosUsuario, Integer idRuta);

    /**
     * Recupera el ecco
     *
     * @param datosUsuarios
     * @param idRuta
     * @return
     */
    Response<List<EccoResponse>> getEcco(DatosUsuarioDTO datosUsuarios, Integer idRuta);

    Response<DatosAsigResponse> getDatosAsignacion(Integer idControlRuta, Integer idRuta, Integer idSolicitud, Integer idVehiculo);

    /**
     * Recupera una tripulaci&oacute;n asignada.
     *
     * @param idControlRuta
     * @param idRuta
     * @param idSolicitud
     * @param idVehiculo
     * @return
     */
    Response<List<TripulacionAsigResponse>> getTripulacionAsignada(Integer idControlRuta, Integer idRuta, Integer idSolicitud, Integer idVehiculo);

    Response<DatosRegistroRecorridoDTO> getRegistroRecorrido(Integer idControlRuta, Integer idRuta, Integer idSolicitud, Integer idVehiculo);

    /**
     * Actualiza el recorrido de la ruta seleccionada
     *
     * @param datosRecorrido
     * @return
     */
    Response<Integer> update(ActualizarControlRutaRequest datosRecorrido);

    /**
     * Recupera el detalle de la asignaci&oacute;n.
     *
     * @param idControlRuta
     * @return
     */
    Response<List<DetRutasAsignacionesResponse>> getDetalleRutasyAsig(Integer idControlRuta);

    /**
     * Recupera la consulta para la vista r&aacute;pida.
     *
     * @param pagina
     * @param tamanio
     * @param orden
     * @param columna
     * @param idAsignacion
     * @param idSolicitud
     * @return
     */
    Response<Page<AsigRutasResponse>> consultaVistaRapida(
            Pageable pageable,
            String idAsignacion, String idSolicitud);

    Response<DatosControlRutaDTO> getDatosControlRutaById(Integer idControlRuta);
}
