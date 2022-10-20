package mx.gob.imss.mssistrans.ccom.rutas.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author opimentel
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TripulacionResponse {
    private Integer idTripulacion;
    private String fecFecha;
    private Integer idVehiculo;
    private String nombreChofer;
    private String cveMatriculaChofer;
    private String nombreCamillero1;
    private String cveMatriculaCamillero1;
    private String nombreCamillero2;
    private String cveMatriculaCamillero2;
}
 