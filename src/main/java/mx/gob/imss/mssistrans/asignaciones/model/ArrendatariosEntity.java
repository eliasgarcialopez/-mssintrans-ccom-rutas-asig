package mx.gob.imss.mssistrans.asignaciones.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SINTRANST_ARRENDATARIOS")

public class ArrendatariosEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ARRENDATARIO ", unique = false, nullable = true)
	private Integer idArrentadario;
	
	@Column(name = "NOM_ARRENDADORA", unique = false, nullable = true)
	private String nomArrendadora;
	
	@Column(name = "NUM_CONTRATO", unique = false, nullable = true)
	private Integer numContrato;
	
	@Column(name = "FEC_INI_CONTRATO", unique = false, nullable = true)
	private Date fecIniContrato;
	
	@Column(name = "FEC_FIN_CONTRATO", unique = false, nullable = true)
	private Date fecFinContrato;
	
	@Column(name = "IMP_COSTO_DIARO", unique = false, nullable = true)
	private Double impCostoDiario;
	
	@Column(name = "IMP_COSTO_MENSUAL", unique = false, nullable = true)
	private Double impCostoMensual;
	
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

