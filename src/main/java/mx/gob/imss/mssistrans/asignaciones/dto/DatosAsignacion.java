package mx.gob.imss.mssistrans.asignaciones.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "datosAsignacion")
public class DatosAsignacion {
	
	@JsonProperty
	private Integer idAsignacion;
	
	@JsonProperty
	private String numAsignacion;
	
	@JsonProperty
	private String cveEcco;
	
	@JsonProperty
	private String numRuta;
	
	@JsonProperty
	private String nomChofer;

}
