/**
 * 
 */
package mx.gob.imss.mssistrans.reasignacion.rutas.model;

import java.util.Date;

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

/**
 * @author brquezada
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SINTRANST_REASIGNACION_RUTAS")
public class ReasignacionRutasEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_REASIGNACION", unique = false, nullable = true)
	private Integer idReasignacion;
	
	@Column(name = "ID_RUTA", unique = false, nullable = true)
	private Integer idRuta;
	
	@Column(name = "ID_CHOFER", unique = false, nullable = true)
	private Integer idChofer;
	
	@Column(name = "DES_MOTIVO_REASIGNACION", unique = false, nullable = true)
	private String motivo;
	
	@Column(name = "SINIESTRO", unique = false, nullable = true)
	private String siniestro;
	
//	@Column(name = "ID_VEHICULO_SUST", unique = false, nullable = true)
	@OneToOne
	@JoinColumn(name = "ID_VEHICULO_SUST")
	private VehiculosEntity idVehiculoSust;
	
//	@Column(name = "ID_CHOFER_SUST", unique = false, nullable = true)
	@OneToOne
	@JoinColumn(name = "ID_CHOFER_SUST")
	private ChoferesEntity idChoferSust;
	
	@Column(name = "CVE_MATRICULA", unique = false, nullable = true)
	private String cveMatricula;
	
	@Column(name = "FEC_ALTA", unique = false, nullable = true)
	private Date fecAlta;
	
	@Column(name = "FEC_ACTUALIZACION", unique = false, nullable = true)
	private Date fecActualizacion;
	
	@Column(name = "FEC_BAJA", unique = false, nullable = true)
	private Date fecBaja;
	
	@Column(name = "IND_ACTIVO", unique = false, nullable = true)
	private Integer indActivo;
	
	@Column(name = "IND_SISTEMA", unique = false, nullable = true)
	private Integer indSistema;
}
