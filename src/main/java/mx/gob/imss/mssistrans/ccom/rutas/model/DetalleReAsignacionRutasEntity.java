package mx.gob.imss.mssistrans.ccom.rutas.model;

import java.time.LocalDateTime;

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
@Table(name = "SINTRANST_REASIGNACION_RUTAS")
public class DetalleReAsignacionRutasEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_REASIGNACION", unique = false, nullable = true)
	private Integer idReasignacion;

	@Column(name = "ID_RUTA", unique = false, nullable = true)
	private Integer idRuta;
	@Column(name = "ID_CHOFER", unique = false, nullable = true)
	private Integer idChofer;
	@Column(name = "DES_MOTIVO_REASIGNACION", unique = false, nullable = true)
	private String desMotivoReasig;
	
	@Column(name = "DES_SINIESTRO", unique = false, nullable = true)
	private String desSiniestro;
	@Column(name = "ID_VEHICULO_SUST", unique = false, nullable = true)
	private Integer idVehiculoSust;
	@Column(name = "ID_CHOFER_SUST", unique = false, nullable = true)
	private Integer idChoferSust;
	@Column(name = "ID_ASIGNACION", unique = false, nullable = true)
	private Integer idAsignacion;
	@Column(name = "CVE_MATRICULA", unique = false, nullable = true)
	private String cveMatricula;
	@Column(name = "FEC_ALTA", unique = false, nullable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime  fecAlta;
	@Column(name = "ID_VEHICULO", unique = false, nullable = true)
	private Integer idVehiculo;
	@Column(name = "IND_ACTIVO", unique = false, nullable = true)
	private Integer indActivo;
	@Column(name = "IND_SISTEMA", unique = false, nullable = true)
	private Integer indSistema;
	
	
}

