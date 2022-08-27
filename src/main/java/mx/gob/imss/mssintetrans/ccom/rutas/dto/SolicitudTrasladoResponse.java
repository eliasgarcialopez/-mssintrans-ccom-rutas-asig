package mx.gob.imss.mssintetrans.ccom.rutas.dto;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import mx.gob.imss.mssintetrans.ccom.rutas.model.UnidadAdscripcion;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SolicitudTrasladoResponse {

private Integer idSolicitud;

private String fecSolicitud;

private String timSolicitud;

 
private Integer idUnidadSolicitante;

private Integer idUnidadAdscripcion;

private String desNssPaciente;

private String desNomPaciente;

private String desDiagnostico;

private boolean indOxigeno;
private boolean indIncubadora;
private boolean indCapsula;
private Integer numTipoServicio;
private Integer numPosturaPaciente;
private String fecTransmision;
private String timTasnmision;
private UnidadAdscripcion cveOrigen;
private Integer cveAreaOrigen;
private Integer numCamaOrigen;
private UnidadAdscripcion cveDestino;
private Integer cveAreaDestino;
private Integer numCamaDestino;
private Integer idCodigoPostal;
private Integer idMunicipio;
private String desColonia;
private String desCalle;
private String numExterior;
private String numInterior;
private String desReferenia;
private Integer numTelDestino;
private String nomFamiliar;
private Integer telFamiliar;
private String cveMatriculaRecibe;

//@Basic
//@Column(name = "DES_NOMBRE_DOCTOR_RECIBE")
//private String desNomDoctorRecibe;
private Integer nomFolioAceptacion;
private String numMatriculaAutoriza;
//
//@Basic
//@Column(name = "DES_NOMBRE_DOCTOR_AUTORIZA")
//private String desNomDoctorAutoriza;
//
private String desEstatusSolicitud;
private String desMotivoCancelacion;
private String cveMatricula;    
}
