package mx.gob.imss.mssintetrans.ccom.rutas.dto;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RutasResponse {
    private Integer idRuta;	
    private Integer idSolicitudTraslado;
    private String origen;
    private String modulo;
    private String horaRuta;
    private String tarjeta;
    private String nombreChofer;
    private String matriculaChofer;
    private String nombreCamillero1;
    private String matriculaCamillero1;
    private String nombreCamillero2;
    private String matriculaCamillero2;
    private String ecco;
    private String fechaRuta;
    
}
