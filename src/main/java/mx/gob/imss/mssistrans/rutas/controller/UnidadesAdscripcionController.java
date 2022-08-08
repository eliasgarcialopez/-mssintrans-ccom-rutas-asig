package mx.gob.imss.mssistrans.rutas.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.rutas.dto.Respuesta;
import mx.gob.imss.mssistrans.rutas.dto.RutasRequest;
import mx.gob.imss.mssistrans.rutas.dto.RutasResponse;
import mx.gob.imss.mssistrans.rutas.model.Rutas;
import mx.gob.imss.mssistrans.rutas.service.RutasService;
import mx.gob.imss.mssistrans.rutas.service.UnidadesAdscripcionService;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/unidades")
@CrossOrigin(methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })

public class UnidadesAdscripcionController {
	
	@Autowired
	private UnidadesAdscripcionService uAdscripcionService;
	
	
	/**
	 * Consultar todas las unidades de adscripcion del sistema
	 *
	
	 * @return
	 */
	@GetMapping
	public ResponseEntity<Respuesta<?>> consultarUnidadesAdscripcion() {
		Respuesta<?> response =uAdscripcionService.consultarUnidadesAdscripcion();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	/**
	 * Consultar todas las unidades de adscripcion por OOAD con o sin pernocta
	 *
	
	 * @return
	 */
	@GetMapping("/filtro")
	public ResponseEntity<Respuesta<?>> consultarUnidadesAdscripcionFiltro(@RequestParam Integer ooadId,@RequestParam boolean pernocta) {
		Respuesta<?> response =uAdscripcionService.consultarUnidadesAdscripcionByIdOOA(ooadId,pernocta);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	/**
	 * Consultar todas las OOADS del Sistema
	 *
	
	 * @return
	 */
	@GetMapping("/ooad")
	public ResponseEntity<Respuesta<?>> consultarOOAD() {
		Respuesta<?> response =uAdscripcionService.consultarOOADS();
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
