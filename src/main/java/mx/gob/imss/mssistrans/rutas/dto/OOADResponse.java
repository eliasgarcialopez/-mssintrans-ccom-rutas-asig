package mx.gob.imss.mssistrans.rutas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OOADResponse {

    private Integer idOOAD;	 
    private String nomOOAD;
}
