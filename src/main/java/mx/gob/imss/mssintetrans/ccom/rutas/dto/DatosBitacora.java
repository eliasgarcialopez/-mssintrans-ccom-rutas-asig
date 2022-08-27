package mx.gob.imss.mssintetrans.ccom.rutas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "datosBitacora")
public class DatosBitacora {
	
	@JsonProperty
	private Integer idVehiculo;
	
	@JsonProperty
	private String cveEcco;
	
	@JsonProperty
	private String desMarca;
	
	@JsonProperty
	private String desTipoVeh;
	
	@JsonProperty
	private String numPlacas;
	
	@JsonProperty
	private Integer desModelo;
	
	@JsonProperty
	private String nomModuloAmb; 
    
    @JsonProperty
	private Integer idTripulacion;
	
	@JsonProperty
	private String cveMatriculaChofer;
	
	@JsonProperty
	private String cveMatriculaCamillero1;
	
    @JsonProperty
    private String cveMatriculaCamillero2;
    
    @JsonProperty
	private String desTipoServicio;
    
    @JsonProperty
	private String numRuta;
	
	@JsonProperty
	private Integer idControlRuta;
	
	// Si ya existe la bitacora
	@JsonProperty
	private String numBitacora;
	
	@JsonProperty
	private String fecBitacora;
	
	@JsonProperty
	private Integer idOoad;
	
}
