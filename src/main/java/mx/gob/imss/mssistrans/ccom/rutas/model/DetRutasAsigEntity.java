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
	private DatosAsigEntity datosAsigEntity;
	/************ Tripulacion Asignada *********************/
	private List<TripulacionAsigGroupEntity> tripulacionAsigGroupEntity;
	/************ Registro del Recorrido *********************/
	private RegistroRecorridoEntity registroRecorridoEntity;
}

