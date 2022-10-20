package mx.gob.imss.mssistrans.ccom.rutas.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.dto.ModuloResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;
import mx.gob.imss.mssistrans.ccom.rutas.service.ModuloService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static mx.gob.imss.mssistrans.ccom.rutas.util.ResponseEntityUtil.sendResponse;


@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/modulo")
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ModuloAmbulanciaController {
    private final ModuloService moduloService;

    /**
     * Consulta el m&oacute;dulo de ambulancia por IdOOAD se obtiene del token
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<Response<ModuloResponse>> consultarModulo() {
        Response<ModuloResponse> response = moduloService.busquedaModulo();
        return sendResponse(response);
    }

}
