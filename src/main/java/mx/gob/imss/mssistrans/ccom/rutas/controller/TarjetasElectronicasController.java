package mx.gob.imss.mssistrans.ccom.rutas.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.dto.CatalogoGenerico;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;
import mx.gob.imss.mssistrans.ccom.rutas.service.TarjetasElectronicasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static mx.gob.imss.mssistrans.ccom.rutas.util.ResponseEntityUtil.sendResponse;


@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/tarjetas")
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class TarjetasElectronicasController {
    private final TarjetasElectronicasService tarjetasElectronicasService;

    /**
     * Consultar las tarjetas de combustible disponibles por IdOOAD se obtiene del token
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<Response<List<CatalogoGenerico>>> consultarTarjetasCombustible() {

        Response<List<CatalogoGenerico>> response = tarjetasElectronicasService.busquedaTarjetasDigitales();
        return sendResponse(response);
    }

}
