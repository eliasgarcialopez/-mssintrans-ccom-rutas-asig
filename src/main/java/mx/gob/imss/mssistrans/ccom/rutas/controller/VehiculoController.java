package mx.gob.imss.mssistrans.ccom.rutas.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;
import mx.gob.imss.mssistrans.ccom.rutas.dto.VehiculoResponse;
import mx.gob.imss.mssistrans.ccom.rutas.service.VehiculoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static mx.gob.imss.mssistrans.ccom.rutas.util.ResponseUtil.sendResponse;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/vehiculo")
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class VehiculoController {
    private final VehiculoService vehiculoService;

    /**
     * Consultar los vehiculos disponibles por IdOOAD se obtiene del token
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<Response<List<VehiculoResponse>>> consultarVehiculos() {
        Response<List<VehiculoResponse>> response = vehiculoService.findVehiculoAsignables();
        return sendResponse(response);
    }
}
