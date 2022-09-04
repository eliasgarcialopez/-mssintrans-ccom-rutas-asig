package mx.gob.imss.mssistrans.ccom.rutas.model;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DetRutasAsigEntity {

	/************ Datos de la Asignacion *********************/
	private DatosAsigEntity datosAsignacion;
	/************ Tripulacion Asignada *********************/
	private List<TripulacionAsigGroupEntity> tripulacion;
	/************ Registro del Recorrido *********************/
	private RegistroRecorridoEntity registroRecorrido;
}

