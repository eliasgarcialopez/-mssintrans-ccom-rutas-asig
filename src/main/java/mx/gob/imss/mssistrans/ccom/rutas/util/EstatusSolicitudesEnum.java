package mx.gob.imss.mssistrans.ccom.rutas.util;

/**
 * @author esa
 */
public enum EstatusSolicitudesEnum {
    Aceptada("1"),
    Cancelada("2"),
    Rechazada("3"),
    Finalizado("4"),
    Asignada("5");

    private final String valor;

    EstatusSolicitudesEnum(String estatus) {
        this.valor = estatus;
    }

    public String getValor() {
        return valor;
    }
}
