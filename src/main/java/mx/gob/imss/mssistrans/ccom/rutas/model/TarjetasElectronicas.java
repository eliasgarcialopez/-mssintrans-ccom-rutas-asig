package mx.gob.imss.mssistrans.ccom.rutas.model;

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
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SINTRANST_TARJETAS_ELECTRONICAS")
public class TarjetasElectronicas {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TARJETA_ELECTRONICA", unique = true,nullable = true)
	private Integer idTarjetaElectronica;
	
	@Column(name = "CVE_NUMERO_CONVENIO")
	private String cveNumeroConvenio;
	
	@Column(name = "NOM_EMPRESA")
	private String nomEmpresa;
	
	@Column(name = "FEC_INI_CONVENIO")
	private Date fecIniConvenio; 
	
	@Column(name = "FEC_FIN_CONVENIO")
	private Date fecFinConvenio;
	
	@Column(name = "IMP_MENSUAL")
	private Double impMensual;
	
	@Column(name = "CAN_LITROS_LIMITE_MES")
	private Double canLitrosLimiteMes;
	
	@OneToOne
	@JoinColumn( name="ID_OOAD", unique = false, nullable = true)
	private Ooad ooad;
	
	@Column(name = "NUM_FOLIO_INICIAL")
	private String numFolioInicial;
	
	@Column(name = "NUM_FOLIO_FINAL")
	private String numFolioFinal;
	
	@Column(name = "CAN_KMS_RECORRIDOS")
	private Double canKmsRecorridos;
	
	@Column(name = "DES_ESTATUS_TARJETA")
	private String desEstatusTarjeta;
	
	@Column(name = "CVE_MATRICULA")
	private String cveMatricula;
	
	@Column(name = "FEC_ALTA")
	private Date fecAlta;
	
	@Column(name = "FEC_ACTUALIZACION")
	private Date fecActualizacion;
	
	@Column(name = "FEC_BAJA")
	private Date fecBaja;
	
	@Column(name = "IND_ACTIVO")
	private boolean indActivo;
	
	@Column(name = "IND_SISTEMA")
	private boolean indSistema;
	
}
