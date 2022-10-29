package mx.gob.imss.mssistrans.ccom.rutas.exceptions;

import net.sf.jasperreports.engine.JRException;

public class JasperException extends RuntimeException {
    public JasperException(JRException e) {
        super(e);
    }
}
