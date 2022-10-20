package mx.gob.imss.mssistrans.ccom.rutas.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import mx.gob.imss.mssistrans.ccom.rutas.model.ModuloAmbulancia;
import mx.gob.imss.mssistrans.ccom.rutas.model.Rutas;
import mx.gob.imss.mssistrans.ccom.rutas.model.SolicitudTraslado;
import mx.gob.imss.mssistrans.ccom.rutas.model.UnidadAdscripcion;
import mx.gob.imss.mssistrans.ccom.rutas.model.Vehiculos;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ControlRutasResponse extends ControlRutasForaneasResponse {
	private Integer totalVehiculosAsignados;
	private Integer totalVehiculosMantenimiento;
	private Integer totalVehiculosSiniestrados;
	private Integer totalVehiculosDisponibles;
	private Rutas ruta;
	private SolicitudTraslado idSolicitudTraslado;
    private String turno;
    private UnidadAdscripcion origen;
    private UnidadAdscripcion destino;
    private ModuloAmbulancia modulo;
    private String horaRuta;
    private String fechaRuta;   
    private String numFolioTarjeta;
    private TripulacionResponse tripulacion;
    private Vehiculos vehiculo;
   
    
}
