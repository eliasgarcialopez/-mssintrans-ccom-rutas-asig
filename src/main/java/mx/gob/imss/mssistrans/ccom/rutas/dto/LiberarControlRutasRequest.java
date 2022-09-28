package mx.gob.imss.mssistrans.ccom.rutas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class LiberarControlRutasRequest {
    // todo - agregar los campos necesarios para poder liberar los recursos que esten asigandos
    private Integer idRuta;
    private String fecha;
}
