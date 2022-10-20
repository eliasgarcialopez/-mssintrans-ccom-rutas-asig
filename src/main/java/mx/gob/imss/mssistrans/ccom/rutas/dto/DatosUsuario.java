package mx.gob.imss.mssistrans.ccom.rutas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@JsonRootName(value = "DatosUsuario")
public class DatosUsuario {
    @JsonProperty
    public String rol;

    @JsonProperty
    public String matricula;

    @SerializedName("IDOOAD")
    public Integer idOoad;

    @JsonProperty
    @SerializedName("CVEDEPARTAMENTO")
    public String cveDepartamento;
}
