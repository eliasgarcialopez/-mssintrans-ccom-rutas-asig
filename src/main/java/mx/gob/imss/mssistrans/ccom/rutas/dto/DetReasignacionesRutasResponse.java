package mx.gob.imss.mssistrans.ccom.rutas.dto;



import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.gob.imss.mssistrans.ccom.rutas.model.DatosAsigEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.DatosControlRutasEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.ReasignacionTripulacionGroupEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "DetallesRutasAsignaciones")
public class DetReasignacionesRutasResponse {

	@JsonProperty
	private DatosControlRutasEntity datosReasignacion;
	@JsonProperty
	private ReasignacionTripulacionGroupEntity tripulacion;
}
