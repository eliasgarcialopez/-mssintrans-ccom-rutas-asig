package mx.gob.imss.mssistrans.asignaciones.rutas.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "Rutas")
public class RutasResponse {

	@JsonProperty
	private String idRuta;
	
	@JsonProperty
	private String desServicio;

}
