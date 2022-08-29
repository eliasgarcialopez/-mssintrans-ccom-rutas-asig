package mx.gob.imss.mssistrans.ccom.rutas.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ControlRutasRequest {
	    private Integer idSolicitudTraslado;
	    private Integer idModulo;
	    private String numFolioTarjeta;
	    private Integer idTripulacion;	 
	    private Integer idVehiculo;	
	    private String fechaRuta;
	    private Integer turno;
	    private String horaRuta;
	    private String cveMatricula;
	   }
