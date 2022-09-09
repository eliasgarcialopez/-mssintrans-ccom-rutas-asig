package mx.gob.imss.mssistrans.ccom.rutas.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author opimentel
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "TripulacionAsignada")
public class ReasignacionTripulacionResponse {

	@JsonProperty
	private String idControlRuta;
	
	@JsonProperty
	private String idChofer;

	@JsonProperty
	private String nombreChofer;

	@JsonProperty
	private String matriculaChofer;
	
	@JsonProperty
	private String nombreCamillero1;

	@JsonProperty
	private String matriculaCamillero1;
	
	@JsonProperty
	private String nombreCamillero2;
	
	@JsonProperty
	private String matriculaCamillero2;

	@JsonProperty
	private String numTarjetaDig;

	@JsonProperty
	private String desMotivoReasig;
	
	@JsonProperty
	private String desSiniestro;
}
