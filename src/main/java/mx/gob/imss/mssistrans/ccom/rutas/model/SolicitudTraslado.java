package mx.gob.imss.mssistrans.ccom.rutas.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;


@Entity 
@Table(name = "SINTRANST_SOLICITUD_TRASLADO")
@Getter
@Setter
public class SolicitudTraslado implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SOLICITUD")
    private Integer idSolicitud;

    @Basic
    @Column(name = "FEC_SOLICITUD")
    private LocalDate fecSolicitud;
    
    @Basic
    @Column(name = "DES_NOM_PACIENTE")
    private String desNomPaciente;
    
    @Basic
    @Column(name = "TIM_SOLICITUD")
    private String timSolicitud;
   
	 
    @Basic
    @Column(name = "ID_UNIDAD_SOLICITANTE")
    private Integer idUnidadSolicitante;
    
    
    @Basic
    @Column(name = "ID_UNIDAD_ADSCRIPCION")
    private Integer idUnidadAdscripcion;
    
    @Basic
    @Column(name = "DES_NSS_PACIENTE")
    private String desNssPaciente;
    
    @Basic
    @Column(name = "DES_DIAGNOSTICO")
    private String desDiagnostico;
    
    
    @Basic
    @Column(name = "IND_OXIGENO")
    private boolean indOxigeno;
    

    @Basic
    @Column(name = "IND_INCUBADORA")
    private boolean indIncubadora;
    
    
    @Basic
    @Column(name = "IND_CAPSULA")
    private boolean indCapsula;
    
    
    @Basic
    @Column(name = "DES_TIPO_SERVICIO")
    private Integer numTipoServicio;
    
    @Basic
    @Column(name = "DES_POSTURA_PACIENTE")
    private Integer numPosturaPaciente;
    
    
    @Basic
    @Column(name = "FEC_TRANSMISION")
    private LocalDate fecTransmision;
    
    @Basic
    @Column(name = "TIM_TRANSMISION")
    private LocalTime timTasnmision;
    
    @Basic
    @Column(name = "CVE_ORIGEN")
    private Integer cveOrigen;
    
    @Basic
    @Column(name = "DES_AREA_ORIGEN")
    private Integer desAreaOrigen;
    
    @Basic
    @Column(name = "NUM_CAMA_ORIGEN")
    private Integer numCamaOrigen;
    
    @Basic
    @Column(name = "CVE_DESTINO")
    private Integer cveDestino;
    
    @Basic
    @Column(name = "DES_AREA_DESTINO")
    private Integer desAreaDestino;
    
    
    @Basic
    @Column(name = "NUM_CAMA_DESTINO")
    private Integer numCamaDestino;
    
    
    
    
    @Basic
    @Column(name = "ID_CODIGO_POSTAL")
    private Integer idCodigoPostal;
    
    @Basic
    @Column(name = "ID_MUNICIPIO")
    private Integer idMunicipio;
        
    @Basic
    @Column(name = "DES_COLONIA")
    private String desColonia;
    
    @Basic
    @Column(name = "DES_CALLE")
    private String desCalle;
    
    
    @Basic
    @Column(name = "NUM_EXTERIOR")
    private String numExterior;
    
    
    @Basic
    @Column(name = "NUM_INTERIOR")
    private String numInterior;
    
    @Basic
    @Column(name = "DES_REFERENCIA")
    private String desReferenia;
    
    
    
    @Basic
    @Column(name = "NUM_TEL_DESTINO")
    private String numTelDestino;
    
    
    
    @Basic
    @Column(name = "NOM_FAMILIAR")
    private String nomFamiliar;
    
    
    
    @Basic
    @Column(name = "TEL_FAMILIAR")
    private String telFamiliar;
    
    
    @Basic
    @Column(name = "CVE_MATRICULA_DOCTOR_RECIBE")
    private String cveMatriculaRecibe;
    
//    @Basic
//    @Column(name = "DES_NOMBRE_DOCTOR_RECIBE")
//    private String desNomDoctorRecibe;
    
    
    @Basic
    @Column(name = "NUM_FOLIO_ACEPTACION")
    private Integer nomFolioAceptacion;
    
    
    @Basic
    @Column(name = "CVE_MATRICULA_DOCTOR_AUTORIZA")
    private String numMatriculaAutoriza;
    
//    
//    @Basic
//    @Column(name = "DES_NOMBRE_DOCTOR_AUTORIZA")
//    private String desNomDoctorAutoriza;
//    
    
    @Basic
    @Column(name = "DES_ESTATUS_SOLICITUD")
    private String desEstatusSolicitud;
    
    @Basic
    @Column(name = "DES_MOTIVO_CANCELACION")
    private String desMotivoCancelacion;
    
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
