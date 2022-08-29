package mx.gob.imss.mssistrans.asignaciones.rutas.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "TripulacionAsignada")
public class TripulacionAsigResponse {

	@JsonProperty
	private String idVehiculo;

	@JsonProperty
	private String nombreChofer;

	@JsonProperty
	private String matriculaChofer;
	
	@JsonProperty
	private String numTarjetaDig;
	
	@JsonProperty
	private String nombreCamillero1;

	@JsonProperty
	private String matriculaCamillero1;
	
	@JsonProperty
	private String nombreCamillero2;
	
	@JsonProperty
	private String matriculaCamillero2;
}
