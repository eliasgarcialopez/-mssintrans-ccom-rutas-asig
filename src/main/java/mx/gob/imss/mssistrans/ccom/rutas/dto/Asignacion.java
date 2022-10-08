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
	private Integer idAsignacion;  // no va
	
	@JsonProperty
	private String numAsignacion; // ya va en la implementacion
	
	@JsonProperty
	private Integer idVehiculo; // ya se manda desde el alta de ruta
	
	@JsonProperty
	private Long idChofer; // No va
	
	@JsonProperty
	private Integer idRuta; // Nova
	
	@JsonProperty
	private String desEstatus;
	
}
