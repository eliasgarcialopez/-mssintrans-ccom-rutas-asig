package mx.gob.imss.mssistrans.ccom.rutas.service.impl;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Asignacion;
import mx.gob.imss.mssistrans.ccom.rutas.dto.*;
import mx.gob.imss.mssistrans.ccom.rutas.model.*;
import mx.gob.imss.mssistrans.ccom.rutas.repository.*;
import mx.gob.imss.mssistrans.ccom.rutas.service.ControlRutasForaneasService;
import mx.gob.imss.mssistrans.ccom.rutas.util.EstatusSolicitudesEnum;
import mx.gob.imss.mssistrans.ccom.rutas.util.EstatusVehiculosEnum;
import mx.gob.imss.mssistrans.ccom.rutas.util.ResponseUtil;
import mx.gob.imss.mssistrans.ccom.rutas.util.Utility;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
@Slf4j
@Transactional
public class ControlRutasForaneasServiceImpl implements ControlRutasForaneasService {
    @Autowired
    private ControlRutasForaneasRepository controlRutasForaneasRepository;
    @Autowired
    private UnidadAdscripcionRepository unidadAdscripcionRepository;
    @Autowired
    private ModuloAmbulanciaRepository moAmbulanciaRepository;
    @Autowired
    private VehiculosRepository vehiculoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private SolicitudTrasladoRepository solicitudTrasladoRepository;
    @Autowired
    private RutasRepository rutasRepository;
    @Autowired
    private TripulacionRepository tripulacionRepository;
    @Autowired
    private ViaticosRepository viaticosRepository;
    @Autowired
    private ZonaAtencionRepository zonaAtencionRepository;
    @Autowired
    private RutasDestinosRepository destinosRepository;
    @Autowired
    private AsignacionesServiceImpl asignaciones;

    @Override
    public Response<Page<ControlRutasTablaResponse>> consultarRutas(DatosUsuario datosUsuarios, Pageable pageable) {
        try {
            log.info("las rutas page number, {}", pageable.getPageNumber());
            log.info("los rutas, page size {}", pageable.getPageSize());

            //Validar si es adminsitrador...
            final Page<ControlRutas> result = datosUsuarios.getRol().equals("Administrador") ||
                    datosUsuarios.getRol().equals("Normativo") ||
                    datosUsuarios.idOoad == 9 ||
                    datosUsuarios.idOoad == 39
                    ? controlRutasForaneasRepository.findAll(pageable)
                    : controlRutasForaneasRepository.findAll(pageable, datosUsuarios.getIdOoad());

            List<ControlRutasTablaResponse> content = new ArrayList<>();
            for (ControlRutas rutas : result) {
                ControlRutasTablaResponse rutasTabla = new ControlRutasTablaResponse();
                rutasTabla.setEcco(rutas.getVehiculo().getCveEcco());
                rutasTabla.setIdControlRuta(rutas.getIdControlRuta());
                rutasTabla.setIdRuta(rutas.getRuta().getIdRuta());
                rutasTabla.setFolioRuta(rutas.getRuta().getNumFolioRuta());
                rutasTabla.setIdSolicitudTraslado(rutas.getIdSolcitud().getIdSolicitud());
                rutasTabla.setModulo(rutas.getModulo().getDesNombre());

                Optional<UnidadAdscripcion> origen = unidadAdscripcionRepository
                        .findByIdUnidadAdscripcionAndActivoEquals(rutas.getIdSolcitud().getCveOrigen(), true);

                origen.ifPresent(
                        unidadAdscripcion -> rutasTabla.setOrigen(unidadAdscripcion.getNomUnidadAdscripcion()));
                content.add(rutasTabla);
            }

            Page<ControlRutasTablaResponse> objetoMapeado = new PageImpl<>(
                    content, pageable, result.getTotalElements());
            return ResponseUtil.crearRespuestaExito(objetoMapeado);
        } catch (Exception exception) {

            log.error("Ha ocurrido un error al consultar las rutas, {}", ExceptionUtils.getStackTrace(exception));
//            response.setError(true);
//            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            response.setMensaje(exception.getMessage());
            return ResponseUtil.errorException(exception);
        }
    }

    @Override
    public Respuesta<ControlRutasForaneasResponse> consultarRutas(Integer idControlRuta) {
        Respuesta<ControlRutasForaneasResponse> response = new Respuesta<>();
        try {
            String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            log.info("usuario {}", user);
            if (user.equals("denegado")) {

                response.setError(false);
                response.setCodigo(HttpStatus.UNAUTHORIZED.value());
                response.setMensaje(user);
                return response;
            }
            log.info("Consultando la ruta");
            Gson gson = new Gson();
            DatosUsuario datosUsuarios = gson.fromJson(user, DatosUsuario.class);
            Integer idOOAD = datosUsuarios.getIdOoad();

            Optional<ModuloAmbulancia> opModulo = moAmbulanciaRepository.findByIdOOADAndActivoEquals(idOOAD, true);


            ControlRutas result = controlRutasForaneasRepository.findByIdControlRuta(idControlRuta)
                    .orElseThrow(() ->
                            new Exception("No se ha encontrado el control de ruta con id: " + idControlRuta));
            ControlRutasResponse rutasResponse = new ControlRutasResponse();
            if (result != null) {
                ControlRutas ruta = result;

                // todo - mover la recuperacion del origen y el destino de un servicio de unidadAdscripcionService
                Optional<UnidadAdscripcion> origen = unidadAdscripcionRepository
                        .findByIdUnidadAdscripcionAndActivoEquals(ruta.getIdSolcitud().getCveOrigen(), true);
                origen.ifPresent(rutasResponse::setOrigen);

                Optional<UnidadAdscripcion> destino = unidadAdscripcionRepository
                        .findByIdUnidadAdscripcionAndActivoEquals(ruta.getIdSolcitud().getCveDestino(), true);
                destino.ifPresent(rutasResponse::setDestino);

                rutasResponse.setFechaRuta(ruta.getFechaInicioAsigna().toString());
                rutasResponse.setHoraRuta(ruta.getTimInicioAsigna().toString());
                rutasResponse.setIdSolicitudTraslado(ruta.getIdSolcitud());
                rutasResponse.setModulo(ruta.getModulo());
                rutasResponse.setNumFolioTarjeta(ruta.getNumFolioTarjetaCombustible());
                rutasResponse.setRuta(ruta.getRuta());

                // todo - recuperar modulo, pasar la logica al modulo de ambulanciaService
                if (opModulo.isPresent()) {
                    ModuloAmbulancia moduloAmbulancia = opModulo.get();
                    final Integer idModulo = moduloAmbulancia.getIdModulo();
                    ZonaAtencion zonaAtencion = zonaAtencionRepository
                            .findByIdModuloAndActivoEquals(idModulo, true)
                            .orElseThrow(() ->
                                    new Exception("No se ha encontrado la zona de atencion ligada al modulo: " + idModulo));

                    Integer idZona = zonaAtencion.getIdZona();

                    Integer totalVA = vehiculoRepository.countTotalVehiculoAsignados(idZona);

                    rutasResponse.setTotalVehiculosAsignados(totalVA);

                    Integer totalVD = vehiculoRepository.countTotalVehiculoDisponibles(idZona);

                    rutasResponse.setTotalVehiculosDisponibles(totalVD);

                    Integer totalMan = vehiculoRepository.countTotalVehiculoMantenimiento(idZona);

                    rutasResponse.setTotalVehiculosMantenimiento(totalMan);

                    Integer totalSin = vehiculoRepository.countTotalVehiculoSiniestrados(idZona);

                    rutasResponse.setTotalVehiculosSiniestrados(totalSin);
                }

                TripulacionResponse tripRes = new TripulacionResponse();
                if (ruta.getTripulacion() != null) {
                    TripulacionInterfaceResponse tripOp = tripulacionRepository.findTripulacionIdVehiculo(ruta.getVehiculo().getIdVehiculo());

                    if (tripOp != null) {


                        tripRes.setIdTripulacion(tripOp.getidTripulacion());
                        tripRes.setIdVehiculo(tripOp.getidVehiculo());

                        tripRes.setCveMatriculaCamillero1(tripOp.getcveMatriculaCamillero1());
                        tripRes.setCveMatriculaCamillero2(tripOp.getcveMatriculaCamillero2());
                        tripRes.setCveMatriculaChofer(tripOp.getcveMatriculaChofer());
                        tripRes.setNombreCamillero1(tripOp.getnombreCamillero1());
                        tripRes.setNombreCamillero2(tripOp.getnombreCamillero2());
                        tripRes.setNombreChofer(tripOp.getnombreChofer());

                        tripRes.setIdTripulacion(ruta.getTripulacion().getIdTripulacion());
                        tripRes.setIdVehiculo(ruta.getTripulacion().getIdVehiculo());
                        tripRes.setFecFecha(ruta.getTripulacion().getFecFecha().toString());
                        rutasResponse.setTripulacion(tripRes);
                    } else {
                        log.info("No se econtro la tripulacion por id vehiculo " + ruta.getVehiculo().getIdVehiculo());
                        response.setDatos(new ControlRutasForaneasResponse());
                        response.setError(false);
                        response.setMensaje("Exito");
                        response.setCodigo(HttpStatus.OK.value());
                        return response;
                    }

                }
                //06.01 a 14:00, vespertino horario del turno 14:01 a 19:00,nocturno o especial horario del turno 19.01 a 06:00
                if (ruta.getIdSolcitud() != null && ruta.getIdSolcitud().getTimSolicitud() != null) {
                    String turno = Utility.getTurnoByHr(ruta.getIdSolcitud().getTimSolicitud());
                    rutasResponse.setTurno(turno);
                }

                rutasResponse.setVehiculo(ruta.getVehiculo());

                final Viaticos viaticos = viaticosRepository.findByControlRutasIdControlRuta(idControlRuta);
                if (viaticos != null) {
                    rutasResponse.setViaticosCasetas(String.valueOf(viaticos.getViaticosCaseta()));
                    rutasResponse.setViaticosChofer(String.valueOf(viaticos.getViaticosChofer()));
                    rutasResponse.setViaticosCamillero1(String.valueOf(viaticos.getViaticosCamillero1()));
                    rutasResponse.setViaticosCamillero2(String.valueOf(viaticos.getViaticosCamillero2()));
                }

                ResponseUtil.crearRespuestaExito(rutasResponse);
            }

        } catch (Exception exception) {
            log.info("Ha ocurrido un error al consultar la ruta con Id {}, error: {}", idControlRuta, ExceptionUtils.getStackTrace(exception));
            response.setError(true);
            response.setMensaje(exception.getMessage());
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    @Override
    public Respuesta<Integer> crearRuta(ControlRutasForaneasRequest params) {
        Respuesta<Integer> response = new Respuesta<>();
        try {
            // Pendiente crear el campo foliador....
            String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            log.info("usuario {}", user);
            if (user.equals("denegado")) {

                response.setError(false);
                response.setCodigo(HttpStatus.UNAUTHORIZED.value());
                response.setMensaje(user);
                return response;
            }

            log.info("Creando la ruta origen...");

            Rutas ruta = new Rutas();
            ruta.setActivo(true);
            ruta.setMatricula(params.getCveMatricula());
            ruta.setFechaAlta(LocalDate.now());
            ruta.setSistema(true);
            ruta.setIndRutaForanea(true);

            log.info("Obtenemos solicitud");

            Optional<SolicitudTraslado> solicitud = solicitudTrasladoRepository
                    .findById(params.getIdSolicitudTraslado());

            if (solicitud.isPresent()) {
                SolicitudTraslado solicitudTraslado = solicitud.get();

                // ruta origen
                ruta.setIdOrigen(solicitudTraslado.getCveOrigen());
                // ruta destino
                ruta.setIdUnidadDestino(solicitudTraslado.getCveDestino() == null ? null : solicitudTraslado.getCveDestino());
                ruta.setIdUnidadSolcitante(solicitudTraslado.getIdUnidadSolicitante());

                log.info("Actualizando estatus a asignada ");
                solicitudTraslado.setDesEstatusSolicitud(EstatusSolicitudesEnum.Asignada.getValor()); // estatus asignada

                solicitudTrasladoRepository.save(solicitudTraslado);

            } else log.info("Solicitud no encontrado" + params.getIdSolicitudTraslado());

            //pendiente
            //ruta.setNumFolioRuta(user)

            RutasDestinos destinos = new RutasDestinos();
            destinos.setActivo(true);
            destinos.setIdUnidadDestino(ruta.getIdUnidadDestino());
            destinos.setIndiceSistema(true);

            destinos.setRuta(ruta);

            ruta.getDestinos().add(destinos);

            Gson gson = new Gson();
            DatosUsuario datosUsuarios = gson.fromJson(user, DatosUsuario.class);

            //Asignadmos el OOAD ala que pertence el ususario

            ruta.setIdOOADFiltro(datosUsuarios.getIdOoad());

            //Se obtiene el folio

            //pendiente definir las siglas ooad
            String folio = Utility.generateFolio("", ruta.getIdRuta() != null ? ruta.getIdRuta() : 1);
            //Actualizamos folio

            ruta.setNumFolioRuta(folio);

            // Generamos registro de control de ruta
            ControlRutas controlRutas = new ControlRutas();
            controlRutas.setActivo(true);
            controlRutas.setMatricula(ruta.getMatricula());
            controlRutas.setFechaAlta(LocalDate.now());

            if (solicitud.isPresent()) controlRutas.setIdSolcitud(solicitud.get());

            else log.info("Solicitud no encontrado");

            Optional<Vehiculos> veOp = vehiculoRepository.findById(params.getIdVehiculo());
            if (veOp.isPresent()) {
                controlRutas.setVehiculo(veOp.get());
                Vehiculos ve = veOp.get();

                log.info("ajustando vehiculo a asignado");
                ve.setDesEstatusVehiculo("9");
                vehiculoRepository.save(ve);
                log.info(" vehiculo a asignado");
                ruta.setDesServicio(ve.getDesTipoServicio());

            } else log.info("Vehiculo no encontrado" + params.getIdVehiculo());
            //pendiente ver el catalog de status asignado
            controlRutas.setDesEstatusAsigna("1");
            controlRutas.setSistema(true);

            rutasRepository.save(ruta);

            Optional<ModuloAmbulancia> moduloOp = moAmbulanciaRepository.findByIdModuloAndActivoEquals(params.getIdModulo(), true);

            if (moduloOp.isPresent())
                controlRutas.setModulo(moduloOp.get());
            else log.info("modulo no encontrado" + params.getIdModulo());

            log.info("folio.." + params.getNumFolioTarjeta());

            controlRutas.setNumFolioTarjetaCombustible("" + params.getNumFolioTarjeta());
            controlRutas.setRuta(ruta);
            controlRutas.setFechaInicioAsigna(LocalDate.parse(params.getFechaRuta()));
            controlRutas.setTimInicioAsigna(LocalTime.parse(params.getHoraRuta()));

            Optional<Tripulacion> tripOp = tripulacionRepository.findById(params.getIdTripulacion());
            if (tripOp.isPresent()) controlRutas.setTripulacion(tripOp.get());
            else log.info("Tripulacion no encontrado" + params.getIdTripulacion());

            log.info("Guardamos el control de ruta");

            controlRutasForaneasRepository.save(controlRutas);

            Viaticos viaticos = new Viaticos();
            viaticos.setControlRutas(controlRutas);
            viaticos.setViaticosChofer(Double.valueOf(params.getViaticosChofer()));
            viaticos.setViaticosCamillero1(Double.valueOf(params.getViaticosCamillero1()));
            viaticos.setViaticosCamillero2(Double.valueOf(params.getViaticosCamillero2()));
            viaticos.setViaticosCaseta(Double.valueOf(params.getViaticosCaseta()));
            viaticos.setCveMatricula(params.getCveMatricula());
            viaticos.setFecAlta(LocalDate.now());

            viaticosRepository.save(viaticos);

            log.info("Ruta foranea asignada..");
            /* Se crea la asignacion */
            Asignacion asignacion = new Asignacion();
            asignacion.setIdVehiculo(params.getIdVehiculo());
            log.info("Chofer: {}", controlRutas.getTripulacion().getPersonalChofer().getChofer().getIdChofer().longValue());
            log.info("Ruta: {}", controlRutas.getRuta().getIdRuta());
            asignacion.setIdChofer(controlRutas.getTripulacion().getPersonalChofer().getChofer().getIdChofer().longValue());
            asignacion.setIdRuta(controlRutas.getRuta().getIdRuta());
            asignacion.setDesEstatus("1");
            asignacion.setNumFolioTarjeta(controlRutas.getNumFolioTarjetaCombustible());
            RespuestaAsig<AsignacionesEntity> aa = asignaciones.registraAsignacion(asignacion, datosUsuarios);
            AsignacionesEntity asignacionEntity = aa.getDatos();
            ResponseUtil.crearRespuestaExito(controlRutas.getIdControlRuta());
        } catch (Exception exception) {
            log.error("Ha ocurrido un error al guardar la ruta, error: {}", ExceptionUtils.getStackTrace(exception));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje(exception.getMessage());
            response.setError(true);
        }
        return response;
    }

    @Override
    public Respuesta<Integer> editarRuta(Integer idControlRuta, ControlRutasForaneasRequest rutaDTO) {
        Respuesta<Integer> response = new Respuesta<>();
        try {
            String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            log.info("usuario {}", user);
            if (user.equals("denegado")) {
                response.setError(false);
                response.setCodigo(HttpStatus.UNAUTHORIZED.value());
                response.setMensaje(user);
                return response;
            }
            log.info("Actualizando ruta");
            Optional<ControlRutas> controlRutaForanea = controlRutasForaneasRepository
                    .findByIdControlRuta(idControlRuta);

            if (controlRutaForanea.isPresent()) {
                ControlRutas controlRuta = controlRutaForanea.get();

                Rutas ruta = controlRuta.getRuta();

                ruta.setActivo(true);
                ruta.setMatricula(rutaDTO.getCveMatricula());
                ruta.setFechaActualizacion(LocalDate.now());
                ruta.setIndRutaForanea(true);
                ruta.setSistema(true);

                Optional<SolicitudTraslado> solicitud = solicitudTrasladoRepository.findById(rutaDTO.getIdSolicitudTraslado());
                if (solicitud.isPresent()) {
                    SolicitudTraslado solicitudTraslado = solicitud.get();
                    ruta.setIdOrigen(solicitudTraslado.getCveOrigen());
                    ruta.setIdUnidadDestino(solicitudTraslado.getCveDestino());
                    ruta.setIdUnidadSolcitante(solicitudTraslado.getIdUnidadSolicitante());
                    controlRuta.setIdSolcitud(solicitudTraslado);

                    //Ponemos esta solicitud en asiganda
                    solicitudTraslado.setDesEstatusSolicitud(EstatusSolicitudesEnum.Asignada.getValor());
                    solicitudTrasladoRepository.save(solicitudTraslado);
                }
                //borrando  destinos existentes
                for (RutasDestinos destino : ruta.getDestinos()) {
                    ruta.setIdRuta(ruta.getIdRuta());
                    destino.setRuta(ruta);
                    destinosRepository.delete(destino);
                }
                log.info("Rutas borradas");

                RutasDestinos destinos = new RutasDestinos();
                destinos.setActivo(true);
                destinos.setIdUnidadDestino(ruta.getIdUnidadDestino());
                destinos.setIndiceSistema(true);
                destinos.setRuta(ruta);
                log.info("destinos agregados");
                ruta.getDestinos().clear();
                ruta.getDestinos().add(destinos);
                ruta.setFechaActualizacion(LocalDate.now());

                controlRuta.setRuta(ruta);
                Optional<Vehiculos> veOp = vehiculoRepository.findById(rutaDTO.getIdVehiculo());
                if (veOp.isPresent()) {

                    Vehiculos vehiculo = veOp.get();
                    controlRuta.setVehiculo(vehiculo);
                    vehiculo.setDesEstatusVehiculo(EstatusVehiculosEnum.En_Transito.getValor()); // 9 - En Transito.
                    vehiculoRepository.save(vehiculo);

                    log.info("Vehiculo Asignado" + vehiculo);
                    ruta.setDesServicio(vehiculo.getDesTipoServicio());
                }

                rutasRepository.save(ruta);
                log.info("Ruta Actualizada");

                controlRuta.setMatricula(rutaDTO.getCveMatricula());
                controlRuta.setFechaActualizacion(LocalDate.now());
                controlRuta.setNumFolioTarjetaCombustible("" + rutaDTO.getNumFolioTarjeta());
                controlRuta.setTimInicioAsigna(LocalTime.parse(rutaDTO.getHoraRuta()));
                controlRuta.setFechaInicioAsigna(LocalDate.parse(rutaDTO.getFechaRuta()));


                Optional<ModuloAmbulancia> moduloOp = moAmbulanciaRepository.findByIdModuloAndActivoEquals(rutaDTO.getIdModulo(), true);
                moduloOp.ifPresent(controlRuta::setModulo);

                Optional<Tripulacion> tripOp = tripulacionRepository.findById(rutaDTO.getIdTripulacion());
                tripOp.ifPresent(controlRuta::setTripulacion);


                controlRutasForaneasRepository.save(controlRuta);
                log.info("Asignacion Actualizada");

                final Viaticos viaticosRecuperados = viaticosRepository
                        .findByControlRutasIdControlRuta(controlRuta.getIdControlRuta());

                Viaticos viaticos = new Viaticos();
                viaticos.setIdViaticos(viaticosRecuperados.getIdViaticos());
                viaticos.setControlRutas(controlRuta);
                viaticos.setViaticosChofer(Double.valueOf(rutaDTO.getViaticosChofer()));
                viaticos.setViaticosCamillero1(Double.valueOf(rutaDTO.getViaticosCamillero1()));
                viaticos.setViaticosCamillero2(Double.valueOf(rutaDTO.getViaticosCamillero2()));
                viaticos.setViaticosCaseta(Double.valueOf(rutaDTO.getViaticosCaseta()));
                viaticos.setCveMatricula(rutaDTO.getCveMatricula());
                viaticos.setFecActualizacion(LocalDate.now());

                viaticosRepository.save(viaticos);

                response.setCodigo(HttpStatus.OK.value());
                response.setMensaje("Exito");
                response.setError(false);
                response.setDatos(controlRuta.getIdControlRuta());


            } else {
                // todo - usar la utileria para crear el response de error
                response.setDatos(null);
                response.setError(false);
                response.setMensaje("Exito");
                response.setCodigo(HttpStatus.OK.value());
            }


        } catch (Exception exception) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("Ha ocurrido un error al actualizar la ruta, error: {}", ExceptionUtils.getStackTrace(exception));
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje(exception.getMessage());
            response.setError(true);
        }
        return response;
    }

    @Override
    public Respuesta<Integer> eliminarRutas(Integer idControlRuta) {
        Respuesta<Integer> response = new Respuesta<>();
        try {
            String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            log.info("usuario {}", user);
            if (user.equals("denegado")) {

                response.setError(false);
                response.setCodigo(HttpStatus.UNAUTHORIZED.value());
                response.setMensaje(user);
                return response;
            }
            log.info("Eliminando la asignacion ruta");
            ControlRutas rutas = controlRutasForaneasRepository.findById(idControlRuta).orElseThrow(Exception::new);
            rutas.setFechaBaja(LocalDate.now());
            rutas.setActivo(false);

            Gson gson = new Gson();
            DatosUsuario datosUsuarios = gson.fromJson(user, DatosUsuario.class);
            rutas.setMatricula(datosUsuarios.getMatricula());

            Optional<Vehiculos> veOp = vehiculoRepository.findById(rutas.getVehiculo().getIdVehiculo());
            if (veOp.isPresent()) {
                Vehiculos ve = veOp.get();
                ve.setDesEstatusVehiculo(EstatusVehiculosEnum.En_Operacion.getValor()); // 8 - En Operacion
                vehiculoRepository.save(ve);
                log.info(" Se cambio estatus de vehiculo a 8");

            } else log.info("Vehiculo no encontrado" + rutas.getVehiculo());

            Optional<SolicitudTraslado> solicitud = solicitudTrasladoRepository.findById(rutas.getIdSolcitud().getIdSolicitud());
            if (solicitud.isPresent()) {
                SolicitudTraslado solicitudTraslado = solicitud.get();
                solicitudTraslado.setDesEstatusSolicitud(EstatusSolicitudesEnum.ACEPTADA.getValor());
                solicitudTrasladoRepository.save(solicitudTraslado);
            } else log.info("Solicitud no encontrado" + rutas.getIdSolcitud().getIdSolicitud());

            controlRutasForaneasRepository.save(rutas);

            log.info("Eliminando la  ruta");
            Rutas ruta = rutasRepository.findById(rutas.getRuta().getIdRuta()).orElseThrow(Exception::new);
            ruta.setFechaBaja(LocalDate.now());
            ruta.setActivo(false);
            ruta.setMatricula(datosUsuarios.getMatricula());


            rutasRepository.save(ruta);

            response.setCodigo(HttpStatus.OK.value());
            response.setMensaje("Exito");
            response.setError(false);
            response.setDatos(rutas.getIdControlRuta());
        } catch (Exception exception) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("Ha ocurrido un error al eliminar la ruta, error: {}", ExceptionUtils.getStackTrace(exception));
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje(exception.getMessage());
            response.setError(true);
        }
        return response;
    }
}
