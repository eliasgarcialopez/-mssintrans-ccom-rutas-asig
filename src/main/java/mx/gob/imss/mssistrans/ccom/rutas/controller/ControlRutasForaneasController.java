package mx.gob.imss.mssistrans.ccom.rutas.controller;

import lombok.AllArgsConstructor;
import mx.gob.imss.mssistrans.ccom.rutas.dto.*;
import mx.gob.imss.mssistrans.ccom.rutas.service.ControlRutasForaneasService;
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
@RestController
@RequestMapping("/asignaciones-foraneas")
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

public class ControlRutasForaneasController {
    private final ControlRutasForaneasService rutasService;

    /**
     * Consultar las rutas con paginacion
     *
     * @param pagina
     * @param tamanio
     * @param sort
     * @param column
     * @return
     */
    @GetMapping
    public ResponseEntity<Respuesta<?>> consultarRutas(@RequestParam Integer pagina,
                                                       @RequestParam(defaultValue = "10") Integer tamanio, @RequestParam String sort, @RequestParam String columna) {
        // todo - sacar a un metodo
        if (columna.equals("idRuta")) columna = "idControlRuta";
        Pageable pageable = PageRequest.of(pagina, tamanio, Sort.by(new Sort.Order(Sort.Direction.fromString(sort), columna)));
        Respuesta<?> response = rutasService.consultarRutas(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Endpoint para consultar una Ruta por su Id
     *
     * @param idRuta
     * @return
     */
    @GetMapping("/id/{idRuta}")
    public ResponseEntity<?> consultarRuta(@PathVariable Integer idRuta) {
        Respuesta<ControlRutasForaneasResponse> response = rutasService.consultarRutas(idRuta);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint para consultar total de vehiculos por ooad se obtiene del token
     * todo - se va a consultar desde el otro servicio de rutas, hay que ver si no se saca a un controller propio
     *
     * @param idRuta
     * @return
     */
    @GetMapping("/totales/")
    public ResponseEntity<?> consultarTotalesVehiculos() {
        Respuesta<ControlRutasTotalesResponse> response = rutasService.consultarTotalesVehiculos();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint para crear una Ruta
     *
     * @param rutaRequest
     * @return
     */
    @PostMapping
    public ResponseEntity<Respuesta<?>> crearRuta(@RequestBody ControlRutasForaneasRequest rutaRequest) {
        Respuesta<?> response = rutasService.crearRuta(rutaRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint para editar una Ruta
     *
     * @param idRuta
     * @param RutaRequestDTO
     * @return
     */
    @PutMapping("/{idRuta}")
    public ResponseEntity<?> editarRuta(@PathVariable Integer idRuta, @RequestBody ControlRutasForaneasRequest rutasRequest) {
        Respuesta<?> response = rutasService.editarRuta(idRuta, rutasRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint para eliminar una Ruta. El eliminado es l&oacute;gico.
     *
     * @param idRuta
     * @return
     */
    @DeleteMapping("/{idRuta}")
    public ResponseEntity<?> eliminarRuta(@PathVariable Integer idRuta) {
        Respuesta<?> response = rutasService.eliminarRutas(idRuta);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Respuesta<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException exception) {
        final Respuesta<Map<String, String>> response = new Respuesta<>();
        response.setMensaje("Hay errores en los datos, favor de revisar la informacion");
        response.setError(true);
        response.setCodigo(HttpStatus.BAD_REQUEST.value());
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errors.put(campo, mensaje);
        });
        return response;
    }
}
