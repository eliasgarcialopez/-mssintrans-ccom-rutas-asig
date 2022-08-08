package mx.gob.imss.mssistrans.reasignacion.rutas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@Data
@JsonRootName(value = "testResponse")
public class TestResponse {
	
	@JsonProperty
	private Integer idReasignacion;
	@JsonProperty
	private Integer idRuta;
	@JsonProperty
	private String cveEcco;
	@JsonProperty
	private String numPlacas;
	@JsonProperty
	private String nombreChofer;

}
