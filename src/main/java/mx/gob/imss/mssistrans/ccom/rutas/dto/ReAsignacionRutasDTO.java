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
public class ReAsignacionRutasDTO {

	@JsonProperty
	private String idRuta;
	@JsonProperty
	private String idChofer;
	@JsonProperty
	private String desMotivoReasig;
	@JsonProperty
	private String desSiniestro;
	@JsonProperty
	private String idVehiculoSust;
	@JsonProperty
	private String idChoferSust;
	@JsonProperty
	private String idAsignacion;
	@JsonProperty
	private String cveMatricula;
	@JsonProperty
	private String fecAlta;
	@JsonProperty  
	private String indActivo;
	@JsonProperty
	private String indSistema;
	@JsonProperty
	private String idVehiculo;
	
}

