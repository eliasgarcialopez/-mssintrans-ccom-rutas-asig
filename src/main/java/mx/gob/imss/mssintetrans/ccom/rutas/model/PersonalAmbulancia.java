package mx.gob.imss.mssintetrans.ccom.rutas.model;

import java.time.LocalDate;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SINTRANST_PERSONAL_AMBULANCIA")
@Getter
@Setter
public class PersonalAmbulancia {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERSONAL_AMBULANCIA", unique = false, nullable = true)
    private Long idPersonalAmbulancia;

    @Basic
    @Column(name = "CVE_MATRICULA_PERSONAL", unique = false, nullable = true)
    private String cveMatriculaPersonal;

    @Basic
    @Column(name = "DES_NOMBRE", unique = false, nullable = true)
    private String desNombre;

    @Basic
    @Column(name = "DES_PUESTO", unique = false, nullable = true)
    private String desPuesto;

    @Basic
    @Column(name = "DES_TURNO", unique = false, nullable = true)
    private String desTurno;

    @Basic
    @Column(name = "DES_ESTATUS", unique = false, nullable = true)
    private String desEstatus;

    @Basic
    @Column(name="ID_INCIDENCIA_PERS_AMB")
    private Integer idIncidenciaPersAmb;
    
    @Basic
    @Column(name = "CVE_MATRICULA_AUDITORIA", unique = false, nullable = true)
    private String cveMatriculaAlt;
    
    @Basic
    @Column(name = "FEC_ALTA", unique = false, nullable = true)
    private LocalDate fechaAlta;
    
    @Basic
    @Column(name = "CVE_MATRICULA_AUDITORIA_ACTUALIZACION", unique = false, nullable = true)
    private String cveMatriculaAct;
    
    @Basic
    @Column(name = "FEC_ACTUALIZACION", unique = false, nullable = true)
    private LocalDate fechaActualizacion;
    
    @Basic
    @Column(name = "FEC_BAJA", unique = false, nullable = true)
    private LocalDate fechaBaja;
    
    @Basic
    @Column(name = "IND_ACTIVO", unique = false, nullable = true)
    private boolean intActivo;
    
    @Basic
    @Column(name = "IND_SISTEMA", unique = false, nullable = true)
    private boolean indSistema;
	
}
