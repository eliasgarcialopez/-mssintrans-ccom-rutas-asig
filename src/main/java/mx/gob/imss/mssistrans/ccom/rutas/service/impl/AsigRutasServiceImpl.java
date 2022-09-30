package mx.gob.imss.mssistrans.ccom.rutas.service.impl;

import lombok.AllArgsConstructor;
import mx.gob.imss.mssistrans.ccom.rutas.dto.*;
import mx.gob.imss.mssistrans.ccom.rutas.model.*;
import mx.gob.imss.mssistrans.ccom.rutas.repository.*;
import mx.gob.imss.mssistrans.ccom.rutas.service.AsigRutasService;
import mx.gob.imss.mssistrans.ccom.rutas.util.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.*;

/**
 * @author opimentel
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Transactional(rollbackOn = SQLException.class)
@AllArgsConstructor
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
    public <T> Response<?> consultaVistaRapida(Integer pagina, Integer tamanio, String orden, String columna,
                                               String idRuta, String idSolicitud) {
        Response<T> respuesta = new Response<>();
        String nomCol = ValidaDatos.getNameColAsignacion(columna);
        Pageable page = PageRequest.of(pagina, tamanio,
                Sort.by(Sort.Direction.fromString(orden.toUpperCase()), nomCol));
        try {
            Page consultaAsignacioRutas = null;
            if (idRuta == null || idRuta.equals(""))
                if (idSolicitud == null || idSolicitud.equals(""))
                    consultaAsignacioRutas = asigRutasRepository.consultaGeneral(page);
                else
                    consultaAsignacioRutas = asigRutasRepository.getConsultaByIdSolicitud(idSolicitud, page);
            else if (idSolicitud == null || idSolicitud.equals(""))
                consultaAsignacioRutas = asigRutasRepository.getConsultaByIdAsignacion(idRuta, page);
            else
                consultaAsignacioRutas = asigRutasRepository.getConsultaById(idRuta, idSolicitud, page);

            final List<AsigRutasResponse> content = (List<AsigRutasResponse>) AsigRutasMapper.INSTANCE
                    .formatearListaArrendados(consultaAsignacioRutas.getContent());

            Page<AsigRutasResponse> objetoMapeado = new PageImpl<>(content, page,
                    consultaAsignacioRutas.getTotalElements());

            return ValidaDatos.resp(respuesta, "Exito", objetoMapeado);
        } catch (Exception e) {
            return ValidaDatos.errorException(respuesta, e);
        }

    }

    @Override
    public Response delete(String idControlRuta) {
        Response respuesta = new Response<>();
        try {
            asigRutasRepository.delete(idControlRuta);
            asigRutasRepository.flush();
            return ValidaDatos.resp(respuesta, "Exito", null);
        } catch (Exception e) {
            return ValidaDatos.errorException(respuesta, e);
        }
    }

    /****** HU006 - 26 **********/
    @Override
    public <T> Response getRutas(Integer idOoad, String rol) {
        Response<T> respuesta = new Response<>();
        List<RutasAsigEntity> consultaGeneral = null;
        try {
            if(rol.equals("Administrador") || rol.equals("Normativo") || idOoad == 9 || idOoad == 39 ) {
                consultaGeneral = rutasRepository.getRutas();
            } else {
                consultaGeneral = rutasRepository.getRutasByOoad(idOoad);
            }
        } catch (Exception e) {
            return ValidaDatos.errorException(respuesta, e);
        }

        List<DatosAsigRutasResponse> listaDeOoad = RutasMapper.INSTANCE.EntityAJson(consultaGeneral);
        return ValidaDatos.resp(respuesta, "Exito", listaDeOoad);
    }

    @Override
    public <T> Response getSolicitudTraslado(DatosUsuarioDTO datosUsuario, Integer idRuta) {
        Response<T> respuesta = new Response<>();
        List<SolTrasladoEntity> consultaGeneral = null;
        try {
            if(datosUsuario.rol.equals("Administrador") || datosUsuario.rol.equals("Normativo") || datosUsuario.IDOOAD == 9 || datosUsuario.IDOOAD == 39 ) {
                consultaGeneral = solicitudTrasladoRepository.getSolicitudTraslado(idRuta);
            } else {
                consultaGeneral = solicitudTrasladoRepository.getSolicitudTraslado(datosUsuario.getIDOOAD(), idRuta);
            }
        } catch (Exception e) {
            return ValidaDatos.errorException(respuesta, e);
        }

        List<SolTrasladoResponse> listaDeSolicituTraslado = SolTrasladoMapper.INSTANCE.EntityAJson(consultaGeneral);
        return ValidaDatos.resp(respuesta, "Exito", listaDeSolicituTraslado);
    }

    @Override
    public <T> Response getEcco(DatosUsuarioDTO datosUsuarios, Integer idRuta) {
        Response<T> respuesta = new Response<>();
        List<EccoEntity> consultaGeneral = null;
        try {
            consultaGeneral = eccoRepository.getEcco(idRuta);
        } catch (Exception e) {
            return ValidaDatos.errorException(respuesta, e);
        }

        List<EccoResponse> listaDeOoad = EccoMapper.INSTANCE.EntityAJson(consultaGeneral);
        return ValidaDatos.resp(respuesta, "Exito", listaDeOoad);
    }

    @SuppressWarnings("unlikely-arg-type")
    @Override
    public <T> Response getDatosAsignacion(Integer idControlRuta, Integer idRuta, Integer idSolicitud,
                                           Integer idVehiculo) {
        Response<T> respuesta = new Response<>();
        DatosAsigEntity consultaGeneral = null;
        try {
            if (idRuta != null && idSolicitud != null && idVehiculo != null) {
                if (!idRuta.equals("") && !idSolicitud.equals("") && !idVehiculo.equals("")) {
                    consultaGeneral = datosRepository.getDatosAsigByidVehiculo(idRuta, idSolicitud, idVehiculo);
                } else if (!idControlRuta.equals(""))
                    consultaGeneral = datosRepository.getDatosAsigByIdCtrlRuta(idControlRuta);
            } else if (idControlRuta != null)
                if (!idControlRuta.equals(""))
                    consultaGeneral = datosRepository.getDatosAsigByIdCtrlRuta(idControlRuta);

        } catch (Exception e) {
            return ValidaDatos.errorException(respuesta, e);
        }

        // todo - hay que hacer tambien la consulta para regresar la RUTA y RUTA_DESTINO
        DatosAsigResponse listaDeSolicituTraslado = DatosAsigMapper.INSTANCE.EntityAJson(consultaGeneral);
        return ValidaDatos.resp(respuesta, "Exito", listaDeSolicituTraslado);
    }

    @Override
    public <T> Response getTripulacionAsignada(Integer idControlRuta, Integer idRuta, Integer idSolicitud,
                                               Integer idVehiculo) {
        Response<T> respuesta = new Response<>();
        List<TripulacionAsigGroupEntity> tripulacionAsigGroupEntity = new ArrayList<TripulacionAsigGroupEntity>();

        try {
            tripulacionAsigGroupEntity = obtenerTripulacion(idControlRuta, idRuta, idSolicitud, idVehiculo);

        } catch (Exception e) {
            return ValidaDatos.errorException(respuesta, e);
        }

        List<TripulacionAsigResponse> listaDeTripulacionAsignada = TripulacionAsigMapper.INSTANCE
                .EntityAJson(tripulacionAsigGroupEntity);
        return ValidaDatos.resp(respuesta, "Exito", listaDeTripulacionAsignada);
    }

    @Override
    public <T> Response getRegistroRecorrido(Integer idControlRuta, Integer idRuta, Integer idSolicitud,
                                             Integer idVehiculo) {
        Response<T> respuesta = new Response<>();
        DatosRegistroRecorridoDTO recorridoDTO = null;
        try {

            if (idRuta != null && idSolicitud != null && idVehiculo != null) {
                if (!idRuta.equals("") && !idSolicitud.equals("") && !idVehiculo.equals("")) {
                    recorridoDTO = regRecorrido1Repository.getRegistroRecorridoByIdRutaIdSolicitudIdVehiculo(idRuta, idSolicitud, idVehiculo);
                } else if (!idControlRuta.equals("")) {
                    recorridoDTO = regRecorrido1Repository.getRegistroRecorridoByRuta(idControlRuta);
                }
            } else if (idControlRuta != null)
                if (!idControlRuta.equals("")) {
                    recorridoDTO = regRecorrido1Repository.getRegistroRecorridoByRuta(idControlRuta);
                }

        } catch (Exception e) {
            return ValidaDatos.errorException(respuesta, e);
        }
        return ValidaDatos.resp(respuesta, "Exito", recorridoDTO);
    }


    @Override
    public Response<?> update(ActualizarControlRutaRequest params) {

        Response<ActualizarControlRutaRequest> respuesta = new Response<>();
        try {
            // todo - validar si la asignacion se cancelo
//			if (params.getEstatusRecorrido())


            // todo - validar el estatus que llega de la asigancaion para que,
            // 		- en el caso de un estatus 3 - terminada hay que actualizar el estatus del ecco para que se pueda
            // 		  en otra solicitud de traslado-> asignacion -> control ruta
            // todo - hay que actualizar el estatus del control de rutas al 3 - Terminada
            // 		- tambien hay que considerar que cuando se cancela la asignacion hay que liberar
            final String estatus = params.getEstatusRecorrido();
            final EstatusControlRutasEnum[] values = EstatusControlRutasEnum.values();
            final Optional<EstatusControlRutasEnum> estatusEnum = Arrays
                    .stream(values)
                    .filter(value -> Objects.equals(value.getValor(), estatus))
                    .findFirst();

            if (!estatusEnum.isPresent()) {
                throw new Exception("El estatus no es correcto, revisar el valor " + estatus);
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
                // todo - cambiar el estatus del control de rutas
                final Integer idControlRuta = params.getIdControlRuta();
                final ControlRutas controlRutas = controlRutasRepository.findByIdControlRuta(idControlRuta)
                        .orElseThrow(() -> new Exception("No se ha encontrado el control de rutas con id: " + idControlRuta));
                controlRutas.setDesEstatusAsigna(estatusAsignacion);
                controlRutasRepository.save(controlRutas);

                // se libera solo cuando esta terminada o cancelada
                if (estatusAsignacion.equals(EstatusControlRutasEnum.Cancelado.getValor()) ||
                        estatusAsignacion.equals(EstatusControlRutasEnum.Terminado.getValor())) {
                    final Integer idVehiculo = controlRutas.getIdVehiculo().getIdVehiculo();
                    final Vehiculos vehiculo = vehiculosRepository.findById(idVehiculo)
                            .orElseThrow(() -> new Exception("No se ha encontrado el vehiculo con id: " + idVehiculo));
                    // colocar el estatus 8 hace que el vehiculo pueda ser asignado nuevamente para atender otra solicitud
                    vehiculo.setDesEstatusVehiculo("8");
                    vehiculosRepository.save(vehiculo);
                }
            }

        } catch (Exception e) {
            return ValidaDatos.errorException(respuesta, e);
        }
        return ValidaDatos.resp(respuesta, "Exito", null);
    }

    @Override
    public <T> Response getDetalleRutasyAsig(Integer idControlRuta) {
        Response<T> respuesta = new Response<>();
        DatosAsigEntity datosAsig = null;
        List<TripulacionAsigGroupEntity> tripulacionAsigEntity = new ArrayList<TripulacionAsigGroupEntity>();
        RegistroRecorridoEntity registroRecorrido = null;
        List<DetRutasAsigEntity> detalleRutasAsignaciones = new ArrayList<DetRutasAsigEntity>();
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
            return ValidaDatos.errorException(respuesta, e);
        }

        List<DetRutasAsignacionesResponse> listaDeSolicituTraslado = DatosAsigMapper.INSTANCE
                .EntityAJson(detalleRutasAsignaciones);
        return ValidaDatos.resp(respuesta, "Exito", listaDeSolicituTraslado);
    }

    private List<TripulacionAsigGroupEntity> obtenerTripulacion(Integer idControlRuta, Integer idRuta, Integer idSolicitud,
                                                                Integer idVehiculo) {
        List<TripulacionAsigGroupEntity> tripulacionAsigGroupEntity = new ArrayList<TripulacionAsigGroupEntity>();

        TripulacionAsigGroupEntity tripulacionAsigEntity = new TripulacionAsigGroupEntity();
        TripulacionAsigCam01Entity getTripulante = null;
        TripulacionAsigCam02Entity getTripulante2 = null;
        TripulacionAsigEntity getChofer = null;


        if (idRuta != null && idSolicitud != null && idVehiculo != null) {
            if (!idRuta.equals("") && !idSolicitud.equals("") && !idVehiculo.equals("")) {
                getChofer = choferRepository.getDatosChoferByidVehiculo(idRuta, idSolicitud, idVehiculo);
                getTripulante = camillero01Repository.getCamillero1ByIdVehiculo(idRuta, idSolicitud, idVehiculo);
                getTripulante2 = camillero02Repository.getCamillero2ByIdVehiculo(idRuta, idSolicitud, idVehiculo);
            } else if (!idControlRuta.equals("")) {
                getChofer = choferRepository.getDatosChofer(idControlRuta);
                getTripulante = camillero01Repository.getCamillero1(idControlRuta);
                getTripulante2 = camillero02Repository.getCamillero2(idControlRuta);
            }
        } else if (idControlRuta != null)
            if (!idControlRuta.equals("")) {
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
    public <T> Response<?> getControlRutas(Integer pagina, Integer tamanio, String orden, String columna,
                                           String idAsignacion, String idSolicitud) {
        Response<T> respuesta = new Response<>();
        String nomCol = ValidaDatos.getNameColAsignacion(columna);
        Pageable page = PageRequest.of(pagina, tamanio,
                Sort.by(Sort.Direction.fromString(orden.toUpperCase()), nomCol));
        try {
            Page consultaAsignacioRutas = null;
            if (idAsignacion != null && !idAsignacion.equals("") && (idSolicitud == null || idSolicitud.equals(""))) {
                consultaAsignacioRutas = asigRutasRepository.getConsultaByIdControlRuta(idAsignacion, page);
            } else if ((idAsignacion == null || idAsignacion.equals("")) && (idSolicitud != null && !idSolicitud.equals(""))) {
                consultaAsignacioRutas = asigRutasRepository.getConsultaByIdSolicitud(idSolicitud, page);
            } else if ((idAsignacion != null && !idAsignacion.equals("")) && (idSolicitud != null && !idSolicitud.equals(""))) {
                consultaAsignacioRutas = asigRutasRepository.getConsultaByIdControlRutaAndIdSolicitud(idAsignacion, idSolicitud, page);
            } else {
                consultaAsignacioRutas = asigRutasRepository.consultaGeneral(page);
            }
            final List<AsigRutasResponse> content = (List<AsigRutasResponse>) AsigRutasMapper.INSTANCE
                    .formatearListaArrendados(consultaAsignacioRutas.getContent());

            Page<AsigRutasResponse> objetoMapeado = new PageImpl<>(content, page,
                    consultaAsignacioRutas.getTotalElements());

            return ValidaDatos.resp(respuesta, "Exito", objetoMapeado);
        } catch (Exception e) {
            return ValidaDatos.errorException(respuesta, e);
        }

    }

    @SuppressWarnings("unlikely-arg-type")
    @Override
    public <T> Response getDatosControlRutaById(Integer idControlRuta) {
        Response<T> respuesta = new Response<>();
        DatosAsigEntity consultaGeneral = null;
        try {
            consultaGeneral = datosRepository.getDatosByIdCtrlRuta(idControlRuta);
        } catch (Exception e) {
            return ValidaDatos.errorException(respuesta, e);
        }

        DatosAsigResponse listaDeSolicituTraslado = DatosAsigMapper.INSTANCE.EntityAJson(consultaGeneral);
        return ValidaDatos.resp(respuesta, "Exito", listaDeSolicituTraslado);
    }

}
