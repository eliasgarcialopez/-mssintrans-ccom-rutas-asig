package mx.gob.imss.mssistrans.ccom.rutas.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "DatosControlRutaDTO")
public class DatosControlRutaDTO {
	
	private Integer idControlRuta;
	private Integer idVehiculo;
	private String cveEcco;
	private String numPlacas;
	private Integer idRuta;
	private Integer idSolicitud;
	private String desEstatus;
	private String desTipoIncidente;

}
