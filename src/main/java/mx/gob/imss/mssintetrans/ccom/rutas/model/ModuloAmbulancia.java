package mx.gob.imss.mssintetrans.ccom.rutas.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity 
@Table(name = "SINTRANST_MODULO_AMBULANCIA")
@Getter
@Setter
public class ModuloAmbulancia implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MODULO_AMBULANCIA")
    private Integer idModulo;

    @Basic
    @Column(name = "DES_NOMBRE")
    private String desNombre;
    
    @Basic
    @Column(name = "DES_TIPO_MODULO")
    private String desTipoModulo;
    
	
	@JsonBackReference
	@Basic
	 @OneToOne(cascade = CascadeType.ALL)
		@JoinColumn(name = "ID_MODULO_AMBULANCIA", unique = false, nullable = true)
	private ZonaAtencion zona;
  
	
    @Basic
    @Column(name = "CVE_PRESUPUESTAL")
    private String cvePresupuestal;
    @Basic
    @Column(name = "NUM_AMBULANCIAS_ASIGNADAS")
    private Integer numAmbulanciasAsignadas;
    @Basic
    @Column(name = "CVE_MATRICULA_JEFE")
    private String cveMatriculaJefe;
    @Basic
    @Column(name = "NUM_TELEFONO_MODULO")
    private Integer numTelefono;
    @Basic
    @Column(name = "DES_PLANTA_LAVADO")
    private String desPlantaLavado;
    @Basic
    @Column(name = "ID_OOAD")
    private Integer idOOAD;
    @Basic
    @Column(name = "NUM_TELEFONO_OOAD")
    private Integer numTelefonoOOAD; 

    @Basic
    @Column(name = "DES_REFERENCIA_OOAD")
    private String desReferenciaOOAD;
    

    
    @Basic
    @Column(name = "CVE_MATRICULA")
    private String cveMatricula;
        
	
    @Basic
    @Column(name = "FEC_ALTA")
    private LocalDate fechaAlta;
    @Basic
    @Column(name = "FEC_ACTUALIZACION")
    private LocalDate fechaActualizacion;
    @Basic
    @Column(name = "FEC_BAJA")
    private LocalDate fechaBaja;
    @Basic
    @Column(name = "IND_ACTIVO")
    private boolean activo;
    @Basic
    @Column(name = "IND_SISTEMA")
    private boolean indiceSistema;
}
