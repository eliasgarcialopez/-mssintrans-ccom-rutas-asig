package mx.gob.imss.mssistrans.rutas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnidadesAdscripcionResponse {

    private Integer idUnidadAdscripcion;

	private String nomUnidadAdscripcion;
    
  
   private boolean indUnidadPerNOCTA;

   private String desCalleNUM;
   
}
