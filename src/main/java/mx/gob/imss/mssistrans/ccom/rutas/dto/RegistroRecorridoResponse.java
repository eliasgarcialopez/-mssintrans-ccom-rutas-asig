package mx.gob.imss.mssistrans.ccom.rutas.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "RegistroRecorrido")
public class RegistroRecorridoResponse {

	@JsonProperty
	private String idVehiculo;
	@JsonProperty
	private String inicioAsignacion;
	@JsonProperty
	private String inicioRuta1;
	@JsonProperty
	private String horaInicioRuta1;
	@JsonProperty
	private String horaFinRuta1;
	@JsonProperty
	private String inicioRuta2;
	@JsonProperty
	private String horaInicioRuta2;
	@JsonProperty
	private String horaFinRuta2;
	@JsonProperty
	private String inicioRuta3;
	@JsonProperty
	private String horaInicioRuta3;
	@JsonProperty
	private String horaFinRuta3;
	@JsonProperty
	private String rutaAsignacion;
	@JsonProperty
	private String desTipoIncidente;
}
