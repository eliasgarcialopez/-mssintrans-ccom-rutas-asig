package mx.gob.imss.mssistrans.asignaciones.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "datosVehiculo")
public class DatosVehiculo {
	
	@JsonProperty
	private String cveEcco;
	
    @JsonProperty
	private String desMarca;
	
	@JsonProperty
	private String numPlacas;
	
	@JsonProperty
	private String desModelo;
	
	@JsonProperty
	private String desTipoServicio;

}
