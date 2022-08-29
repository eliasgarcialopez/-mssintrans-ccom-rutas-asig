package mx.gob.imss.mssintetrans.ccom.rutas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ControlRutasForaneasRequest {
	    private Integer idSolicitudTraslado;
	    private Integer idModulo;
	    private String numFolioTarjeta;
	    private Integer idTripulacion;	 
	    private Integer idVehiculo;	
	    private String fechaRuta;
	    private Integer turno;
	    private String horaRuta;
	    private String cveMatricula;
		private Double viaticosChofer;
		private Double viaticosCamillero1;
		private Double viaticosCamillero2;
	   }
