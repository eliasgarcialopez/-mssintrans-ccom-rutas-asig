package mx.gob.imss.mssistrans.ccom.rutas.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Respuesta;
import mx.gob.imss.mssistrans.ccom.rutas.dto.TripulacionResponse;
import mx.gob.imss.mssistrans.ccom.rutas.service.TripulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/tripulacion")
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

public class TripulacionController {
    @Autowired
    private TripulacionService triuService;

    /**
     * Consultar la tripulaci&oacute;n  por idVehiculo
     *
     * @param idVehiculo
     * @return
     */
    @GetMapping
    public ResponseEntity<Respuesta<TripulacionResponse>> consultaTripulacion(@RequestParam Integer idVehiculo) {
        Respuesta<TripulacionResponse> response = triuService.findByIdVehiculo(idVehiculo);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo()));
    }
}
