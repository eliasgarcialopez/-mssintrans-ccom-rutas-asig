package mx.gob.imss.mssistrans.ccom.rutas.util;

/**
 * @author esa
 */
public enum EstatusControlRutasEnum {
    En_Ruta("1"),
    Cancelado("2"),
    Terminado("3"),
    Reasignado("4");

    private final String valor;

    EstatusControlRutasEnum(String estatus) {
        this.valor = estatus;
    }

    public String getValor() {
        return valor;
    }
}
