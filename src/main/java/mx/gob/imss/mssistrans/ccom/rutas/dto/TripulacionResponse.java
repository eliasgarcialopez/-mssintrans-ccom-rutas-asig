package mx.gob.imss.mssistrans.ccom.rutas.dto;




import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

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
 