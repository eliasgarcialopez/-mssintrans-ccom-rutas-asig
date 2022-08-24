package mx.gob.imss.mssintetrans.ccom.rutas.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ControlRutasTablaResponse {
	private Integer idControlRuta;
	private Integer idRuta;
	private String folioRuta;
	private Integer idSolicitudTraslado;
	private String origen;
	private String modulo;
	private String ecco;

}
