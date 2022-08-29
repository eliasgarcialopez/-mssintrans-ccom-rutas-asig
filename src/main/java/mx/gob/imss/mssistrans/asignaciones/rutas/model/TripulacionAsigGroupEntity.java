package mx.gob.imss.mssistrans.asignaciones.rutas.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TripulacionAsigGroupEntity {

	private String idVehiculo;
	private String nombreChofer;
	private String matriculaChofer;
	private String nombreCamillero1;
	private String matriculaCamillero1;
	private String nombreCamillero2;
	private String matriculaCamillero2;
	private String numTarjetaDig;
}

