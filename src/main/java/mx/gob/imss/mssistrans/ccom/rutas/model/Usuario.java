package mx.gob.imss.mssistrans.ccom.rutas.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private Integer idUsuario;

	@Column(name = "CVE_MATRICULA")
	private String cveMatricula;

	@Column(name = "DES_ESTATUS_USUARIO")
	private String estatusUsuario;

	@Column(name = "DES_MOTIVO")
	private String motivo;

	@Column(name = "ID_ROLES_USUARIO")
	private Integer idRolUsuario;
	@Column(name = "ID_UNIDAD_ADSCRIPCION")
	private Integer idUnidad;
	@Column(name = "NOM_USUARIO")
	private String nombreUsuario;
	@Column(name = "NOM_APELLIDO_PATERNO")
	private String apellidoPaterno;
	@Column(name = "NOM_APELLIDO_MATERNO")
	private String apellidoMaterno;
	@Column(name = "CVE_PASSWORD")
	private String password;
	@Column(name = "FEC_PWD_CADUCA")
	private Date fechaCaducidadPassword;
	@Column(name = "CVE_MATRIC_AUDIT")
	private String matriculaAuditoria;
	@Column(name = "IND_ACTIVO")
	private Integer indActivo;
	@Column(name = "IND_SISTEMA")
	private Integer indSistema;
	@Column(name = "NUM_INTENTOS")
	private Integer intentos;
	@Column(name = "FEC_ALTA")
	private Date fecAlta;
}
