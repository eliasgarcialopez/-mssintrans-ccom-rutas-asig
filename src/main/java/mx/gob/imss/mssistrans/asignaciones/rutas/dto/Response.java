package mx.gob.imss.mssistrans.asignaciones.rutas.dto;

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
@JsonRootName(value = "response")
public class Response<T> {
    @JsonProperty
    public Integer codigo;

    @JsonProperty
    public String mensaje;

    @JsonProperty
    public boolean error;

    @JsonProperty
    private T datos;
    
}
