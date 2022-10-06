package mx.gob.imss.mssistrans.ccom.rutas.service.impl;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.dto.*;
import mx.gob.imss.mssistrans.ccom.rutas.model.*;
import mx.gob.imss.mssistrans.ccom.rutas.repository.*;
import mx.gob.imss.mssistrans.ccom.rutas.service.ControlRutasForaneasService;
import mx.gob.imss.mssistrans.ccom.rutas.util.EstatusSolicitudesEnum;
import mx.gob.imss.mssistrans.ccom.rutas.util.EstatusVehiculosEnum;
import mx.gob.imss.mssistrans.ccom.rutas.util.Utility;
import org.apache.commons.lang3.exception.ExceptionUtils;
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

@Service
@Slf4j
@Transactional
public class ControlRutasForaneasServiceImpl implements ControlRutasForaneasService {
    private final ControlRutasForaneasRepository controlRutasForaneasRepository;
    private final UnidadAdscripcionRepository unidadAdscripcionRepository;
    private final ModuloAmbulanciaRepository moAmbulanciaRepository;
    private final VehiculosRepository vehiculoRepository;
    private final SolicitudTrasladoRepository solicitudTrasladoRepository;
    private final RutasRepository rutasRepository;
    private final TripulacionRepository tripulacionRepository;
    private final ViaticosRepository viaticosRepository;
    private final RutasDestinosRepository destinosRepository;
    private final ZonaAtencionRepository zonaAtencionRepository;

    public ControlRutasForaneasServiceImpl(
            ControlRutasForaneasRepository controlRutasForaneasRepository,
            UnidadAdscripcionRepository unidadAdscripcionRepository,
            ModuloAmbulanciaRepository moAmbulanciaRepository,
            VehiculosRepository vehiculoRepository,
            SolicitudTrasladoRepository solicitudTrasladoRepository,
            RutasRepository rutasRepository,
            TripulacionRepository tripulacionRepository,
            ViaticosRepository viaticosRepository,
            RutasDestinosRepository destinosRepository,
            ZonaAtencionRepository zonaAtencionRepository) {
        this.controlRutasForaneasRepository = controlRutasForaneasRepository;
        this.unidadAdscripcionRepository = unidadAdscripcionRepository;
        this.moAmbulanciaRepository = moAmbulanciaRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.solicitudTrasladoRepository = solicitudTrasladoRepository;
        this.rutasRepository = rutasRepository;
        this.tripulacionRepository = tripulacionRepository;
        this.viaticosRepository = viaticosRepository;
        this.destinosRepository = destinosRepository;
        this.zonaAtencionRepository = zonaAtencionRepository;
    }

    @Override
    public Respuesta<Page<ControlRutasTablaResponse>> consultarRutas(Pageable pageable) {
        Respuesta<Page<ControlRutasTablaResponse>> response = new Respuesta<>();
        try {
            String usuario = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            log.info("usuario {}", usuario);

            if (usuario.equals("denegado")) {

                response.setError(false);
                response.setCodigo(HttpStatus.UNAUTHORIZED.value());
                response.setMensaje(usuario);
                return response;
            }
            log.info("las rutas page number, {}", pageable.getPageNumber());

            log.info("los rutas, page size {}", pageable.getPageSize());
            Gson gson = new Gson();
            DatosUsuario datosUsuarios = gson.fromJson(usuario, DatosUsuario.class);

            //Validar si es adminsitrador...
            final Page<ControlRutas> result = datosUsuarios.getRol().equals("Administrador") || datosUsuarios.getRol().equals("Normativo") || datosUsuarios.IDOOAD == 9 || datosUsuarios.IDOOAD == 39
                    ? controlRutasForaneasRepository.findAll(pageable)
                    : controlRutasForaneasRepository.findAll(pageable, datosUsuarios.getIDOOAD());

            log.info("las rutas, {}", result.getContent().size());

            List<ControlRutasTablaResponse> content = new ArrayList<>();
            for (ControlRutas rutas : result) {
                ControlRutasTablaResponse rutasTabla = new ControlRutasTablaResponse();
                rutasTabla.setEcco(rutas.getIdVehiculo().getCveEcco());
                rutasTabla.setIdControlRuta(rutas.getIdControlRuta());
                rutasTabla.setIdRuta(rutas.getRuta().getIdRuta());
                rutasTabla.setFolioRuta(rutas.getRuta().getNumFolioRuta());
                rutasTabla.setIdSolicitudTraslado(rutas.getIdSolcitud().getIdSolicitud());
                rutasTabla.setModulo(rutas.getModulo().getDesNombre());

                Optional<UnidadAdscripcion> origen = unidadAdscripcionRepository
                        .findByIdUnidadAdscripcionAndActivoEquals(rutas.getIdSolcitud().getCveOrigen(), true);

                if (origen.isPresent())
                    rutasTabla.setOrigen(origen.get().getNomUnidadAdscripcion());
                content.add(rutasTabla);
            }

            Page<ControlRutasTablaResponse> objetoMapeado = new PageImpl<>(content, pageable, result.getTotalElements());
            response.setDatos(objetoMapeado);
            response.setMensaje("Exito");
            response.setCodigo(HttpStatus.OK.value());
        } catch (Exception exception) {

            log.error("Ha ocurrido un error al consultar las rutas, {}", ExceptionUtils.getStackTrace(exception));
            response.setError(true);
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje(exception.getMessage());
        }
        return response;
    }

    // todo - este se puede quedar del otro lado
    @Override
    public Respuesta<ControlRutasTotalesResponse> consultarTotalesVehiculos() {
        return null;
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
            Integer idOOAD = datosUsuarios.getIDOOAD();

            Optional<ModuloAmbulancia> opModulo = moAmbulanciaRepository.findByIdOOADAndActivoEquals(idOOAD, true);


            Optional<ControlRutas> result = controlRutasForaneasRepository.findByIdControlRuta(idControlRuta);
            ControlRutasForaneasResponse rutasResponse = new ControlRutasForaneasResponse();
            if (result.isPresent()) {
                ControlRutas ruta = result.get();

                Optional<UnidadAdscripcion> origen = unidadAdscripcionRepository
                        .findByIdUnidadAdscripcionAndActivoEquals(ruta.getIdSolcitud().getCveOrigen(), true);
                if (origen.isPresent()) rutasResponse.setOrigen(origen.get());

                Optional<UnidadAdscripcion> destino = unidadAdscripcionRepository
                        .findByIdUnidadAdscripcionAndActivoEquals(ruta.getIdSolcitud().getCveDestino(), true);
                if (destino.isPresent()) rutasResponse.setDestino(destino.get());

                rutasResponse.setFechaRuta(ruta.getFechaInicioAsigna().toString());
                rutasResponse.setHoraRuta(ruta.getTimInicioAsigna().toString());
                rutasResponse.setIdSolicitudTraslado(ruta.getIdSolcitud());
                rutasResponse.setModulo(ruta.getModulo());
                rutasResponse.setNumFolioTarjeta(ruta.getNumFolioTarjetaCombustible());
                rutasResponse.setRuta(ruta.getRuta());

                //

                if (opModulo.isPresent()) {

                    ModuloAmbulancia moduloAmbulancia = opModulo.get();

                    Optional<ZonaAtencion> zonaAtencion = zonaAtencionRepository
                            .findByIdModuloAndActivoEquals(moduloAmbulancia.getIdModulo(), true);

                    Integer idZona = zonaAtencion.get().getIdZona();

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
                    TripulacionInterfaceResponse tripOp = tripulacionRepository.findTripulacionIdVehiculo(ruta.getIdVehiculo().getIdVehiculo());

                    if (tripOp != null) {


                        tripRes.setIdTripulacion(tripOp.getidTripulacion());
                        tripRes.setIdVehiculo(tripOp.getidVehiculo());


                        tripRes.setCveMatriculaCamillero1(tripOp.getcveMatriculaCamillero1());
                        tripRes.setCveMatriculaCamillero2(tripOp.getcveMatriculaCamillero2());
                        tripRes.setCveMatriculaChofer(tripOp.getcveMatriculaChofer());

                        //Usuario camillero1=usuarioRepository.getUsuario(tripulacion.getPersonalCamillero1().getCamillero().getCveMatricula());
                        tripRes.setNombreCamillero1(tripOp.getnombreCamillero1());

                        //Usuario  camillero2=usuarioRepository.getUsuario(tripulacion.getPersonalCamillero2().getCamillero().getCveMatricula());
                        tripRes.setNombreCamillero2(tripOp.getnombreCamillero2());
                        //Usuario  chofer=usuarioRepository.getUsuario(tripulacion.getPersonalChofer().getChofer().getMatriculaChofer());
                        tripRes.setNombreChofer(tripOp.getnombreChofer());


                        tripRes.setIdTripulacion(ruta.getTripulacion().getIdTripulacion());
                        tripRes.setIdVehiculo(ruta.getTripulacion().getIdVehiculo());
                        tripRes.setFecFecha(ruta.getTripulacion().getFecFecha().toString());
                        rutasResponse.setTripulacion(tripRes);
                    } else {
                        log.info("No se econtro la tripulacion por id vehiculo " + ruta.getIdVehiculo().getIdVehiculo());
                        response.setDatos(null);
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

                rutasResponse.setVehiculo(ruta.getIdVehiculo());

                final Viaticos viaticos = viaticosRepository.findByControlRutasIdControlRuta(idControlRuta);
                if (viaticos != null) {
                    rutasResponse.setViaticosCasetas(String.valueOf(viaticos.getViaticosCaseta()));
                    rutasResponse.setViaticosChofer(String.valueOf(viaticos.getViaticosChofer()));
                    rutasResponse.setViaticosCamillero1(String.valueOf(viaticos.getViaticosCamillero1()));
                    rutasResponse.setViaticosCamillero2(String.valueOf(viaticos.getViaticosCamillero2()));
                }

                response.setDatos(rutasResponse);
            } else {
                response.setDatos(null);
            }
            response.setError(false);
            response.setMensaje("Exito");
            response.setCodigo(HttpStatus.OK.value());

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
        // todo - puede que existan solicitudes que no tengan destino
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
            ruta.setCveMatricula(params.getCveMatricula());
            ruta.setFechaAlta(LocalDate.now());
            ruta.setIndiceSistema(true);
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
                // todo - estatus para solicitudes de traslado
                // todo - cambiar tambien en asignacion normal
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

//            rutasRepository.save(ruta);


            Gson gson = new Gson();
            DatosUsuario datosUsuarios = gson.fromJson(user, DatosUsuario.class);

            //Asignadmos el OOAD ala que pertence el ususario

            ruta.setIdOOADFiltro(datosUsuarios.getIDOOAD());

            //Se obtiene el folio

            Utility utility = new Utility();
            //pendiente definir las siglas ooad
            String folio = utility.generateFolio("", ruta.getIdRuta()!=null?ruta.getIdRuta():1);
            //Actualizamos folio

            ruta.setNumFolioRuta(folio);

            // Generamos registro de control de ruta
            ControlRutas controlRutas = new ControlRutas();
            controlRutas.setActivo(true);
            controlRutas.setCveMatricula(ruta.getCveMatricula());
            controlRutas.setFechaAlta(LocalDate.now());

            if (solicitud.isPresent()) controlRutas.setIdSolcitud(solicitud.get());

            else log.info("Solicitud no encontrado");

            Optional<Vehiculos> veOp = vehiculoRepository.findById(params.getIdVehiculo());
            if (veOp.isPresent()) {
                controlRutas.setIdVehiculo(veOp.get());
                Vehiculos ve = veOp.get();

                log.info("ajustando vehiculo a asignado");
                ve.setDesEstatusVehiculo("9");
                vehiculoRepository.save(ve);
                log.info(" vehiculo a asignado");
                ruta.setDesServicio(ve.getDesTipoServicio());

            } else log.info("Vehiculo no encontrado" + params.getIdVehiculo());
            //pendiente ver el catalog de status asignado
            controlRutas.setDesEstatusAsigna("1");
            controlRutas.setIndiceSistema(true);

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

            response.setCodigo(HttpStatus.OK.value());
            response.setMensaje("Exito");
            response.setError(false);
            response.setDatos(controlRutas.getIdControlRuta());
        } catch (Exception exception) {
            exception.printStackTrace();
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
            Optional<ControlRutas> crutaOp = controlRutasForaneasRepository.findByIdControlRuta(idControlRuta);

            if (crutaOp.isPresent()) {
                ControlRutas controlRuta = crutaOp.get();

                Rutas ruta = controlRuta.getRuta();

                ruta.setActivo(true);
                ruta.setCveMatriculaModifica(rutaDTO.getCveMatricula());
                ruta.setFechaActualizacion(LocalDate.now());
                ruta.setIndRutaForanea(true);
                ruta.setIndiceSistema(true);

                Optional<SolicitudTraslado> solicitud = solicitudTrasladoRepository.findById(rutaDTO.getIdSolicitudTraslado());
                if (solicitud.isPresent()) {
                    SolicitudTraslado solicitudTraslado = solicitud.get();
                    ruta.setIdOrigen(solicitudTraslado.getCveOrigen());
                    ruta.setIdUnidadDestino(solicitudTraslado.getCveDestino());
                    ruta.setIdUnidadSolcitante(solicitudTraslado.getIdUnidadSolicitante());
                    controlRuta.setIdSolcitud(solicitudTraslado);

                    //Ponemos esta solicitud en asiganda
                    // todo - hacer un enum para los estatus de la so
//                    solicitudTraslado.setDesEstatusSolicitud("4");
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
                    controlRuta.setIdVehiculo(vehiculo);
                    // todo - ver si se puede implementar el enum para los estatus
                    vehiculo.setDesEstatusVehiculo(EstatusVehiculosEnum.En_Transito.getValor()); // 9 - En Transito.
                    vehiculoRepository.save(vehiculo);
                    log.info("Vehiculo Asignado" + vehiculo);
                    ruta.setDesServicio(vehiculo.getDesTipoServicio());
                }

                rutasRepository.save(ruta);
                log.info("Ruta Actualizada");
                controlRuta.setCveMatriculaModifica(rutaDTO.getCveMatricula());
                controlRuta.setFechaActualizacion(LocalDate.now());
                controlRuta.setNumFolioTarjetaCombustible("" + rutaDTO.getNumFolioTarjeta());
                controlRuta.setTimInicioAsigna(LocalTime.parse(rutaDTO.getHoraRuta()));
                controlRuta.setFechaInicioAsigna(LocalDate.parse(rutaDTO.getFechaRuta()));
                
                Optional<ModuloAmbulancia> moduloOp = moAmbulanciaRepository.findByIdModuloAndActivoEquals(rutaDTO.getIdModulo(), true);
                if (moduloOp.isPresent())
                    controlRuta.setModulo(moduloOp.get());

                Optional<Tripulacion> tripOp = tripulacionRepository.findById(rutaDTO.getIdTripulacion());
                if (tripOp.isPresent()) controlRuta.setTripulacion(tripOp.get());


                controlRutasForaneasRepository.save(controlRuta);
                log.info("Asignacion Actualizada");

                // todo - guardar los viaticos
                // todo - recuperar los viaticos anteriores y cargar los nuevos
                final Viaticos viaticosRecuperados = viaticosRepository
                        .findByControlRutasIdControlRuta(controlRuta.getIdControlRuta());

                Viaticos viaticos = new Viaticos();
                viaticos.setIdViaticos(viaticosRecuperados.getIdViaticos());
                viaticos.setControlRutas(controlRuta);
                viaticos.setViaticosChofer(Double.valueOf(rutaDTO.getViaticosChofer()));
                viaticos.setViaticosCamillero1(Double.valueOf(rutaDTO.getViaticosCamillero1()));
                viaticos.setViaticosCamillero2(Double.valueOf(rutaDTO.getViaticosCamillero2()));
                viaticos.setViaticosCaseta(Double.valueOf(rutaDTO.getViaticosCaseta()));
                viaticos.setCveMatriculaModifica(rutaDTO.getCveMatricula());
                viaticos.setFecActualizacion(LocalDate.now());

                viaticosRepository.save(viaticos);

                response.setCodigo(HttpStatus.OK.value());
                response.setMensaje("Exito");
                response.setError(false);
                response.setDatos(controlRuta.getIdControlRuta());


            } else {
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
            rutas.setCveMatriculaBaja(datosUsuarios.getMatricula());

            Optional<Vehiculos> veOp = vehiculoRepository.findById(rutas.getIdVehiculo().getIdVehiculo());
            if (veOp.isPresent()) {
                Vehiculos ve = veOp.get();
                ve.setDesEstatusVehiculo(EstatusVehiculosEnum.En_Operacion.getValor()); // 8 - En Operacion
                vehiculoRepository.save(ve);
                log.info(" Se cambio estatus de vehiculo a 8");

            } else log.info("Vehiculo no encontrado" + rutas.getIdVehiculo());

            Optional<SolicitudTraslado> solicitud = solicitudTrasladoRepository.findById(rutas.getIdSolcitud().getIdSolicitud());
            if (solicitud.isPresent()) {
                SolicitudTraslado solicitudTraslado = solicitud.get();
                solicitudTraslado.setDesEstatusSolicitud(EstatusSolicitudesEnum.Aceptada.getValor());
                solicitudTrasladoRepository.save(solicitudTraslado);
            } else log.info("Solicitud no encontrado" + rutas.getIdSolcitud().getIdSolicitud());

            controlRutasForaneasRepository.save(rutas);

            log.info("Eliminando la  ruta");
            Rutas ruta = rutasRepository.findById(rutas.getRuta().getIdRuta()).orElseThrow(Exception::new);
            ruta.setFechaBaja(LocalDate.now());
            ruta.setActivo(false);
            ruta.setCveMatriculaBaja(datosUsuarios.getMatricula());


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
