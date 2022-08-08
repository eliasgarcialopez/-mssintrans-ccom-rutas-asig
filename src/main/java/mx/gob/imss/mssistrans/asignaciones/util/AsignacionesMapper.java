package mx.gob.imss.mssistrans.asignaciones.util;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssistrans.asignaciones.dto.Asignacion;
import mx.gob.imss.mssistrans.asignaciones.dto.DatosVehiculo;
import mx.gob.imss.mssistrans.asignaciones.model.AsignacionesEntity;
import mx.gob.imss.mssistrans.asignaciones.model.VehiculosEntity;

@Mapper
public interface AsignacionesMapper {
	
	AsignacionesMapper INSTANCE = Mappers.getMapper(AsignacionesMapper.class);
	
	AsignacionesEntity jsonToAsignaEntity(Asignacion asignacion);
	
	Asignacion asignaEntityToJson(AsignacionesEntity asignacionEntity);
	
	DatosVehiculo datosVehToJson(VehiculosEntity vehiculosEntity);
	
}
