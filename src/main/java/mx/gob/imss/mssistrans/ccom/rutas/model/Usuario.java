package mx.gob.imss.mssistrans.ccom.rutas.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
 
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SINTRANSC_USUARIOS")
@Getter
@Setter
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_USUARIO", unique = true, nullable = true)
	private Integer ID_USUARIO;

	@Column(name = "CVE_MATRICULA")
	private String CVE_MATRICULA;

	@Column(name = "DES_ESTATUS_USUARIO")
	private String DES_ESTATUS_USUARIO;

	@Column(name = "DES_MOTIVO")
	private String DES_MOTIVO;

	@Column(name = "ID_ROLES_USUARIO")
	private Integer ID_ROLES_USUARIO;

	@Column(name = "ID_UNIDAD_ADSCRIPCION")
	private Integer ID_UNIDAD_ADSCRIPCION;

	@Column(name = "NOM_USUARIO")
	private String NOM_USUARIO;

	@Column(name = "NOM_APELLIDO_PATERNO")
	@JoinColumn(name = "NOM_APELLIDO_PATERNO")
	private String NOM_APELLIDO_PATERNO;

	@Column(name = "NOM_APELLIDO_MATERNO")
	private String NOM_APELLIDO_MATERNO;

	@Column(name = "CVE_PASSWORD")
	private String CVE_PASSWORD;

	@Column(name = "FEC_PWD_CADUCA")
	private Date FEC_PWD_CADUCA;

	@Column(name = "CVE_MATRIC_AUDIT")
	private String CVE_MATRIC_AUDIT;

	@Column(name = "IND_ACTIVO")
	private Integer IND_ACTIVO;

	@Column(name = "IND_SISTEMA")
	private Integer IND_SISTEMA;

	@Column(name = "NUM_INTENTOS")
	private Integer NUM_INTENTOS;

	@Column(name = "FEC_ALTA")
	private Date fecAlta;
	
	

	
	
	

}
