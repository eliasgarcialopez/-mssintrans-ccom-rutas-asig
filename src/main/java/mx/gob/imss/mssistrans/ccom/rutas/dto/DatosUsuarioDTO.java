package mx.gob.imss.mssistrans.ccom.rutas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author opimentel
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "DatosUsuarioDTO")
public class DatosUsuarioDTO {
    @JsonProperty
    public String rol;
    @JsonProperty
    public String matricula;
    @JsonProperty
    public Integer IDOOAD;
    @JsonProperty
    public Integer idUnidadAdscripcion;
}