package mx.gob.imss.mssistrans.ccom.rutas.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "SINTRANST_PERSONAL_AMBULANCIA")
@Getter
@Setter
public class PersonalAmbulancia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERSONAL_AMBULANCIA", unique = false, nullable = true)
    private Long idPersonalAmbulancia;

    @OneToOne
    @JoinColumn(name = "ID_CHOFER")
    private ChoferesEntity chofer;

    @OneToOne
    @JoinColumn(name = "ID_CAMILLERO")
    private CamillerosEntity camillero;

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
    @Column(name = "ID_INCIDENCIA_PERS_AMB")
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
