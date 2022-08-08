package mx.gob.imss.mssistrans.rutas.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RutasRequest {
	
	    private String desServicio;
	    private Integer idUnidadSolcitante;
	    private Integer idOrigen;
	    private List<RutasDestinoRequest> destinos=new ArrayList<>();
	   
	    private String desCodigoPostal;	    
	    private String desMunicipio;	    
	    private String desEntidad;
	    
	    private String nomCiudad;
	    private String nomColonia;
	    private String desCalle;
	    private String numExterior;
	    private String numInterior;
	    private boolean indRutaForane;
	    private Integer idOOAD;
	    private Integer idUnidadDestino;
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
