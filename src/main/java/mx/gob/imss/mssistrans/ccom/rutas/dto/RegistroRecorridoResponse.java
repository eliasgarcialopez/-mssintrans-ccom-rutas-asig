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
	private String estatus;
	@JsonProperty
	private String inicioRuta;
	@JsonProperty
	private String horaInicio;
	@JsonProperty
	private String horaFinRuta;
}
