package mx.gob.imss.mssistrans.ccom.rutas.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.dto.*;
import mx.gob.imss.mssistrans.ccom.rutas.service.ControlRutasService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/asignaciones")
@CrossOrigin(methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })

public class ControlRutasController {
	@Autowired
	private ControlRutasService rutasService;

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
		if(columna.equals("idRuta")) columna="idControlRuta";
		Pageable pageable = PageRequest.of(pagina, tamanio,Sort.by(new Sort.Order(Sort.Direction.fromString(sort), columna)));
		Respuesta<?> response =rutasService.consultarRutas(pageable);
		if(response.isError()) {
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
	}

	
	/**
	 * Endpoint para consultar una Ruta por su Id
	 *
	 * @param idRuta
	 * @return
	 */
	@GetMapping("/id/{idRuta}")
	public ResponseEntity<?> consultarRuta(@PathVariable Integer idRuta) {
		Respuesta<ControlRutasResponse> response = rutasService.consultarRutas(idRuta);
		if(response.isError()) {
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
	/**
	 * Endpoint para consultar total de vehiculos por ooad se obtiene del token
	 *
	 * @param idRuta
	 * @return
	 */
	@GetMapping("/totales/")
	public ResponseEntity<?> consultarTotalesVehiculos() {
		Respuesta<ControlRutasTotalesResponse> response = rutasService.consultarTotalesVehiculos();

		if(response.isError()) {
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
	/**
	 * Endpoint para crear una Ruta
	 *
	 * @param ControlRutasRequest
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Respuesta<?>> crearRuta(@RequestBody ControlRutasRequest rutaRequest) {
		Respuesta<?> response = rutasService.crearRuta(rutaRequest);
		
		if(response.isError()) {
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	/**
	 * Endpoint para editar una Ruta
	 *
	 * @param idRuta
	 * @param RutaRequestDTO
	 * @return
	 */
	@PutMapping("/{idRuta}")
	public ResponseEntity<?> editarRuta(@PathVariable Integer idRuta, @RequestBody ControlRutasRequest rutasRequest) {
		Respuesta<?> response = rutasService.editarRuta(idRuta, rutasRequest);
		
		if(response.isError()) {
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
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

		if(response.isError()) {
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	/**
	 * Libera los recursos que tenga asignados el control de ruta
	 * @param idRuta
	 * @param params
	 * @return
	 */
	@PutMapping("/liberar-ruta/{idRuta}")
	public ResponseEntity<?> liberarControlRuta(@PathVariable Integer idRuta,
												@RequestBody LiberarControlRutasRequest params) {
		// todo - se puede pasar el idRuta o el idControlRuta
		// todo - ver que se le va a pasar al serivico para liberar los recursos
		Respuesta<?> respuesta = rutasService.liberarControlRuta(idRuta, params);
		return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getCodigo()));
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
