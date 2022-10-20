package mx.gob.imss.mssistrans.ccom.rutas.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserUnauthorizedException extends Exception {
    private static final String MESSAGE = "Usuario no autorizado";
    public UserUnauthorizedException() {
        super(MESSAGE);
    }
}
