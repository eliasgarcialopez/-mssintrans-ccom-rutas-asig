package mx.gob.imss.mssistrans.ccom.rutas.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DetReasignacionRutasEntity {

	/************ Datos de la Asignacion *********************/
	private DatosAsigEntity datosReasignacion;
	/************ Tripulacion Asignada *********************/
	private ReasignacionTripulacionGroupEntity tripulacion;
}

