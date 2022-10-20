package mx.gob.imss.mssistrans.ccom.rutas.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ControlRutasForaneasResponse {
    private String viaticosCasetas;
    private String viaticosChofer;
    private String viaticosCamillero1;
    private String viaticosCamillero2;


}
