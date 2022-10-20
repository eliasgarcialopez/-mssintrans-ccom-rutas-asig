package mx.gob.imss.mssistrans.ccom.rutas.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author opimentel
 *
 * @param <T>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "response")
public class Response<T> {
    private Integer codigo;
    private String mensaje;
    private boolean error;
    private T datos;
}
