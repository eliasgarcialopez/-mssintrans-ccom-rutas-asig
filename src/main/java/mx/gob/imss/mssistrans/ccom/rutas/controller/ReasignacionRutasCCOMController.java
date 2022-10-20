package mx.gob.imss.mssistrans.ccom.rutas.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.dto.*;
import mx.gob.imss.mssistrans.ccom.rutas.exceptions.UserUnauthorizedException;
import mx.gob.imss.mssistrans.ccom.rutas.service.ReasignacionRutasService;
import mx.gob.imss.mssistrans.ccom.rutas.util.ControllersUtil;
import mx.gob.imss.mssistrans.ccom.rutas.util.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static mx.gob.imss.mssistrans.ccom.rutas.util.ResponseEntityUtil.sendResponse;


/**
 * @author opimentel
 */
@Slf4j
@RestController
@RequestMapping("/ReasignacionRutasCCOM")
@AllArgsConstructor
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ReasignacionRutasCCOMController {
    private final ReasignacionRutasService reasignacionRutasService;

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
        }

        Pageable pageable = PageRequest.of(pagina, tamanio, Sort.by(ControllersUtil.convertSort(sort)));
        Response<Page<AsigRutasResponse>> response = reasignacionRutasService
                .consultaVistaRapida(
                        pageable,
                        idRutaAsig,
                        idSolicitud
                );

        return sendResponse(response);
    }

    @DeleteMapping(path = "{idReAsignacion}")
    public ResponseEntity<Response<Integer>> delete(@PathVariable String idReAsignacion) throws UserUnauthorizedException {
        if (ResponseUtil.getAccess()) {
            throw new UserUnauthorizedException();
        }
        Response<Integer> response = reasignacionRutasService.delete(idReAsignacion);
        return sendResponse(response);
    }

    /****** HU006 - 28 **********/
    @GetMapping(path = "/getDetalleReAsignacion")
    public ResponseEntity<Response<List<DetReasignacionesRutasResponse>>> getDetalleReAsignacion(@RequestParam Integer idControlRuta) throws UserUnauthorizedException {
        if (ResponseUtil.getAccess()) {
            throw new UserUnauthorizedException();
        }
        Response<List<DetReasignacionesRutasResponse>> response = reasignacionRutasService
                .getDetalleReAsignacion(idControlRuta);
        return sendResponse(response);
    }

    @GetMapping(path = "/ecco")
    public ResponseEntity<Response<List<ReasignacionEccoResponse>>> getEcco() throws UserUnauthorizedException {
        if (ResponseUtil.getAccess()) {
            throw new UserUnauthorizedException();
        }
        Response<List<ReasignacionEccoResponse>> response = reasignacionRutasService.getEcco();
        return sendResponse(response);
    }

    @GetMapping(path = "/getTripulacionAsignada")
    public ResponseEntity<Response<List<ReasignacionTripulacionResponse>>> getTripulacionAsignada(
            @RequestParam(required = false) Integer idControlRuta,
            @RequestParam(required = false) Integer idRuta,
            @RequestParam(required = false) Integer idSolicitud,
            @RequestParam(required = false) Integer idVehiculo) throws UserUnauthorizedException {

        if (ResponseUtil.getAccess()) {
            throw new UserUnauthorizedException();
        }
        Response<List<ReasignacionTripulacionResponse>> response = reasignacionRutasService
                .getTripulacionAsignada(idControlRuta, idRuta, idSolicitud, idVehiculo);
        return sendResponse(response);
    }

    @GetMapping(path = "/getIncidentes")
    public ResponseEntity<Response<List<SiniestrosResponse>>> getSiniestro() throws UserUnauthorizedException {
        if (ResponseUtil.getAccess()) {
            throw new UserUnauthorizedException();
        }
        Response<List<SiniestrosResponse>> response = reasignacionRutasService.getSiniestro();
        return sendResponse(response);
    }

    @PostMapping(path = "/")
    public ResponseEntity<Response<Integer>> save(
            @RequestParam Integer idVehiculo,
            @RequestParam Integer idRuta, @RequestParam Integer idChofer,
            @RequestParam String desMotivoReasig, @RequestParam String desSiniestro,
            @RequestParam(required = false) Integer idVehiculoSust,
            @RequestParam(required = false) Integer idChoferSust,
            @RequestParam Integer idAsignacion, @RequestParam String cveMatricula) throws UserUnauthorizedException {
        if (ResponseUtil.getAccess()) {
            throw new UserUnauthorizedException();
        }
        Response<Integer> response = reasignacionRutasService.crearReasignacion(
                idVehiculo, idRuta,
                idChofer, desMotivoReasig,
                desSiniestro, idVehiculoSust,
                idChoferSust, idAsignacion,
                cveMatricula);
        return sendResponse(response);
    }


    @PutMapping(path = "/")
    public ResponseEntity<Response<Integer>> update(
            @RequestParam(required = false) String desSiniestro,
            @RequestParam(required = false) Integer idVehiculoSust,
            @RequestParam(required = false) String desMotivoReasignacion,
            @RequestParam Integer idVehiculo, @RequestParam Integer idRuta,
            @RequestParam Integer idChofer) throws UserUnauthorizedException {
        if (ResponseUtil.getAccess()) {
            throw new UserUnauthorizedException();
        }
        Response<Integer> response = reasignacionRutasService
                .update(desSiniestro,
                        idVehiculoSust,
                        desMotivoReasignacion,
                        idVehiculo,
                        idRuta,
                        idChofer
                );
        return sendResponse(response);
    }
}
