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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.service.TarjetasElectronicasService;




@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/tarjetas")
@CrossOrigin(methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })

public class TarjetasElectronicasController {
	@Autowired
	private TarjetasElectronicasService tarjetasElectronicasService;

	/**
	 * Consultar las tarjetas de combustible disponibles por IdOOAD se obtiene del token
	 *
	 * @param idOOAD
	 *
	 * @return
	 */
	@GetMapping
	public ResponseEntity<Respuesta<?>> consultarTarjetasCombustible() {
	
	Respuesta<?> response =tarjetasElectronicasService.busquedaTarjetasDigitales();
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
