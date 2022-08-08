package mx.gob.imss.mssistrans.rutas.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RutasTablaResponse {
	private Integer idRuta;
	private String numFolioRuta;
	private String desServicio;
	private String unidadSolicitante;
	private String destino;

}
