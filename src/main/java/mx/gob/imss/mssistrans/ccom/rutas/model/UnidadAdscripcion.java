package mx.gob.imss.mssistrans.ccom.rutas.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SINTRANSC_UNIDADES_ADSCRIPCION")
@Getter
@Setter
public class UnidadAdscripcion extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_UNIDAD_ADSCRIPCION")
    private Integer idUnidadAdscripcion;


    @Basic
    @Column(name = "NOM_UNIDAD_ADSCRIPCION")
    private String nomUnidadAdscripcion;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_OOAD", nullable = false)
    private Ooad ooad;


    @Basic
    @Column(name = "DES_TIPO_UNIDAD")
    private String desTipoUnidad;

    @Basic
    @Column(name = "IND_UNIDAD_PERNOCTA")
    private boolean pernocta;

    @Basic
    @Column(name = "NUM_UN_INF")
    private String numUnInf;

    @Basic
    @Column(name = "NUM_UN_OPE")
    private String numUnOpe;

    @Basic
    @Column(name = "NUM_CC")
    private String numCC;


    @Basic
    @Column(name = "NUM_CU")
    private String numCU;

    @Basic
    @Column(name = "NUM_DIV")
    private String numDiv;

    @Basic
    @Column(name = "NUM_SDIV")
    private String numSDIV;


    @JsonBackReference
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_CODIGO_POSTAL", nullable = false)
    private CodigoPostal codigoPostal;

    @Basic
    @Column(name = "NOM_COLONIA")
    private String nomColonia;


    @Basic
    @Column(name = "DES_CALLE_NUM")
    private String desCalleNUM;

}
