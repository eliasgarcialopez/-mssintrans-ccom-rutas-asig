package mx.gob.imss.mssistrans.ccom.rutas.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.dto.*;
import mx.gob.imss.mssistrans.ccom.rutas.exceptions.UserUnauthorizedException;
import mx.gob.imss.mssistrans.ccom.rutas.service.AsigRutasService;
import mx.gob.imss.mssistrans.ccom.rutas.util.ControllersUtil;
import mx.gob.imss.mssistrans.ccom.rutas.util.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static mx.gob.imss.mssistrans.ccom.rutas.util.ResponseEntityUtil.sendResponse;

/**
 * @author opimentel
 */
@RestController
@Slf4j
@RequestMapping("/control-rutas")
@AllArgsConstructor
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RutasAsignacionesCCOMController {
    private final AsigRutasService asigRutasService;

    @GetMapping
    public ResponseEntity<Response<Page<AsigRutasResponse>>> consultaGeneral(
            @RequestParam(defaultValue = "0") Integer pagina,
            @RequestParam(defaultValue = "10") Integer tamanio,
            @RequestParam(defaultValue = "") String ordenCol,
            @RequestParam(defaultValue = "idSolicitud,asc") String[] sort,
            @RequestParam(required = false) String idRutaAsig,
            @RequestParam(required = false) String idSolicitud) throws UserUnauthorizedException {
        log.info("ordenCol: {}", ordenCol);
        if (ResponseUtil.getAccess()) {
            throw new UserUnauthorizedException();
        } else {
            Pageable pageable = PageRequest
                    .of(pagina, tamanio, Sort.by(ControllersUtil.convertSort(sort)));
            Response<Page<AsigRutasResponse>> response = asigRutasService
                    .consultaVistaRapida(pageable, idRutaAsig, idSolicitud);
            return sendResponse(response);
        }
    }

    @DeleteMapping(path = "{idControlRuta}")
    public ResponseEntity<Response<Integer>> delete(@PathVariable String idControlRuta) throws UserUnauthorizedException {
        if (ResponseUtil.getAccess()) {
            throw new UserUnauthorizedException();
        } else {
            Response<Integer> response = asigRutasService.delete(idControlRuta);
            return sendResponse(response);
        }
    }

    /****** HU006 - 26 **********/
    @GetMapping(path = "/getRutas")
    public ResponseEntity<Response<List<DatosAsigRutasResponse>>> obtenerRuta() throws UserUnauthorizedException {
        if (ResponseUtil.getAccess()) {
            throw new UserUnauthorizedException();
        } else {
            DatosUsuarioDTO datosUsuario = ResponseUtil.datosUsuarios();
            Response<List<DatosAsigRutasResponse>> response = asigRutasService
                    .getRutas(datosUsuario.getIdOoad(), datosUsuario.getRol());
            return sendResponse(response);
        }
    }

    @GetMapping(path = "/getSolTraslado/{idRuta}")
    public ResponseEntity<Response<List<SolTrasladoResponse>>> getSolicitudTraslado(@PathVariable Integer idRuta) throws UserUnauthorizedException {

        if (ResponseUtil.getAccess()) {
            throw new UserUnauthorizedException();
        } else {
            DatosUsuarioDTO datosUsuario = ResponseUtil.datosUsuarios();
            Response<List<SolTrasladoResponse>> response = asigRutasService
                    .getSolicitudTraslado(datosUsuario, idRuta);
            return sendResponse(response);
        }
    }

    @GetMapping(path = "/ecco/{idRuta}")
    public ResponseEntity<Response<List<EccoResponse>>> getEcco(
            @PathVariable Integer idRuta) throws UserUnauthorizedException {

        if (ResponseUtil.getAccess()) {
            throw new UserUnauthorizedException();
        } else {
            DatosUsuarioDTO datosUsuario = ResponseUtil.datosUsuarios();
            Response<List<EccoResponse>> response = asigRutasService.getEcco(datosUsuario, idRuta);
            return sendResponse(response);
        }
    }

    @GetMapping(path = "/getDatosAsignacion")
    public ResponseEntity<Response<DatosAsigResponse>> getDatosAsignacion(
            @RequestParam(required = false) Integer idControlRuta,
            @RequestParam(required = false) Integer idRuta,
            @RequestParam(required = false) Integer idSolicitud,
            @RequestParam(required = false) Integer idVehiculo) throws UserUnauthorizedException {

        if (ResponseUtil.getAccess()) {
            throw new UserUnauthorizedException();
        } else {
            Response<DatosAsigResponse> response = asigRutasService.getDatosAsignacion(idControlRuta, idRuta, idSolicitud, idVehiculo);
            return sendResponse(response);
        }
    }

    @GetMapping(path = "/getTripulacionAsignada")
    public ResponseEntity<Response<List<TripulacionAsigResponse>>> getTripulacionAsignada(
            @RequestParam(required = false) Integer idControlRuta,
            @RequestParam(required = false) Integer idRuta,
            @RequestParam(required = false) Integer idSolicitud,
            @RequestParam(required = false) Integer idVehiculo) throws UserUnauthorizedException {

        if (ResponseUtil.getAccess()) {
            throw new UserUnauthorizedException();
        } else {
            Response<List<TripulacionAsigResponse>> response = asigRutasService
                    .getTripulacionAsignada(idControlRuta, idRuta, idSolicitud, idVehiculo);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping(path = "/getRegRecorrido")
    public ResponseEntity<Response<DatosRegistroRecorridoDTO>> getRegRecorrido(
            @RequestParam(required = false) Integer idControlRuta,
            @RequestParam(required = false) Integer idRuta,
            @RequestParam(required = false) Integer idSolicitud,
            @RequestParam(required = false) Integer idVehiculo) throws UserUnauthorizedException {

        if (ResponseUtil.getAccess()) {
            throw new UserUnauthorizedException();
        } else {
            Response<DatosRegistroRecorridoDTO> response = asigRutasService
                    .getRegistroRecorrido(idControlRuta, idRuta, idSolicitud, idVehiculo);

            return sendResponse(response);
        }
    }

    @PutMapping(path = "/")
    public ResponseEntity<Response<Integer>> update(
            @RequestBody ActualizarControlRutaRequest datosRecorrido) throws UserUnauthorizedException {

        if (ResponseUtil.getAccess()) {
            throw new UserUnauthorizedException();
        } else {
            Response<Integer> response = asigRutasService.update(datosRecorrido);
            return sendResponse(response);
        }
    }

    @GetMapping(path = "/getDetalleRutasyAsig")
    public ResponseEntity<Response<List<DetRutasAsignacionesResponse>>> getDetalleRutasyAsig(@RequestParam Integer idControlRuta) throws UserUnauthorizedException {

        if (ResponseUtil.getAccess()) {
            throw new UserUnauthorizedException();
        } else {
            Response<List<DetRutasAsignacionesResponse>> response = asigRutasService.getDetalleRutasyAsig(idControlRuta);
            return sendResponse(response);
        }
    }

    @GetMapping(path = "/getControlRutas")
    public ResponseEntity<Response<Page<AsigRutasResponse>>> getControlRutas(
            @RequestParam(defaultValue = "0") Integer pagina,
            @RequestParam(defaultValue = "10") Integer tamanio,
            @RequestParam(defaultValue = "") String ordenCol,
            @RequestParam(defaultValue = "idSolicitud,asc") String[] sort,
            @RequestParam(required = false) String idAsignacion,
            @RequestParam(required = false) String idSolicitud) throws UserUnauthorizedException {
        log.info("ordenCol, {}", ordenCol);
        if (ResponseUtil.getAccess()) {
            throw new UserUnauthorizedException();
        }

        Pageable pageable = PageRequest.of(pagina, tamanio, Sort.by(ControllersUtil.convertSort(sort)));

        Response<Page<AsigRutasResponse>> response = asigRutasService
                .getControlRutas(pageable, idAsignacion, idSolicitud);
        return sendResponse(response);
    }

    @GetMapping(path = "/getDatosControlRutaById")
    public ResponseEntity<Response<DatosControlRutaDTO>> getDatosControlRutaById(
            @RequestParam(required = false) Integer idControlRuta) throws UserUnauthorizedException {

        if (ResponseUtil.getAccess()) {
            throw new UserUnauthorizedException();
        } else {
            Response<DatosControlRutaDTO> response = asigRutasService.getDatosControlRutaById(idControlRuta);
            return sendResponse(response);
        }
    }

}
