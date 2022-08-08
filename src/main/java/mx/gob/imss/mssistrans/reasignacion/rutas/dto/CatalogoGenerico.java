package mx.gob.imss.mssistrans.reasignacion.rutas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Brayan Ramiro Quezada Hernandez
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "catalogoGenerico")
public class CatalogoGenerico {
	
	@JsonProperty
	private Integer id;
	
	@JsonProperty
	private String descripcion;

}
