package mx.gob.imss.mssistrans.asignaciones.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "BitacoraServicio")
public class BitacoraServicio {

	@JsonProperty
	private Integer idOoad; 
	
	@JsonProperty
	private String tipoMov;
	
	@JsonProperty
	private Integer id;
	
	@JsonProperty
	private String fechaBitacora;
	
	@JsonProperty
	private String matricula;
	
}
