package mx.gob.imss.mssistrans.ccom.rutas.controller;

import java.util.HashMap;
import java.util.Map;

import mx.gob.imss.mssistrans.ccom.rutas.dto.Respuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.service.TripulacionService;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/tripulacion")
@CrossOrigin(methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })

public class TripulacionController {
	@Autowired
	private TripulacionService triuService;

	/**
	 * Consultar la tripulacion  por idVehiculo
	 *
	 * @param idOOAD
	 *
	 * @return
	 */
	@GetMapping
	public ResponseEntity<Respuesta<?>> consultaTripulacion(@RequestParam Integer idVehiculo) {
	
	Respuesta<?> response =triuService.findByIdVehiculo(idVehiculo);
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
