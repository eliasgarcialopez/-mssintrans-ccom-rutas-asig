package mx.gob.imss.mssistrans.ccom.rutas.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "SINTRANST_VIATICOS")
public class Viaticos implements Serializable  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 470207867688243789L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "NUM_VIATICOS_CASETA")
    private Double viaticosCaseta;
    @Column(name = "CVE_MATRICULA", unique = false, nullable = true)
    private String cveMatricula;
    @Column(name = "CVE_MATRICULA_MODIFICA", unique = false, nullable = true)
    private String cveMatriculaModifica;
    @Column(name = "CVE_MATRICULA_BAJA", unique = false, nullable = true)
    private String cveMatriculaBaja;
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
