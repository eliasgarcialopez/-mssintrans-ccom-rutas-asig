package mx.gob.imss.mssistrans.asignaciones.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SINTRANST_PRESTAMOS")
public class PrestamosEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PRESTAMO", unique = true, nullable = true)
	private Integer idPrestamo;

	@OneToOne
	@JoinColumn(name = "ID_VEHICULO")
	private VehiculosEntity vehiculo;
	
	@Column(name = "DES_TIPO_PRESTAMO")
	private String desTipoPrestamo;

	@OneToOne
	@JoinColumn( name="ID_OOAD", unique = false, nullable = true)
	private OoadEntity ooad;

	@Column(name = "ID_UNIDAD_ADSC_PRESTATARIA", unique = false, nullable = true)
	private Integer idUnidad;

	@Column(name = "FEC_ENTREGA")
	private Date fechaEntrega;

	@Column(name = "TIM_ENTREGA")
	private String timEntrega;

	@Column(name = "FEC_DEVOLUCION")
	private Date fechaDevolucion;

	@Column(name = "TIM_DEVOLUCION")
	private String timDevolucion;

	@Column(name = "CVE_MATRICULA")
	private String cveMatricula;

	@Column(name = "FEC_ALTA")
	private Date fecAlta;

	@Column(name = "FEC_ACTUALIZACION")
	private Date fecActualizacion;

	@Column(name = "FEC_BAJA")
	private Date fecBaja;

	@Column(name = "IND_ACTIVO")
	private Boolean indActivo;

	@Column(name = "IND_SISTEMA")
	private Boolean indSistema;

}
