package mx.gob.imss.mssistrans.ccom.rutas.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.dto.*;
import mx.gob.imss.mssistrans.ccom.rutas.exceptions.EstatusException;
import mx.gob.imss.mssistrans.ccom.rutas.model.*;
import mx.gob.imss.mssistrans.ccom.rutas.repository.*;
import mx.gob.imss.mssistrans.ccom.rutas.service.AsigRutasService;
import mx.gob.imss.mssistrans.ccom.rutas.util.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author opimentel
 */
@Transactional(rollbackOn = SQLException.class)
@AllArgsConstructor
@Slf4j
@Service
public class AsigRutasServiceImpl implements AsigRutasService {

    private final AsigRutasRepository asigRutasRepository;
    private final RutasAsigRepository rutasRepository;
    private final SolTrasladoRepository solicitudTrasladoRepository;
    private final EccoRepository eccoRepository;
    private final DatosAsigRepository datosRepository;
    private final TripulacionAsigRepository choferRepository;
    private final TripulacionAsigCamillero01Repository camillero01Repository;
    private final TripulacionAsigCamillero02Repository camillero02Repository;
    private final RegistroRecorridoRepository regRecorrido1Repository;
    private final RutasDestinosRepository rutasDestinoRepository;
    private final ControlRutasRepository controlRutasRepository;
    private final VehiculosRepository vehiculosRepository;

    @Override
    public Response<Page<AsigRutasResponse>> consultaVistaRapida(
            Pageable pageable,
            String idRuta,
            String idSolicitud) {

        try {
            Page<AsigRutasEntity> consultaAsignacioRutas;

            if (idRuta == null || idRuta.equals("")) {
                if (idSolicitud == null || idSolicitud.equals("")) {
                    consultaAsignacioRutas = asigRutasRepository.consultaGeneral(pageable);
                } else {
                    consultaAsignacioRutas = asigRutasRepository.getConsultaByIdSolicitud(idSolicitud, pageable);
                }
            } else if (idSolicitud == null || idSolicitud.equals("")) {
                consultaAsignacioRutas = asigRutasRepository.getConsultaByIdAsignacion(idRuta, pageable);
            } else {
                consultaAsignacioRutas = asigRutasRepository.getConsultaById(idRuta, idSolicitud, pageable);
            }

            final List<AsigRutasResponse> content = AsigRutasMapper.INSTANCE
                    .formatearListaArrendados(consultaAsignacioRutas.getContent());

            Page<AsigRutasResponse> objetoMapeado = new PageImpl<>(
                    content, pageable,
                    consultaAsignacioRutas.getTotalElements());

            return ResponseUtil.crearRespuestaExito(objetoMapeado);
        } catch (Exception e) {
            return ResponseUtil.errorException(e);
        }

    }

    @Override
    public Response<Integer> delete(String idControlRuta) {
        try {
            // todo - hay que regresar al paso anterior la asignacion de ruta
            //      - en este punto cuando se elmin
            try {
                final Optional<ControlRutas> controlRutas = controlRutasRepository.findByIdControlRuta(Integer.parseInt(idControlRuta));
                if(controlRutas.isPresent()){
                    controlRutas.get().setActivo(false);
                    controlRutas.get().getIdSolcitud().setActivo(false);
                    controlRutas.get().getIdVehiculo().setDesEstatusVehiculo("8");
                    controlRutas.get().getIdVehiculo().setIndAsignado(false);
                    //controlRutas.getTripulacion().setActivo(false);
                    controlRutasRepository.save(controlRutas.get());
                }
            } catch (Exception e){
                log.error("Error al eliminar el control de ruta {}", e);
            }

            asigRutasRepository.delete(idControlRuta);
            asigRutasRepository.flush();
            return ResponseUtil.crearRespuestaExito(null);
        } catch (Exception e) {
            log.error("Ha ocurrido un error al eliminar la asignacion de ruta, {}", e.getMessage());
            return ResponseUtil.errorException(e);
        }
    }

    /****** HU006 - 26 **********/
    @Override
    public Response<List<DatosAsigRutasResponse>> getRutas(Integer idOoad, String rol) {
        List<RutasAsigEntity> consultaGeneral = null;
        try {
            if (rol.equals("Administrador") || rol.equals("Normativo") || idOoad == 9 || idOoad == 39) {
                consultaGeneral = rutasRepository.getRutas();
            } else {
                consultaGeneral = rutasRepository.getRutasByOoad(idOoad);
            }
        } catch (Exception e) {
            return ResponseUtil.errorException(e);
        }

        List<DatosAsigRutasResponse> listaDeOoad = RutasMapper.INSTANCE.entityAJson(consultaGeneral);

        return ResponseUtil.crearRespuestaExito(listaDeOoad);
    }

    @Override
    public Response<List<SolTrasladoResponse>> getSolicitudTraslado(DatosUsuarioDTO datosUsuario, Integer idRuta) {
        List<SolTrasladoEntity> consultaGeneral;
        try {
            if (datosUsuario.rol.equals("Administrador") || datosUsuario.rol.equals("Normativo") || datosUsuario.idOoad == 9 || datosUsuario.idOoad == 39) {
                consultaGeneral = solicitudTrasladoRepository.getSolicitudTraslado(idRuta);
            } else {
                consultaGeneral = solicitudTrasladoRepository.getSolicitudTraslado(datosUsuario.getIdOoad(), idRuta);
            }
        } catch (Exception e) {
            return ResponseUtil.errorException(e);
        }

        List<SolTrasladoResponse> listaDeSolicituTraslado = SolTrasladoMapper.INSTANCE.entityAJson(consultaGeneral);
        return ResponseUtil.crearRespuestaExito(listaDeSolicituTraslado);
    }

    @Override
    public Response<List<EccoResponse>> getEcco(DatosUsuarioDTO datosUsuarios, Integer idRuta) {

        List<EccoEntity> consultaGeneral;
        try {
            consultaGeneral = eccoRepository.getEcco(idRuta);
        } catch (Exception e) {
            return ResponseUtil.errorException(e);
        }
        List<EccoResponse> listaDeOoad = consultaGeneral.stream()
                .map(EccoMapper.INSTANCE::eccoEntityToJson)
                .collect(Collectors.toList());

        return ResponseUtil.crearRespuestaExito(listaDeOoad);
    }

    @Override
    public Response<DatosAsigResponse> getDatosAsignacion(
            Integer idControlRuta, Integer idRuta,
            Integer idSolicitud, Integer idVehiculo) {

        DatosAsigEntity consultaGeneral = null;
        try {
            if (idRuta != null && idSolicitud != null && idVehiculo != null) {
                consultaGeneral = datosRepository.getDatosAsigByidVehiculo(idRuta, idSolicitud, idVehiculo);
            } else if (idControlRuta != null) {
                consultaGeneral = datosRepository.getDatosAsigByIdCtrlRuta(idControlRuta);
            }

        } catch (Exception e) {
            return ResponseUtil.errorException(e);
        }

        DatosAsigResponse listaDeSolicituTraslado = DatosAsigMapper.INSTANCE.entityAJson(consultaGeneral);

        return ResponseUtil.crearRespuestaExito(listaDeSolicituTraslado);
    }

    @Override
    public Response<List<TripulacionAsigResponse>> getTripulacionAsignada(
            Integer idControlRuta, Integer idRuta,
            Integer idSolicitud, Integer idVehiculo) {

        List<TripulacionAsigGroupEntity> tripulacionAsigGroupEntity;
        try {
            tripulacionAsigGroupEntity = obtenerTripulacion(idControlRuta, idRuta, idSolicitud, idVehiculo);
        } catch (Exception e) {
            return ResponseUtil.errorException(e);
        }

        List<TripulacionAsigResponse> listaDeTripulacionAsignada = TripulacionAsigMapper.INSTANCE
                .entityAJson(tripulacionAsigGroupEntity);

        return ResponseUtil.crearRespuestaExito(listaDeTripulacionAsignada);
    }

    @Override
    public Response<DatosRegistroRecorridoDTO> getRegistroRecorrido(
            Integer idControlRuta, Integer idRuta,
            Integer idSolicitud,
            Integer idVehiculo) {

        DatosRegistroRecorridoDTO recorridoDTO = null;
        try {

            if (idRuta != null && idSolicitud != null && idVehiculo != null) {
                recorridoDTO = regRecorrido1Repository.getRegistroRecorridoByIdRutaIdSolicitudIdVehiculo(idRuta, idSolicitud, idVehiculo);
            } else if (idControlRuta != null) {
                recorridoDTO = regRecorrido1Repository.getRegistroRecorridoByRuta(idControlRuta);
            }

        } catch (Exception e) {
            return ResponseUtil.errorException(e);
        }
        return ResponseUtil.crearRespuestaExito(recorridoDTO);
    }


    @Override
    public Response<Integer> update(ActualizarControlRutaRequest params) {

        try {
            final String estatus = params.getEstatusRecorrido();
            final EstatusControlRutasEnum[] values = EstatusControlRutasEnum.values();
            final Optional<EstatusControlRutasEnum> estatusEnum = Arrays
                    .stream(values)
                    .filter(value -> Objects.equals(value.getValor(), estatus))
                    .findFirst();

            if (!estatusEnum.isPresent()) {
                throw new EstatusException("El estatus no es correcto, revisar el valor " + estatus);
            } else {
                final Integer idRutaOrigen = params.getIdRutaOrigen();
                RutasAsigEntity rutaOrigen = rutasRepository.findById(idRutaOrigen)
                        .orElseThrow(() -> new Exception("No se ha encontrado la ruta origen con id " + idRutaOrigen));

                final Integer idRutaDestino = params.getIdRutaDestino();
                RutasDestinos rutaDestino = rutasDestinoRepository.findById(idRutaDestino)
                        .orElseThrow(() -> new Exception("No se ha encontrado la ruta destino con id " + idRutaDestino));

                rutaOrigen.setHoraInicio(params.getHoraInicioOrigen());
                rutaOrigen.setHoraFin(params.getHoraFinOrigen());
                rutaDestino.setTimHoraInicio(params.getHoraInicioDestino());
                rutaDestino.setTimHoraFin(params.getHoraFinDestino());
                rutasRepository.save(rutaOrigen);
                rutasDestinoRepository.save(rutaDestino);

                final String estatusAsignacion = estatusEnum.get().getValor();

                final Integer idControlRuta = params.getIdControlRuta();
                final Integer idSolicitud = params.getIdSolicitud();

                final ControlRutas controlRutas = idControlRuta != null ?
                        controlRutasRepository.findByIdControlRuta(idControlRuta)
                                .orElseThrow(() -> new Exception("No se ha encontrado el control de rutas con id: " + idControlRuta)) :
                        controlRutasRepository.findByIdSolicitud(idSolicitud)
                                .orElseThrow(() -> new Exception("No se ha encontrado el control de rutas relacionado a la solicitud: " + idSolicitud));

                controlRutas.setDesEstatusAsigna(estatusAsignacion);
                // se coloca el string que se captura en la pantalla
                controlRutas.setDesTipoIncidente(params.getIdTipoIncidente());
                controlRutasRepository.save(controlRutas);

                // se libera solo cuando esta terminada o cancelada
                if (estatusAsignacion.equals(EstatusControlRutasEnum.Cancelado.getValor()) ||
                        estatusAsignacion.equals(EstatusControlRutasEnum.Terminado.getValor())) {
                    final Integer idVehiculo = controlRutas.getVehiculo().getIdVehiculo();
                    final Vehiculos vehiculo = vehiculosRepository.findById(idVehiculo)
                            .orElseThrow(() -> new Exception("No se ha encontrado el vehiculo con id: " + idVehiculo));
                    // colocar el estatus 8 hace que el vehiculo pueda ser asignado nuevamente para atender otra solicitud
                    vehiculo.setDesEstatusVehiculo("8");
                    vehiculo.setIndAsignado(false);
                    vehiculosRepository.save(vehiculo);
                }
            }

            return ResponseUtil.crearRespuestaExito(1);
        } catch (Exception e) {
            return ResponseUtil.errorException(e);
        }
    }

    @Override
    public Response<List<DetRutasAsignacionesResponse>> getDetalleRutasyAsig(Integer idControlRuta) {

        DatosAsigEntity datosAsig;
        List<TripulacionAsigGroupEntity> tripulacionAsigEntity;
        RegistroRecorridoEntity registroRecorrido;
        List<DetRutasAsigEntity> detalleRutasAsignaciones = new ArrayList<>();
        DetRutasAsigEntity detRutasAsignaciones = new DetRutasAsigEntity();
        try {
            datosAsig = datosRepository.getDatosAsigByIdCtrlRuta(idControlRuta);
            tripulacionAsigEntity = obtenerTripulacion(idControlRuta, null, null, null);
            registroRecorrido = regRecorrido1Repository.getRegistroRecorrido(idControlRuta);
            detRutasAsignaciones.setDatosAsignacion(datosAsig);
            detRutasAsignaciones.setTripulacion(tripulacionAsigEntity);
            detRutasAsignaciones.setRegistroRecorrido(registroRecorrido);
            detalleRutasAsignaciones.add(detRutasAsignaciones);
        } catch (Exception e) {
            return ResponseUtil.errorException(e);
        }

        List<DetRutasAsignacionesResponse> listaDeSolicituTraslado = detalleRutasAsignaciones.stream()
                .map(DatosAsigMapper.INSTANCE::entityAJson)
                .collect(Collectors.toList());

        return ResponseUtil.crearRespuestaExito(listaDeSolicituTraslado);
    }

    private List<TripulacionAsigGroupEntity> obtenerTripulacion(Integer idControlRuta, Integer idRuta, Integer idSolicitud,
                                                                Integer idVehiculo) {
        List<TripulacionAsigGroupEntity> tripulacionAsigGroupEntity = new ArrayList<>();

        TripulacionAsigGroupEntity tripulacionAsigEntity = new TripulacionAsigGroupEntity();
        TripulacionAsigCam01Entity getTripulante = null;
        TripulacionAsigCam02Entity getTripulante2 = null;
        TripulacionAsigEntity getChofer = null;


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
            tripulacionAsigEntity.setIdControlRuta(getChofer.getIdControlRuta());
            tripulacionAsigEntity.setIdPersonalAmbulancia(getChofer.getIdPersonalAmbulancia());
            tripulacionAsigEntity.setNombreChofer(getChofer.getNomTripulante());
            tripulacionAsigEntity.setNumTarjetaDig(getChofer.getNumTarjetaDig());
            tripulacionAsigEntity.setMatriculaChofer(getChofer.getCveMatricula());
            tripulacionAsigEntity.setNombreCamillero1(getTripulante == null ? "" : getTripulante.getNomTripulante());
            tripulacionAsigEntity.setMatriculaCamillero1(getTripulante == null ? "" : getTripulante.getCveMatricula());
            tripulacionAsigEntity.setNombreCamillero2(getTripulante2 == null ? "" : getTripulante2.getNomTripulante());
            tripulacionAsigEntity.setMatriculaCamillero2(getTripulante2 == null ? "" : getTripulante2.getCveMatricula());
            tripulacionAsigGroupEntity.add(tripulacionAsigEntity);
        }
        return tripulacionAsigGroupEntity;
    }

    @Override
    public Response<Page<AsigRutasResponse>> getControlRutas(
            Pageable pageable, String idAsignacion, String idSolicitud) {
        try {
            Page<AsigRutasEntity> consultaAsignacioRutas = null;
            if (idAsignacion != null && !idAsignacion.equals("") && (idSolicitud == null || idSolicitud.equals(""))) {
                consultaAsignacioRutas = asigRutasRepository.getConsultaByIdControlRuta(idAsignacion, pageable);
            } else if ((idAsignacion == null || idAsignacion.equals("")) && (idSolicitud != null && !idSolicitud.equals(""))) {
                consultaAsignacioRutas = asigRutasRepository.getConsultaByIdSolicitud(idSolicitud, pageable);
            } else if (idAsignacion != null && !idAsignacion.equals("")) {
                consultaAsignacioRutas = asigRutasRepository.getConsultaByIdControlRutaAndIdSolicitud(idAsignacion, idSolicitud, pageable);
            } else {
                consultaAsignacioRutas = asigRutasRepository.consultaGeneral(pageable);
            }
            final List<AsigRutasResponse> content = AsigRutasMapper.INSTANCE
                    .formatearListaArrendados(consultaAsignacioRutas.getContent());

            Page<AsigRutasResponse> objetoMapeado = new PageImpl<>(content, pageable,
                    consultaAsignacioRutas.getTotalElements());

            return ResponseUtil.crearRespuestaExito(objetoMapeado);
        } catch (Exception e) {
            return ResponseUtil.errorException(e);
        }

    }

    @Override
    public Response<DatosControlRutaDTO> getDatosControlRutaById(Integer idControlRuta) {
        DatosControlRutaDTO datosControlRutaDTO;
        try {
            datosControlRutaDTO = datosRepository.getDatosControlRutaByIdCtrlRuta(idControlRuta);
        } catch (Exception e) {
            return ResponseUtil.errorException(e);
        }

        return ResponseUtil.crearRespuestaExito(datosControlRutaDTO);
    }

}
