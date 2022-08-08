package mx.gob.imss.mssistrans.asignaciones.dto;

import org.springframework.data.domain.Page;

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
@JsonRootName(value = "responseTO")
public class RespuestaTO<DatosAsignacion> {

    @JsonProperty
    public Integer codigo;

    @JsonProperty
    public String mensaje;

    @JsonProperty
    public boolean error;

    @JsonProperty
    private Page datos;
}
