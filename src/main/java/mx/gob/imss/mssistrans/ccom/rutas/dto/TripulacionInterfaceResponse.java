package mx.gob.imss.mssistrans.ccom.rutas.dto;

/**
 * @author Itzi B. Enriquez R.
 * @puesto DEV
 * @date 30 ago. 2022
 */
public interface  TripulacionInterfaceResponse {
	Integer getidTripulacion();
	Integer getidVehiculo();
	String getnombreChofer();
	String getcveMatriculaChofer();
	String getnombreCamillero1();
	String getcveMatriculaCamillero1();
	String getnombreCamillero2();
	String getcveMatriculaCamillero2();
}
