package mx.gob.imss.mssistrans.reasignacion.rutas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "datosReasignacion")
public class DatosReasignacion {

	@JsonProperty
	private Integer idRuta;
	
	@JsonProperty
	private String numAsignacion;
	
	@JsonProperty
	private String cveEcco;
	
	@JsonProperty
	private String placas;
	
	@JsonProperty
	private String nomChofer;
}
