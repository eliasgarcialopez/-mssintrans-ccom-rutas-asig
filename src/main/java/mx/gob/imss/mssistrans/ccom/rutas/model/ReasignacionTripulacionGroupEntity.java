package mx.gob.imss.mssistrans.ccom.rutas.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReasignacionTripulacionGroupEntity {

	private String idControlRuta;
	private String nombreChofer;
	private String matriculaChofer;
	private String nombreCamillero1;
	private String matriculaCamillero1;
	private String nombreCamillero2;
	private String matriculaCamillero2;
	private String numTarjetaDig;
	private String idPersonalAmbulancia;
	private String desMotivoReasig;
}

