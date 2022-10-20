package mx.gob.imss.mssistrans.ccom.rutas.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @author Itzi B. Enriquez R.
 * @puesto DEV
 * @date 10 ago. 2022
 */
@Getter
@Setter
@Entity
@Table(name = "SINTRANST_CHOFERES")
public class ChoferesEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CHOFER", unique = false, nullable = true)
    private Integer idChofer;

    @Column(name = "CVE_MATRICULA_CHOFER", unique = false, nullable = true)
    private String matriculaChofer;

    @Column(name = "NOM_CHOFER", unique = false, nullable = true)
    private String nombreChofer;

    @Column(name = "CVE_UNIDAD_ADSCRIPCION", unique = false, nullable = true)
    private String unidadAdscripcion;

    @Column(name = "CVE_UNIDAD_OOAD", unique = false, nullable = true)
    private String unidadOoad;

    @Column(name = "FEC_INI_CONTRATO", unique = false, nullable = true)
    private Date fecInicioContrato;

    @Column(name = "FEC_FIN_CONTRATO", unique = false, nullable = true)
    private Date fecFinContrato;

    @Column(name = "FEC_INI_INCAPACIDAD", unique = false, nullable = true)
    private Date fecIniIncapacidad;

    @Column(name = "FEC_FIN_INCAPACIDAD", unique = false, nullable = true)
    private Date fecFinIncapacidad;

    @Column(name = "ID_UNIDAD_ADSCRIPCION", unique = false, nullable = true)
    private Integer idUnidadAdscripcion;

    @Column(name = "DES_CATEGORIA", unique = false, nullable = true)
    private String categoria;

    @Column(name = "ESTATUS_CHOFER", unique = false, nullable = true)
    private String estatusChofer;

    @Column(name = "DES_MOTIVO", unique = false, nullable = true)
    private String motivo;

    @Column(name = "CVE_LICENCIA", unique = false, nullable = true)
    private String licencia;

    @Column(name = "CVE_TIPO_LICENCIA", unique = false, nullable = true)
    private String tipoLicencia;

    @Column(name = "FEC_VIGENCIA", unique = false, nullable = true)
    private Date fecVigencia;

    @Column(name = "FEC_EXPEDICION", unique = false, nullable = true)
    private Date fecExpedicion;

    @Column(name = "DES_RUTA_LICENCIA", unique = false, nullable = true)
    private String desrutaLicencia;

    @Column(name = "CVE_MATRICULA", unique = false, nullable = true)
    private String matricula;

    @Column(name = "FEC_ALTA", unique = false, nullable = true)
    private Date fecAlta;

    @Column(name = "FEC_ACTUALIZACION", unique = false, nullable = true)
    private Date fecActualizacion;

    @Column(name = "FEC_BAJA", unique = false, nullable = true)
    private Date fecBaja;

    @Column(name = "IND_ACTIVO", unique = false, nullable = true)
    private boolean intActivo;

    @Column(name = "IND_SISTEMA", unique = false, nullable = true)
    private boolean indSistema;

}
