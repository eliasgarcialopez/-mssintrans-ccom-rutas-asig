package mx.gob.imss.mssistrans.ccom.rutas.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ControlRutasTotalesResponse {
	private Integer totalVehiculosAsignados;
	private Integer totalVehiculosMantenimiento;
	private Integer totalVehiculosSiniestrados;
	private Integer totalVehiculosDisponibles;   
    
}
