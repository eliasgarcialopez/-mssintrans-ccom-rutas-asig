package mx.gob.imss.mssistrans.ccom.rutas.dto;




import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehiculoResponse {
	
	private Integer idVehiculo;	
	private String cveEcco;
	

	    
}
