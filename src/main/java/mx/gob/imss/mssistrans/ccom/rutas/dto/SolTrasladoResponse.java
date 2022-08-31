package mx.gob.imss.mssistrans.ccom.rutas.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author opimentel
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "SolicitudTraslado")
public class SolTrasladoResponse {

	@JsonProperty
	private String idSolicitud;
	

}
