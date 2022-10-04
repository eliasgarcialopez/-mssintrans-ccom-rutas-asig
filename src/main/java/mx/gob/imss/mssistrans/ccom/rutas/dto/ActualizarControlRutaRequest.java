package mx.gob.imss.mssistrans.ccom.rutas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActualizarControlRutaRequest {
    private Integer idControlRuta;
    private Integer idSolicitud;
    private String estatusAsignado;
    private String estatusRecorrido;
    private Integer idRutaOrigen;
    private String horaInicioOrigen;
    private String horaFinOrigen;
    private Integer idRutaDestino;
    private String horaInicioDestino;
    private String horaFinDestino;
    private String idTipoIncidente;
}
