package mx.gob.imss.mssistrans.ccom.rutas.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SINTRANST_RUTAS")
@Getter
@Setter
public class Rutas implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RUTA")
    private Integer idRuta;
    @Basic
    @Column(name = "NUM_FOLIO_RUTA")
    private String numFolioRuta;
    @Basic
    @Column(name = "DES_SERVICIO")
    private String desServicio;
    @Basic
    @Column(name = "ID_UNIDAD_SOLICITANTE")
    private Integer idUnidadSolcitante;
    @Basic
    @Column(name = "ID_ORIGEN")
    private Integer idOrigen;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_RUTA")
    @JsonManagedReference
    private List<RutasDestinos> destinos = new ArrayList<>();
    @Basic
    @Column(name = "DES_CODIGO_POSTAL")
    private String desCodigoPostal;
    @Basic
    @Column(name = "DES_MUNICIPIO")
    private String desMunicipio;
    @Basic
    @Column(name = "DES_ENTIDAD")
    private String desEntidad;
    @Basic
    @Column(name = "NOM_COLONIA")
    private String nomColonia;
    @Basic
    @Column(name = "DES_CALLE")
    private String desCalle;
    @Basic
    @Column(name = "DES_CIUDAD")
    private String nomCiudad;
    @Basic
    @Column(name = "NUM_EXTERIOR")
    private String numExterior;
    @Basic
    @Column(name = "NUM_INTERIOR")
    private String numInterior;
    @Basic
    @Column(name = "IND_RUTA_FORANEA")
    private boolean indRutaForanea;
    @Basic
    @Column(name = "ID_OOAD_FILTRO")
    private Integer idOOADFiltro;
    @Basic
    @Column(name = "ID_OOAD")
    private Integer idOOAD;
    @Basic
    @Column(name = "ID_UNIDAD_DESTINO")
    private Integer idUnidadDestino;
    @Basic
    @Column(name = "IND_LUNES")
    private boolean indLunes;
    @Basic
    @Column(name = "IND_MARTES")
    private boolean indMartes;
    @Basic
    @Column(name = "IND_MIERCOLES")
    private boolean indMiercoles;
    @Basic
    @Column(name = "IND_JUEVES")
    private boolean indJueves;
    @Basic
    @Column(name = "IND_VIERNES")
    private boolean indViernes;
    @Basic
    @Column(name = "IND_SABADO")
    private boolean indSabado;
    @Basic
    @Column(name = "IND_DOMINGO")
    private boolean indDomingo;
    @Basic
    @Column(name = "TIM_HORARIO_INICIAL")
    private String timHorarioInicial;
    @Basic
    @Column(name = "TIM_HORARIO_FINAL")
    private String timHorarioFinal;
    @Basic
    @Column(name = "DES_TIPO_RUTA")
    private String desTipoRuta;
    @Basic
    @Column(name = "NOM_RESPONSABLE")
    private String nomResponsable;
    @Basic
    @Column(name = "NUM_TELEFONO_RESPONSABLE")
    private String numTelefonoResponsable;
    @Basic
    @Column(name = "CVE_MATRICULA")
    private String cveMatricula;
    @Basic
    @Column(name = "FEC_ALTA")
    private LocalDate fechaAlta;
    @Basic
    @Column(name = "FEC_ACTUALIZACION")
    private LocalDate fechaActualizacion;
    @Basic
    @Column(name = "FEC_BAJA")
    private LocalDate fechaBaja;
    @Basic
    @Column(name = "IND_ACTIVO")
    private boolean activo;
    @Basic
    @Column(name = "IND_SISTEMA")
    private boolean indiceSistema;
}
