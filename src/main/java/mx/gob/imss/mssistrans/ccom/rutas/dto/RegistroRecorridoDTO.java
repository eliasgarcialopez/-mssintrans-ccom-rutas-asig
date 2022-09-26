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
public class RegistroRecorridoDTO {

	@JsonProperty
	private String idRuta;
	@JsonProperty
	private String idSolicitud;
	@JsonProperty
	private String idVehiculo;
	@JsonProperty
	private String desEstatusRuta;
	@JsonProperty
	private String horaInicioAsignacion;
	@JsonProperty
	private String estatusTraslado;
	@JsonProperty
	private String idRuta1;
	@JsonProperty
	private String hrInicio1;
	@JsonProperty
	private String hrFin1;
	@JsonProperty
	private String idRuta2;
	@JsonProperty
	private String hrInicio2;
	@JsonProperty
	private String hrFin2;
	@JsonProperty
	private String idRuta3;
	@JsonProperty
	private String hrInicio3;
	@JsonProperty
	private String hrFin3;	
	@JsonProperty
	private String desTipoIncidente;
}
