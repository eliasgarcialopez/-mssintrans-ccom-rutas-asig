package mx.gob.imss.mssistrans.ccom.rutas.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import mx.gob.imss.mssistrans.ccom.rutas.model.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ControlRutasForaneasResponse {
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
    private String viaticosCasetas;
    private String viaticosChofer;
    private String viaticosCamillero1;
    private String viaticosCamillero2;


}
