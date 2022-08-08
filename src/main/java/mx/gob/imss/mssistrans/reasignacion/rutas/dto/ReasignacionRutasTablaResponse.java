package mx.gob.imss.mssistrans.reasignacion.rutas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@Data
@JsonRootName(value = "reasignacionRutasTablaResponse")
public class ReasignacionRutasTablaResponse {
	
	@JsonProperty
	private Integer idReasignacion;
	@JsonProperty
	private Integer idRuta;
	@JsonProperty
	private Vehiculo idVehiculoSust;
	@JsonProperty
	private Chofer idChoferSust;

}
