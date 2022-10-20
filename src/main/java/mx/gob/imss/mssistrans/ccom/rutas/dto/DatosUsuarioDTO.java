package mx.gob.imss.mssistrans.ccom.rutas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.gson.annotations.SerializedName;
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
    @SerializedName("IDOOAD")
    public Integer idOoad;
    @JsonProperty
    @SerializedName("CVEDEPARTAMENTO")
    public Integer idUnidadAdscripcion;
}