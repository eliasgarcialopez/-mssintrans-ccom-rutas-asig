package mx.gob.imss.mssistrans.ccom.rutas.dto;


import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "AsignacionRutas")
public class AsigRutasResponse {
	private String idSolicitud;
	private String idControlRuta;
	private String cveEcco;
	private String desEstatusSolicitud;
	private String idRutaAsignacion;
	private String idVehiculo;
	private String idRuta;
	private String idReasignacion;
}
