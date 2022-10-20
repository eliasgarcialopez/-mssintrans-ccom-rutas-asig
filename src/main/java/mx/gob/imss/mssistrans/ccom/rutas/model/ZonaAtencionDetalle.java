package mx.gob.imss.mssistrans.ccom.rutas.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "SINTRANST_ZONAS_DETALLE")
public class ZonaAtencionDetalle implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ZONAS_DETALLE")
    private Integer idZonaAtencionDetalle;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_ZONAS_ATENCION", nullable = false)
    private ZonaAtencion zona;

    @Basic
    @Column(name = "ID_OOAD")
    private Integer idOOAD;

    @Basic
    @Column(name = "ID_UNIDAD")
    private Integer idUnidad;

    @OneToOne
    @JoinColumn(name = "ID_VEHICULO", unique = false, nullable = true)
    private Vehiculos vehiculo;
    @Basic
    @Column(name = "IND_ACTIVO", unique = false, nullable = true)
    private boolean indActivo;
    @Basic
    @Column(name = "IND_SISTEMA", unique = false, nullable = true)
    private boolean indSistema;
}
