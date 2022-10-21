package mx.gob.imss.mssistrans.ccom.rutas.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SINTRANST_REASIGNACION_RUTAS")
public class Reasignacion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REASIGNACION", nullable = false)
    private Integer idReasignacion;
    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "ID_RUTA")
    private RutasAsigEntity ruta;
    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "ID_CHOFER")
    private ChoferesEntity chofer;
    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "ID_CHOFER_SUST")
    private ChoferesEntity choferSustituto;
    @Basic
    @Column(name = "DES_MOTIVO_REASIGNACION")
    private String motivoReasignacion;
    @Basic
    @Column(name = "DES_SINIESTRO")
    private String idSiniestro;
    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "ID_VEHICULO")
    private Vehiculos vehiculo;
    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "ID_VEHICULO_SUST")
    private Vehiculos vehiculoSustituto;
    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "ID_ASIGNACION")
    private ControlRutas controlRuta;
}
