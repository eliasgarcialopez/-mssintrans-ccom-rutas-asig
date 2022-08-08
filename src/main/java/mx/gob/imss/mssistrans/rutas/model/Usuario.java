package mx.gob.imss.mssistrans.rutas.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name="SINTRANSC_USUARIOS")
public class Usuario implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_USUARIO")
	private Long idUsuario;
	
	@Column(name = "CVE_MATRICULA",nullable = false)
	private String cveMatricula; 	
	
	@Column(name = "DES_ESTATUS_USUARIO")
	private String desEstatusUsuario;
	
	@Column(name = "DES_MOTIVO")
	private String desMotivo;
	
	@OneToOne
	@JoinColumn(name = "ID_ROLES_USUARIO")
	private Role role;	
	
	@OneToOne
	@JoinColumn(name = "ID_UNIDAD_ADSCRIPCION")
	private UnidadAdscripcion idUnidadAd;
	
	@Column(name = "NOM_USUARIO")
	private String nomUsuario;
	
	@Column(name = "NOM_APELLIDO_PATERNO")
	private String apellidoPaterno;
	
	@Column(name = "NOM_APELLIDO_MATERNO")
	private String apellidoMaterno;
	
	@Column(name = "CVE_PASSWORD")
	private String cvePassword;
	
	@Column(name = "FEC_PWD_CADUCA")
	private LocalDate fecPwdCaduca;
	

	@Column(name = "CVE_MATRIC_AUDIT")
	private String cveMatricAudit;
	
	@Column(name = "FEC_ALTA")
	private LocalDate fecAlta;
	
	@Column(name = "FEC_ACTUALIZACION")
	private LocalDate fecAct;
	
	@Column(name = "FEC_BAJA")
	private LocalDate fecBaja;
	
	@Column(name = "IND_ACTIVO")
	private Integer indActivo;
	
	@Column(name = "IND_SISTEMA")
	private Integer indSistema;

	
	@Column(name = "NUM_INTENTOS")
	private Integer numIntentos;
	

}
