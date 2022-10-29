package mx.gob.imss.mssistrans.ccom.rutas.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SINTRANST_ZONAS_ATENCION")
public class ZonaAtencion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ZONAS_ATENCION", unique = false, nullable = true)
    private Integer idZona;

    @Column(name = "ID_MODULO_AMBULANCIA", unique = false, nullable = true)
    private Integer idModulo;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_ZONAS_ATENCION")
    @JsonManagedReference
    private List<ZonaAtencionDetalle> detalle = new ArrayList<>();

    @Column(name = "CVE_MATRICULA")
    private String cveMatricula;

    @Column(name = "FEC_ALTA", unique = false, nullable = true)
    private LocalDate fecAlta;

    @Column(name = "FEC_ACTUALIZACION", unique = false, nullable = true)
    private LocalDate fecActualizacion;

    @Column(name = "FEC_BAJA", unique = false, nullable = true)
    private LocalDate fecBaja;

    @Column(name = "IND_ACTIVO", unique = false, nullable = true)
    private boolean activo;

    @Column(name = "IND_SISTEMA", unique = false, nullable = true)
    private boolean indSistema;
}
