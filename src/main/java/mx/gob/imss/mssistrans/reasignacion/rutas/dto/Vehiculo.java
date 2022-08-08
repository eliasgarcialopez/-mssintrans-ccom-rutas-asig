package mx.gob.imss.mssistrans.reasignacion.rutas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "vehiculo")
public class Vehiculo {
	
	@JsonProperty
	private Integer idVehiculo;
	@JsonProperty
	private String cveEcco;
	@JsonProperty
	private String numPlacas;
}
