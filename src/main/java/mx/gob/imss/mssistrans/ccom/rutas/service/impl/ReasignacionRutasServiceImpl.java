package mx.gob.imss.mssistrans.ccom.rutas.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.dto.*;
import mx.gob.imss.mssistrans.ccom.rutas.model.*;
import mx.gob.imss.mssistrans.ccom.rutas.repository.*;
import mx.gob.imss.mssistrans.ccom.rutas.service.ReasignacionRutasService;
import mx.gob.imss.mssistrans.ccom.rutas.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static mx.gob.imss.mssistrans.ccom.rutas.util.AsigRutasMapper.INSTANCE;

/**
 * @author opimentel
 */
@Transactional(rollbackOn = SQLException.class)
@AllArgsConstructor
@Slf4j
@Service
public class ReasignacionRutasServiceImpl implements ReasignacionRutasService {
    private final AsigRutasRepository asigRutasRepository;
    private final ReasignacionEccoRepository eccoRepository;
    private final DatosAsigRepository datosRepository;
    private final DatosControlRutasRepository datosCRRepository;
    private final ReasignacionTripulacionRepository choferRepository;

    @Autowired
    private TripulacionAsigCamillero01Repository camillero01Repository;

    @Autowired
    private TripulacionAsigCamillero02Repository camillero02Repository;

    @Autowired
    private SiniestrosRepository siniestrosRepository;

    @Autowired
    private ReAsignacionRutasRepository reasignacionRepository;

    @Autowired
    private VehiculosRepository vehiculosRepository;

    @Autowired
    private DetalleReAsignacionRutasRepository detalleReAsignacionRutasRepository;

    @Override
    public Response<Page<AsigRutasResponse>> consultaVistaRapida(
            Pageable pageable,
            String idRuta,
            String idSolicitud) {

        // todo - parece que no se esta usando bien los filtros del sortable y por eso esta usando
        //        ese tal nomCol
        //      - hay que hacer el pageable en el controller y aca solo la consulta
        //      - podemos hacer el cambio de las consultas para que sean con jpa
        try {
            Page<AsigRutasEntity> consultaAsignacionRutas = null;
            if (idRuta == null || idRuta.equals("")) {
                if (idSolicitud == null || idSolicitud.equals("")) {
                    consultaAsignacionRutas = asigRutasRepository.consultaGeneralRasignaciones(pageable);
                } else {
                    consultaAsignacionRutas = asigRutasRepository.getConsultaByIdSolicitudReasignaciones(idSolicitud, pageable);
                }
            } else if (idSolicitud == null || idSolicitud.equals("")) {
                consultaAsignacionRutas = asigRutasRepository.getConsultaByIdAsignacionReasignaciones(idRuta, pageable);
            } else {
                consultaAsignacionRutas = asigRutasRepository.getConsultaByIdReasignaciones(idRuta, idSolicitud, pageable);
            }
            final List<AsigRutasResponse> content = consultaAsignacionRutas.getContent().stream()
                    .map(INSTANCE::entityToJson)
                    .collect(Collectors.toList());

            Page<AsigRutasResponse> objetoMapeado = new PageImpl<>(content, pageable,
                    consultaAsignacionRutas.getTotalElements());

            return ResponseUtil.crearRespuestaExito(objetoMapeado);
        } catch (Exception e) {
            log.error("Ha ocurrido un error al realizar la consulta, {}", e.getMessage());
            return ResponseUtil.errorException(e);
        }

    }

    @Override
    public Response<Integer> delete(String idReAsignacion) {
        try {
            asigRutasRepository.deleteReasignacion(idReAsignacion);
            asigRutasRepository.flush();

            asigRutasRepository.actalizarCOntrolRutasReasignacion(idReAsignacion);
            asigRutasRepository.flush();
            return ResponseUtil.crearRespuestaExito(null);
        } catch (Exception e) {
            return ResponseUtil.errorException(e);
        }
    }

    /****** HU006 - 28 **********/

    @Override
    public Response<List<DetReasignacionesRutasResponse>> getDetalleReAsignacion(Integer idControlRuta) {
        DatosControlRutasEntity datosCR;
        ReasignacionTripulacionGroupEntity tripulacionAsigEntity;

        List<DetReasignacionRutasEntity> detalleReAsignaciones = new ArrayList<>();
        DetReasignacionRutasEntity detRutasAsignaciones = new DetReasignacionRutasEntity();
        try {
            new ReasignacionTripulacionGroupEntity();
            datosCR = datosCRRepository.getDatosAsigByIdCtrlRuta(idControlRuta);
            tripulacionAsigEntity = obtenerTripulacion(idControlRuta, null, null, null);

            detRutasAsignaciones.setDatosReasignacion(datosCR);
            detRutasAsignaciones.setTripulacion(tripulacionAsigEntity);
            detalleReAsignaciones.add(detRutasAsignaciones);

            List<DetReasignacionesRutasResponse> listaDeSolicituTraslado = DatosReasignacionMapper.INSTANCE.entityAJson(detalleReAsignaciones);

            return ResponseUtil.crearRespuestaExito(listaDeSolicituTraslado);
        } catch (Exception e) {
            return ResponseUtil.errorException(e);
        }

    }


    @Override
    public Response<List<ReasignacionEccoResponse>> getEcco() {

        List<ReasignacionEccoEntity> consultaGeneral;
        try {
            consultaGeneral = eccoRepository.getEcco();

            List<ReasignacionEccoResponse> listaDeEccos = consultaGeneral.stream()
                    .map(EccoMapper.INSTANCE::entityToJson)
                    .collect(Collectors.toList());

            return ResponseUtil.crearRespuestaExito(listaDeEccos);
        } catch (Exception e) {
            return ResponseUtil.errorException(e);
        }

    }

    @Override
    public Response<List<ReasignacionTripulacionResponse>> getTripulacionAsignada(
            Integer idControlRuta,
            Integer idRuta,
            Integer idSolicitud,
            Integer idVehiculo) {

        List<ReasignacionTripulacionGroupEntity> tripulacionAsigGroupEntity = new ArrayList<>();

        try {
            tripulacionAsigGroupEntity.add(obtenerTripulacion(idControlRuta, idRuta, idSolicitud, idVehiculo));

        } catch (Exception e) {
            return ResponseUtil.errorException(e);
        }

        List<ReasignacionTripulacionResponse> listaDeTripulacionAsignada = ReasignacionTripulacionMapper.INSTANCE
                .entityAJson(tripulacionAsigGroupEntity);
        return ResponseUtil.crearRespuestaExito(listaDeTripulacionAsignada);
    }


    /**
     * todo - mover el servicio a un servicio para la tripulacion
     * Recupera la tripulaci&oacute;n.
     *
     * @param idControlRuta
     * @param idRuta
     * @param idSolicitud
     * @param idVehiculo
     * @return
     */
    private ReasignacionTripulacionGroupEntity obtenerTripulacion(Integer idControlRuta, Integer idRuta, Integer idSolicitud,
                                                                  Integer idVehiculo) {
        ReasignacionTripulacionGroupEntity tripulacionAsigEntity = new ReasignacionTripulacionGroupEntity();
        TripulacionAsigCam01Entity getTripulante = null;
        TripulacionAsigCam02Entity getTripulante2 = null;
        ReasignacionTripulacionEntity getChofer = null;

        if (idRuta != null && idSolicitud != null && idVehiculo != null) {
            getChofer = choferRepository.getDatosChoferByidVehiculo(idRuta, idSolicitud, idVehiculo);
            getTripulante = camillero01Repository.getCamillero1ByIdVehiculo(idRuta, idSolicitud, idVehiculo);
            getTripulante2 = camillero02Repository.getCamillero2ByIdVehiculo(idRuta, idSolicitud, idVehiculo);
        } else if (idControlRuta != null) {
            getChofer = choferRepository.getDatosChofer(idControlRuta);
            getTripulante = camillero01Repository.getCamillero1(idControlRuta);
            getTripulante2 = camillero02Repository.getCamillero2(idControlRuta);
        }
        if (getChofer != null) {
            tripulacionAsigEntity.setIdControlRuta(idControlRuta != null ? idControlRuta.toString() : "");
            tripulacionAsigEntity.setIdPersonalAmbulancia(getChofer.getIdPersonalAmbulancia());
            tripulacionAsigEntity.setIdChofer(getChofer.getIdChofer());
            tripulacionAsigEntity.setNombreChofer(getChofer.getNomTripulante());
            tripulacionAsigEntity.setNumTarjetaDig(getChofer.getNumTarjetaDig());
            tripulacionAsigEntity.setMatriculaChofer(getChofer.getCveMatricula());
            tripulacionAsigEntity.setDesMotivoReasig(getChofer.getDesMotivoReasig());
            tripulacionAsigEntity.setDesSiniestro(getChofer.getDesSiniestro());
            tripulacionAsigEntity.setIdReasignacion(getChofer.getIdReasignacion());
            tripulacionAsigEntity.setNombreCamillero1(getTripulante.getNomTripulante());
            tripulacionAsigEntity.setMatriculaCamillero1(getTripulante.getCveMatricula());
            tripulacionAsigEntity.setNombreCamillero2(getTripulante2.getNomTripulante());
            tripulacionAsigEntity.setMatriculaCamillero2(getTripulante2.getCveMatricula());
        }
        return tripulacionAsigEntity;
    }

    @Override
    public Response<List<SiniestrosResponse>> getSiniestro() {
        List<SiniestrosEntity> getSiniestros;
        try {
            getSiniestros = siniestrosRepository.getSiniestro();

            List<SiniestrosResponse> listaDeSiniestros = SiniestrosMapper.INSTANCE.entityAJson(getSiniestros);

            return ResponseUtil.crearRespuestaExito(listaDeSiniestros);
        } catch (Exception e) {
            log.error("Ha ocurrido un error al recuperar los siniestros, {}", e.getMessage());
            return ResponseUtil.errorException(e);
        }

    }

    @Override
    public Response<Integer> crearReasignacion(
            Integer idVehiculo,
            Integer idRuta,
            Integer idChofer,
            String desMotivoReasig,
            String desSiniestro,
            Integer idVehiculoSust,
            Integer idChoferSust,
            Integer idAsignacion,
            String cveMatricula) {

        try {
            if (idVehiculoSust == null) {
                idVehiculoSust = idVehiculo;
            }
            if (idChoferSust == null) {
                idChoferSust = idChofer;
            }

            ReAsignacionRutasEntity reasignacion = new ReAsignacionRutasEntity();

            reasignacionRepository.save(reasignacion);
            // todo - usar el repo de jpa para no usar esos para metros
            reasignacionRepository.save(
                    idVehiculo,
                    idRuta,
                    idChofer,
                    desMotivoReasig,
                    desSiniestro,
                    idVehiculoSust,
                    idChoferSust,
                    idAsignacion,
                    cveMatricula);

            datosRepository.flush();

            Vehiculos vehiculo = vehiculosRepository.getById(idVehiculo);
            if (vehiculo != null && vehiculo.getIdVehiculo() != null) {
                if (!desSiniestro.equals("1")) {
                    vehiculo.setDesEstatusVehiculo(desSiniestro);
                    vehiculosRepository.save(vehiculo);
                }
            }
            return ResponseUtil.crearRespuestaExito(1);
        } catch (Exception e) {
            log.error("Ha ocurrido un error al guardar el registro, {}", e.getMessage());
            return ResponseUtil.errorException(e);
        }
    }

    @Override
    public Response<Integer> update(
            String desSiniestro,
            Integer idVehiculoSust,
            String desMotivoReasignacion,
            Integer idVehiculo,
            Integer idRuta,
            Integer idChofer) {

        try {
            DetalleReAsignacionRutasEntity reasignacion = detalleReAsignacionRutasRepository.getReasignacionByVehiculoChoferRuta(idVehiculo, idRuta, idChofer);
            if (reasignacion != null && reasignacion.getIdReasignacion() != null) {
                if (desSiniestro != null && desSiniestro.trim().length() > 0) {
                    reasignacion.setDesSiniestro(desSiniestro);
                }
                if (idVehiculoSust != null && idVehiculoSust > 0) {
                    reasignacion.setIdVehiculoSust(idVehiculoSust);
                }
                if (desMotivoReasignacion != null && desMotivoReasignacion.trim().length() > 0) {
                    reasignacion.setDesMotivoReasig(desMotivoReasignacion);
                }
                if (idVehiculo != null && idVehiculo > 0) {
                    reasignacion.setIdVehiculo(idVehiculo);
                }
                if (idRuta != null && idRuta > 0) {
                    reasignacion.setIdRuta(idRuta);
                }
                if (idChofer != null && idChofer > 0) {
                    reasignacion.setIdChofer(idChofer);
                }
                detalleReAsignacionRutasRepository.save(reasignacion);
            }
            Vehiculos vehiculo = vehiculosRepository.getById(idVehiculo);
            if (vehiculo != null && vehiculo.getIdVehiculo() != null) {
                if (!desSiniestro.equals("1")) {
                    vehiculo.setDesEstatusVehiculo(desSiniestro);
                    vehiculosRepository.save(vehiculo);
                }
            }
            return ResponseUtil.crearRespuestaExito(1);
        } catch (Exception e) {
            log.error("Ha ocurrido un error al actualizar el registro, {}", e.getMessage());
            return ResponseUtil.errorException(e);
        }
    }
}
