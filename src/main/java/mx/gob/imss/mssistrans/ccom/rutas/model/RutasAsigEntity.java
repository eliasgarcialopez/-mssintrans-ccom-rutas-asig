package mx.gob.imss.mssistrans.ccom.rutas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SINTRANST_RUTAS")
public class RutasAsigEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_RUTA", unique = false, nullable = true)
	private String idRuta;

	@Column(name = "NUM_FOLIO_RUTA", unique = false, nullable = true)
	private String numFolioRuta;

	@Column(name = "TIM_HORARIO_INICIAL")
	private String horaInicio;
	@Column(name = "TIM_HORARIO_FINAL")
	private String horaFin;

}

