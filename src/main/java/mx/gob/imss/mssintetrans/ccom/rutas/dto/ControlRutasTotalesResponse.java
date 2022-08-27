package mx.gob.imss.mssintetrans.ccom.rutas.dto;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import mx.gob.imss.mssintetrans.ccom.rutas.model.ModuloAmbulancia;
import mx.gob.imss.mssintetrans.ccom.rutas.model.Rutas;
import mx.gob.imss.mssintetrans.ccom.rutas.model.SolicitudTraslado;
import mx.gob.imss.mssintetrans.ccom.rutas.model.TarjetasElectronicas;
import mx.gob.imss.mssintetrans.ccom.rutas.model.Tripulacion;
import mx.gob.imss.mssintetrans.ccom.rutas.model.UnidadAdscripcion;
import mx.gob.imss.mssintetrans.ccom.rutas.model.Vehiculos;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ControlRutasTotalesResponse {
	private Integer totalVehiculosAsignados;
	private Integer totalVehiculosMantenimiento;
	private Integer totalVehiculosSiniestrados;
	private Integer totalVehiculosDisponibles;   
    
}
