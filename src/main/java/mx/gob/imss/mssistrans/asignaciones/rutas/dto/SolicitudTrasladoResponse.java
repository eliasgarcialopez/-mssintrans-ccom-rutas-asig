package mx.gob.imss.mssistrans.asignaciones.rutas.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "SolicitudTraslado")
public class SolicitudTrasladoResponse {

	@JsonProperty
	private String idSolicitud;
	

}
