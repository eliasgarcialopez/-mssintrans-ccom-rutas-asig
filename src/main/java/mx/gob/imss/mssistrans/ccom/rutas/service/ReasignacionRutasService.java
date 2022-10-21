package mx.gob.imss.mssistrans.ccom.rutas.service;

import mx.gob.imss.mssistrans.ccom.rutas.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReasignacionRutasService {

    Response<Page<AsigRutasResponse>> consultaVistaRapida(Pageable pageable, String idAsignacion, String idSolicitud);

    /**
     * Elimina una reasignaci&oacute;n.
     *
     * @param id
     * @return
     */
    Response<Integer> delete(String id);

    /**
     * Recupera una lista de n&uacute;meros ecco.
     *
     * @return
     */
    Response<List<ReasignacionEccoResponse>> getEcco();

    /**
     * Recupera la tripulaci&oacute;n asignada.
     *
     * @param idControlRuta
     * @param idRuta
     * @param idSolicitud
     * @param idVehiculo
     * @return
     */
    Response<List<ReasignacionTripulacionResponse>> getTripulacionAsignada(Integer idControlRuta, Integer idRuta, Integer idSolicitud, Integer idVehiculo);

    /**
     * Recupera una lista de Siniestros.
     *
     * @return
     */
    Response<List<SiniestrosResponse>> getSiniestro();

    /**
     * Guarda una nueva reasignaci&oacute;n.
     *
     * @param idVehiculo
     * @param idRuta
     * @param idChofer
     * @param desMotivoReasig
     * @param desSiniestro
     * @param idVehiculoSust
     * @param idChoferSust
     * @param idAsignacion
     * @param cveMatricula
     * @return
     */
    Response<Integer> crearReasignacion(Integer idVehiculo, Integer idRuta,
                                        Integer idChofer, String desMotivoReasig,
                                        String desSiniestro, Integer idVehiculoSust,
                                        Integer idChoferSust, Integer idAsignacion,
                                        String cveMatricula);

    Response<List<DetReasignacionesRutasResponse>> getDetalleReAsignacion(Integer idControlRuta);

    Response<Integer> update(String desSiniestro, Integer idVehoculoSust, String desMotivoReasignacion, Integer idVehiculo, Integer idRuta, Integer idChofer);

}
