package mx.gob.imss.mssintetrans.ccom.rutas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormatoPdf {

	@JsonProperty
	private String inmueble;
	
	@JsonProperty
	private String fecha;
	
	@JsonProperty
	private String marca;
	
	@JsonProperty 
	private String tipo;
	
	@JsonProperty 
	private String placas;
	
	@JsonProperty 
	private String modelo;
	
	@JsonProperty 
	private String unidadAdscripcion;
	
	@JsonProperty 
	private String tipoServicio;
	
	@JsonProperty 
	private String idRuta;
}
