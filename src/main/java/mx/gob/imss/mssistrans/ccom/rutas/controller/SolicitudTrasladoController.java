package mx.gob.imss.mssistrans.ccom.rutas.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;
import mx.gob.imss.mssistrans.ccom.rutas.dto.SolicitudTrasladoResponse;
import mx.gob.imss.mssistrans.ccom.rutas.service.SolicitudTrasladoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static mx.gob.imss.mssistrans.ccom.rutas.util.ResponseUtil.sendResponse;


@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/solicitudes")
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class SolicitudTrasladoController {
    private final SolicitudTrasladoService solicitudTrasladoService;

    /**
     * Consultar las solicitudes de traslado por turno y que sean aceptadas
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<Response<List<SolicitudTrasladoResponse>>> consultarSolicitudesTraslado() {
        Response<List<SolicitudTrasladoResponse>> response = solicitudTrasladoService.consultarSolicitudesByEstatus();
        return sendResponse(response);
    }
}
