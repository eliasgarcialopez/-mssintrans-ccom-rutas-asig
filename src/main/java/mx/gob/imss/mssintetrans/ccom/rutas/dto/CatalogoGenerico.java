package mx.gob.imss.mssintetrans.ccom.rutas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "catalogoGenerico")
public class CatalogoGenerico {
	
	@JsonProperty
	private Integer id;
	
	@JsonProperty
	private String descripcion;
	
	@JsonProperty
	private Integer idTarjeta;

}
