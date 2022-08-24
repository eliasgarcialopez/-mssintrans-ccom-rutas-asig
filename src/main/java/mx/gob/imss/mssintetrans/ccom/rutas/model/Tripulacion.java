package mx.gob.imss.mssintetrans.ccom.rutas.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "SINTRANST_TRIPULACION")
@Getter
@Setter
public class Tripulacion implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TRIPULACION")
	private Integer idTripulacion;

	@Basic
	@Column(name = "FEC_FECHA")
	private LocalDate fecFecha;

	@Basic
	@Column(name = "ID_VEHICULO")
	private Integer idVehiculo;

	@Basic
	@Column(name = "CVE_MATRICULA_CHOFER")
	private String cveMatriculaChofer;

	@Basic
	@Column(name = "CVE_MATRICULA_CAMILLERO1")
	private String cveMatriculaCamillero1;

	@Basic
	@Column(name = "CVE_MATRICULA_CAMILLERO2")
	private String cveMatriculaCamillero2;
	
	@Basic
	@Column(name = "FEC_ALTA")
	private LocalDate fechaAlta;
	
	@Basic
	@Column(name = "FEC_ACTUALIZACION")
	private LocalDate fechaActualizacion;
	
	@Basic
	@Column(name = "FEC_BAJA")
	private LocalDate fechaBaja;

	@Basic
	@Column(name = "IND_ACTIVO")
	private boolean activo;
	@Basic
	@Column(name = "IND_SISTEMA")
	private boolean indiceSistema;
}
