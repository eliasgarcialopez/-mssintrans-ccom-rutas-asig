package mx.gob.imss.mssintetrans.ccom.rutas.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "sintranst_viaticos")
public class Viaticos {
    @Id
    @GeneratedValue
    @Column(name = "ID_VIATICOS")
    private Integer idViaticos;
    @OneToOne
    @JoinColumn( name = "ID_CONTROL_RUTA")
    private ControlRutas controlRutas;
    @Column(name = "VIATICOS_CHOFER")
    private Double viaticosChofer;
    @Column(name = "VIATICOS_CAMILLERO_1")
    private Double viaticosCamillero1;
    @Column(name = "VIATICOS_CAMILLERO_2")
    private Double viaticosCamillero2;
    @Column(name = "CVE_MATRICULA", unique = false, nullable = true)
    private String cveMatricula;
    @Column(name = "FEC_ALTA", unique = false, nullable = true)
    private LocalDate fecAlta;
    @Column(name = "FEC_ACTUALIZACION", unique = false, nullable = true)
    private LocalDate fecActualizacion;
    @Column(name = "FEC_BAJA", unique = false, nullable = true)
    private LocalDate fecBaja;
    @Column(name = "IND_ACTIVO", unique = false, nullable = true)
    private boolean indActivo;
    @Column(name = "IND_SISTEMA", unique = false, nullable = true)
    private boolean indSistema;
}
