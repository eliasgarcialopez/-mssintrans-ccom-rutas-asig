package mx.gob.imss.mssistrans.ccom.rutas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "asignacion")
public class Asignacion {

	@JsonProperty
	private Integer idAsignacion;
	
	@JsonProperty
	private String numAsignacion;
	
	@JsonProperty
	private Integer idVehiculo;
	
	@JsonProperty
	private String cveEcco;
	
	@JsonProperty
	private String desMarca;

	@JsonProperty
	private String numPlacas;
	
	@JsonProperty
	private String desModelo;
	
	@JsonProperty
	private Long idChofer;
	
	@JsonProperty
	private Integer idRuta;
	
	@JsonProperty
	private String numFolioTarjeta;
	
	@JsonProperty
	private String desEstatus;
	
}
