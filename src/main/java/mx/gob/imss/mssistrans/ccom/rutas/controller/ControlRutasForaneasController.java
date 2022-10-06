package mx.gob.imss.mssistrans.ccom.rutas.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.dto.*;
import mx.gob.imss.mssistrans.ccom.rutas.service.ControlRutasForaneasService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/asignaciones-foraneas")
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

public class ControlRutasForaneasController {
    private final ControlRutasForaneasService rutasForaneasService;

    /**
     * Consultar las rutas con paginacion
     *
     * @param pagina
     * @param tamanio
     * @param sort
     * @param columna
     * @return
     */
    @GetMapping
    public ResponseEntity<Respuesta<Page<ControlRutasTablaResponse>>> consultarRutas(
            @RequestParam Integer pagina,
            @RequestParam(defaultValue = "10") Integer tamanio,
            @RequestParam String sort,
            @RequestParam String columna) {
        if (columna.equals("idRuta")) columna = "idControlRuta";
        Pageable pageable = PageRequest.of(pagina, tamanio, Sort.by(new Sort.Order(Sort.Direction.fromString(sort), columna)));
        Respuesta<Page<ControlRutasTablaResponse>> response = rutasForaneasService.consultarRutas(pageable);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo()));
    }


    /**
     * Endpoint para consultar una Ruta por su Id
     *
     * @param idRuta
     * @return
     */
    @GetMapping("/id/{idRuta}")
    public ResponseEntity<Respuesta<ControlRutasForaneasResponse>> consultarRuta(@PathVariable Integer idRuta) {
        Respuesta<ControlRutasForaneasResponse> response = rutasForaneasService.consultarRutas(idRuta);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo()));
    }

    /**
     * Endpoint para consultar total de vehiculos por ooad se obtiene del token
     *
     * @return
     */
    @GetMapping("/totales/")
    public ResponseEntity<Respuesta<ControlRutasTotalesResponse>> consultarTotalesVehiculos() {
        Respuesta<ControlRutasTotalesResponse> response = rutasForaneasService.consultarTotalesVehiculos();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo()));
    }

    /**
     * Endpoint para crear una Ruta
     *
     * @param rutaRequest
     * @return
     */
    @PostMapping
    public ResponseEntity<Respuesta<Integer>> crearRuta(@RequestBody ControlRutasForaneasRequest rutaRequest) {
        Respuesta<Integer> response = rutasForaneasService.crearRuta(rutaRequest);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo()));
    }

    /**
     * Endpoint para editar una Ruta
     *
     * @param idRuta
     * @param rutasRequest
     * @return
     */
    @PutMapping("/{idRuta}")
    public ResponseEntity<Respuesta<Integer>> editarRuta(
            @PathVariable Integer idRuta,
            @RequestBody ControlRutasForaneasRequest rutasRequest) {
        Respuesta<Integer> response = rutasForaneasService.editarRuta(idRuta, rutasRequest);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo()));
    }

    /**
     * Endpoint para eliminar una Ruta. El eliminado es l&oacute;gico.
     *
     * @param idRuta
     * @return
     */
    @DeleteMapping("/{idRuta}")
    public ResponseEntity<Respuesta<Integer>> eliminarRuta(@PathVariable Integer idRuta) {
        Respuesta<Integer> respuesta = rutasForaneasService.eliminarRutas(idRuta);
        return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getCodigo()));
    }

}
