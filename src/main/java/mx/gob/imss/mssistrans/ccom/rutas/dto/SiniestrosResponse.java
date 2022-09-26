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
@JsonRootName(value = "SiniestrosResponse")
public class SiniestrosResponse {

	@JsonProperty
	private String idSiniestro;
	@JsonProperty
	private String numFolio;

}
