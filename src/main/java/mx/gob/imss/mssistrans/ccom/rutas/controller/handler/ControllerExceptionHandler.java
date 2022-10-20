package mx.gob.imss.mssistrans.ccom.rutas.controller.handler;

import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;
import mx.gob.imss.mssistrans.ccom.rutas.exceptions.EntityNotFoundException;
import mx.gob.imss.mssistrans.ccom.rutas.exceptions.UserUnauthorizedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UserUnauthorizedException.class)
    public ResponseEntity<Object> handleUserUnauthorized(
            UserUnauthorizedException ex, WebRequest request) {
        Response<Object> response = createErrorResponse(ex, request);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo()));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        Response<Object> response = createErrorResponse(ex, request);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo()));
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final Response<Map<String, String>> response = new Response<>();
        response.setMensaje("Hay errores en los datos, favor de revisar la informacion");
        response.setError(true);
        response.setCodigo(HttpStatus.BAD_REQUEST.value());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errors.put(campo, mensaje);
        });
        response.setDatos(errors);
        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }

    private static <T extends Exception> Response<Object> createErrorResponse(T ex, WebRequest request) {
        Response<Object> response = new Response<>();
        response.setCodigo(HttpStatus.UNAUTHORIZED.value());
        response.setError(true);
        response.setMensaje("request: " + request.getContextPath() + ", mensaje: " + ex.getMessage());
        return response;
    }
}
