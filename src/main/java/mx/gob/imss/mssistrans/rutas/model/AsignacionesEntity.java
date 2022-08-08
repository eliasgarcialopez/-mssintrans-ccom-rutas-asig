package mx.gob.imss.mssistrans.rutas.model;

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
import mx.gob.imss.mssistrans.asignaciones.model.RutasEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SINTRANST_ASIGNACIONES")
public class AsignacionesEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ASIGNACION", unique = true, nullable = false)
    private Integer idAsignacion;
	
	@Column(name = "NUM_ASIGNACION")
	private String numAsignacion;
	
	@Column(name = "ID_VEHICULO")
	private Integer vehiculo;
	
	@Column(name = "DES_MARCA")
	private String desMarca;
	
	@Column(name = "NUM_PLACAS")
	private String numPlacas;
	
	@Column(name = "DES_MODELO")
	private String desModelo;
	
	@Column(name = "ID_CHOFER")
	private Integer chofer;
	
	@OneToOne
	@JoinColumn(name = "ID_RUTA")
	private RutasEntity ruta;
	
	@Column(name = "NUM_FOLIO_TJTA_COMBUSTIBLE")
	private String numFolioTarjeta;
	
	@Column(name = "DES_ESTATUS")
	private String desEstatus;
	
	@Column(name = "CVE_MATRICULA")
	private String matricula;
	
	@Column(name = "FEC_ALTA")
	private Date fechaAlta;
	 
	@Column(name = "FEC_ACTUALIZACION")
	private Date fechaActualizacion;

	@Column(name = "FEC_BAJA")
	private Date fechaBaja;

	@Column(name = "IND_ACTIVO")
	private Boolean indActivo;

	@Column(name = "IND_SISTEMA")
	private Boolean indSistema;

}
