package mx.gob.imss.mssistrans.rutas.dto;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import mx.gob.imss.mssistrans.rutas.model.OOAD;
import mx.gob.imss.mssistrans.rutas.model.UnidadAdscripcion;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RutasResponse {
   
    private String desServicio;
    @JsonProperty
    private UnidadAdscripcion unidadSolicitante;
  
    private UnidadAdscripcion unidadOrigen;
    
    private List<UnidadAdscripcion> destinos=new ArrayList<>();
    
    private String desCodigoPostal;
    private String desEntidad;
    private String desMunicipio;
    private String nomCiudad;
    private String nomColonia;
    private String desCalle;
    private String numExterior;
    private String numInterior;
    private boolean indRutaForane;
    private OOAD idOOAD;
    private UnidadAdscripcion unidadDestino;
    private boolean indLunes;
    private boolean indMartes;
    private boolean indMiercoles;
    private boolean indJueves;
    private boolean indViernes;
    private boolean indSabado;
    private boolean indDomingo;
    private String timHorarioInicial;
    private String timHorarioFinal;
    private String desTipoRuta;
    private String nomResponsable;
    private String numTelefonoResponsable;
    private String cveMatricula;
}
