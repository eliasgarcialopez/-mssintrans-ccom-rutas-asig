package mx.gob.imss.mssistrans.ccom.rutas.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.valueOf;

/**
 * @author esa
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseEntityUtil {


    /**
     * Responde un request con la respuesta que llegue desde el servicio
     *
     * @param response
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity<Response<T>> sendResponse(Response<T> response) {
        return new ResponseEntity<>(response, valueOf(response.getCodigo()));
    }

}
