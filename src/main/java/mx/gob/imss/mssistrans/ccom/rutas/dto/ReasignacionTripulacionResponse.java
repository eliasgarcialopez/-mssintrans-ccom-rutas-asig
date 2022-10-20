package mx.gob.imss.mssistrans.ccom.rutas.dto;


import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author opimentel
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "TripulacionAsignada")
public class ReasignacionTripulacionResponse {
    private String idControlRuta;
    private String idChofer;
    private String nombreChofer;
    private String matriculaChofer;
    private String nombreCamillero1;
    private String matriculaCamillero1;
    private String nombreCamillero2;
    private String matriculaCamillero2;
    private String numTarjetaDig;
    private String desMotivoReasig;
    private String desSiniestro;
}
