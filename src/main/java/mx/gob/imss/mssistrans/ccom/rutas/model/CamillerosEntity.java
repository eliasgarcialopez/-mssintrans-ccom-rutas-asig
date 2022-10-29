package mx.gob.imss.mssistrans.ccom.rutas.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Itzi B. Enriquez R.
 * @puesto DEV
 * @date 16 ago. 2022
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SINTRANST_CAMILLERO")
public class CamillerosEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CAMILLERO", unique = false, nullable = true)
    private Long idCamillero;

    @Column(name = "NOM_CAMILLERO", unique = false, nullable = true)
    private String nomCamillero;

    @Basic
    @Column(name = "CVE_MATRICULA", unique = false, nullable = true)
    private String cveMatricula;

    @Basic
    @Column(name = "CVE_MATRICULA_AUDITORIA", unique = false, nullable = true)
    private String cveMatriculaAlt;

    @Column(name = "FEC_ALTA", unique = false, nullable = true)
    private LocalDate fechaAlta;

    @Column(name = "FEC_ACTUALIZACION", unique = false, nullable = true)
    private LocalDate fechaActualizacion;

    @Column(name = "FEC_BAJA", unique = false, nullable = true)
    private LocalDate fechaBaja;

    @Basic
    @Column(name = "IND_ACTIVO", unique = false, nullable = true)
    private boolean intActivo;

    @Basic
    @Column(name = "IND_SISTEMA", unique = false, nullable = true)
    private boolean indSistema;

}
