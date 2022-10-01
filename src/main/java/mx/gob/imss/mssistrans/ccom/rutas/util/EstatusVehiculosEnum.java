package mx.gob.imss.mssistrans.ccom.rutas.util;

/**
 * @author esa
 */
public enum EstatusVehiculosEnum {
    En_Operacion("8"),
    En_Transito("9");

    private final String valor;

    EstatusVehiculosEnum(String estatus) {
        this.valor = estatus;
    }

    public String getValor() {
        return valor;
    }
}
