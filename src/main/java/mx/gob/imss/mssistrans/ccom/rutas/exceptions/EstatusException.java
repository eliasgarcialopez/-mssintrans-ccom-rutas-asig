package mx.gob.imss.mssistrans.ccom.rutas.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EstatusException extends Exception {
    public EstatusException(String mensaje) {
        super(mensaje);
    }
}
