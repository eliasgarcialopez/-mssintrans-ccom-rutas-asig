package mx.gob.imss.mssistrans.ccom.rutas.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String mensaje) {
        super(mensaje);
    }
}
