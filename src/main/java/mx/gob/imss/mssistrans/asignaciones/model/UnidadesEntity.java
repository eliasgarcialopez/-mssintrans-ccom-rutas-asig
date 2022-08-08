package mx.gob.imss.mssistrans.asignaciones.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SINTRANSC_UNIDADES_ADSCRIPCION")
public class UnidadesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_UNIDAD_ADSCRIPCION", unique = false, nullable = true)
	private Integer idUnidad;

	@Column(name = "NOM_UNIDAD_ADSCRIPCION", unique = false, nullable = true)
	private String nomUnidadAdscripcion;

	@OneToOne
	@JoinColumn( name="ID_OOAD", unique = false, nullable = true)
	private OoadEntity ooad;
	
	@Column(name = "DES_TIPO_UNIDAD", unique = false, nullable = true)
	private String desTipoUnidad;

	@Column(name = "IND_UNIDAD_PERNOCTA", unique = false, nullable = true)
	private Integer indUnidadPernocta;

	@Column(name = "NUM_UN_INF", unique = false, nullable = true)
	private String numUnInf;

	@Column(name = "NUM_UN_OPE", unique = false, nullable = true)
	private String numUnOpe;

	@Column(name = "NUM_CC", unique = false, nullable = true)
	private String numCc;

	@Column(name = "NUM_CU", unique = false, nullable = true)
	private String numCu;

	@Column(name = "NUM_DIV", unique = false, nullable = true)
	private String numDiv;

	@Column(name = "NUM_SDIV", unique = false, nullable = true)
	private String numSdiv;

	@Column( name="ID_CODIGO_POSTAL", unique = false, nullable = true)
	private Integer idCodigoPostal;

	@Column(name = "NOM_COLONIA", unique = false, nullable = true)
	private String nomColonia;

	@Column(name = "CVE_MATRICULA", unique = false, nullable = true)
	private String cveMatricula;

	@Column(name = "FEC_ALTA", unique = false, nullable = true)
	private String fecAlta;

	@Column(name = "FEC_ACTUALIZACION", unique = false, nullable = true)
	private String fecActualizacion;

	@Column(name = "FEC_BAJA", unique = false, nullable = true)
	private String fecBaja;
	
	@Column(name = "IND_ACTIVO", unique = false, nullable = true)
	private Integer indActivo;

	@Column(name = "IND_SISTEMA", unique = false, nullable = true)
	private Integer indSistema;

	
}
