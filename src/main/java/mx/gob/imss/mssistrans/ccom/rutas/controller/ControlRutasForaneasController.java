package mx.gob.imss.mssistrans.ccom.rutas.controller;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.dto.*;
import mx.gob.imss.mssistrans.ccom.rutas.exceptions.UserUnauthorizedException;
import mx.gob.imss.mssistrans.ccom.rutas.service.ControlRutasForaneasService;
import mx.gob.imss.mssistrans.ccom.rutas.util.ControllersUtil;
import mx.gob.imss.mssistrans.ccom.rutas.util.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static mx.gob.imss.mssistrans.ccom.rutas.util.ResponseEntityUtil.sendResponse;


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
    public ResponseEntity<Response<Page<ControlRutasTablaResponse>>> consultarRutas(
            @RequestParam Integer pagina,
            @RequestParam(defaultValue = "10") Integer tamanio,
            @RequestParam(value = "sort", defaultValue = "idControlRuta,desc") String[] sort,
            @RequestParam String columna) throws UserUnauthorizedException {
        log.info(columna);
        String usuario = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("usuario {}", usuario);

        if (ResponseUtil.getAccess()) {
            throw new UserUnauthorizedException();
        }
        Gson gson = new Gson();
        DatosUsuario datosUsuarios = gson.fromJson(usuario, DatosUsuario.class);
        Pageable pageable = PageRequest.of(pagina, tamanio, Sort.by(ControllersUtil.convertSort(sort)));
        Response<Page<ControlRutasTablaResponse>> response = rutasForaneasService
                .consultarRutas(datosUsuarios, pageable);
        return sendResponse(response);
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
