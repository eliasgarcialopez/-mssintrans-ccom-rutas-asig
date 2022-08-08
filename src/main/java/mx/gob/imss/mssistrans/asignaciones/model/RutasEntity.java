package mx.gob.imss.mssistrans.asignaciones.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalTime;
import java.util.Date;

/**
 * @author gsz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SINTRANST_RUTAS")
public class RutasEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RUTA")
    private Integer idRuta;
	
	@Column(name = "NUM_FOLIO_RUTA")
	private String numRuta;
	 
    @Basic
    @Column(name = "DES_SERVICIO")
    private String desServicio;
    
	@Column(name = "ID_UNIDAD_SOLICITANTE", unique = false, nullable = true)
	private Integer idUnidadSolicitante;
    
    @Basic
    @Column(name = "ID_ORIGEN")
    private Integer idOrigen;

    @Basic
    @Column(name = "ID_CODIGO_POSTAL")
    private Integer idCodigoPostal;
	 
    @Basic
    @Column(name = "NOM_COLONIA")
    private String nomColonia;
    
    @Basic
    @Column(name = "DES_CIUDAD")
    private String desCiudad;
    
    @Basic
    @Column(name = "DES_CALLE")
    private String desCalle;
    
    @Basic
    @Column(name = "NUM_EXTERIOR")
    private String numExterior;
    
    @Basic
    @Column(name = "NUM_INTERIOR")
    private String numInterior;
    
    @Basic
    @Column(name = "IND_RUTA_FORANEA")
    private Boolean indRutaForane;
    
    @Basic
    @Column(name = "ID_OOAD")
    private Integer idOoad;
    
	@Column(name = "ID_UNIDAD_DESTINO", unique = false, nullable = true)
	private Integer idUnidadDestino;
    
    @Basic
    @Column(name = "IND_LUNES")
    private Boolean indLunes;
    
    @Basic
    @Column(name = "IND_MARTES")
    private Boolean indMartes;
    
    @Basic
    @Column(name = "IND_MIERCOLES")
    private Boolean indMiercoles;
    
    @Basic
    @Column(name = "IND_JUEVES")
    private Boolean indJueves;
    
    @Basic
    @Column(name = "IND_VIERNES")
    private Boolean indViernes;
    
    @Basic
    @Column(name = "IND_SABADO")
    private Boolean indSabado;
    
    @Basic
    @Column(name = "IND_DOMINGO")
    private Boolean indDomingo;
    
    @Basic
    @Column(name = "TIM_HORARIO_INICIAL")
    private LocalTime timHorarioInicial;
    
    @Basic
    @Column(name = "TIM_HORARIO_FINAL")
    private LocalTime timHorarioFinal;
    
    @Basic
    @Column(name = "DES_TIPO_RUTA")
    private String desTipoRuta;
    
    @Basic
    @Column(name = "NOM_RESPONSABLE")
    private String nomResponsable;
    
    @Basic
    @Column(name = "NUM_TELEFONO_RESPONSABLE")
    private String numTelefonoResponsable;
    
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
