package mx.gob.imss.mssistrans.asignaciones.rutas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SINTRANST_RUTAS_ASIGNACIONES")
public class RegistroRecorridoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_VEHICULO", unique = false, nullable = true)
	private String idVehiculo;
	@Column(name = "TIM_HORA_INICIO_ASIGNACION", unique = false, nullable = true)
	private String inicioAsignacion;
	@Column(name = "ID_RUTA1", unique = false, nullable = true)
	private String inicioRuta1;
	@Column(name = "TIM_HORA_INICIO_RUTA_1", unique = false, nullable = true)
	private String horaInicioRuta1;
	@Column(name = "TIM_HORA_FIN_RUTA_1", unique = false, nullable = true)
	private String horaFinRuta1;
	@Column(name = "ID_RUTA2", unique = false, nullable = true)
	private String inicioRuta2;
	@Column(name = "TIM_HORA_INICIO_RUTA_2", unique = false, nullable = true)
	private String horaInicioRuta2;
	@Column(name = "TIM_HORA_FIN_RUTA_2", unique = false, nullable = true)
	private String horaFinRuta2;
	@Column(name = "ID_RUTA3", unique = false, nullable = true)
	private String inicioRuta3;
	@Column(name = "TIM_HORA_INICIO_RUTA_3", unique = false, nullable = true)
	private String horaInicioRuta3;
	@Column(name = "TIM_HORA_FIN_RUTA_3", unique = false, nullable = true)
	private String horaFinRuta3;
	@Column(name = "ID_RUTA_ASIGNACION", unique = false, nullable = true)
	private String rutaAsignacion;
	@Column(name = "DES_TIPO_INCIDENTE", unique = false, nullable = true)
	private String desTipoIncidente;
}

