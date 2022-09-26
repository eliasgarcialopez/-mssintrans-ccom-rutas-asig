package mx.gob.imss.mssistrans.ccom.rutas.model;

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
	@Column(name = "TIM_INICIO_ASIGNA", unique = false, nullable = true)
	private String inicioAsignacion;
	@Column(name = "DES_ESTATUS_ASIGNA", unique = false, nullable = true)
	private String estatus;
	@Column(name = "ID_DESTINO", unique = false, nullable = true)
	private String inicioRuta;
	@Column(name = "TIM_HORA_INICIO", unique = false, nullable = true)
	private String horaInicio;
	@Column(name = "TIM_HORA_FIN", unique = false, nullable = true)
	private String horaFinRuta;

}

