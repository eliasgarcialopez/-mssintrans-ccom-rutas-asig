package mx.gob.imss.mssistrans.ccom.rutas.dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.gob.imss.mssistrans.ccom.rutas.model.DatosAsigEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.RegistroRecorridoEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigGroupEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "DetallesRutasAsignaciones")
public class DetRutasAsignacionesResponse {

	@JsonProperty
	private DatosAsigEntity datosAsigEntity;
	@JsonProperty
	private List<TripulacionAsigGroupEntity> tripulacionAsigGroupEntity;
	@JsonProperty
	private RegistroRecorridoEntity registroRecorridoEntity;
}
