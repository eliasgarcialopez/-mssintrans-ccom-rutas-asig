package mx.gob.imss.mssistrans.ccom.rutas.exceptions;

import java.io.IOException;

public class FileException extends RuntimeException {
    public FileException(IOException e) {
        super(e);
    }
}
