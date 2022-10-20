package mx.gob.imss.mssistrans.ccom.rutas.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssistrans.ccom.rutas.dto.Asignacion;
import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosBitacora;
import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosVehiculo;
import mx.gob.imss.mssistrans.ccom.rutas.model.AsignacionesEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.VehiculosEntity;

@Mapper
public interface AsignacionesMapper {
	
	AsignacionesMapper INSTANCE = Mappers.getMapper(AsignacionesMapper.class);
	
	AsignacionesEntity jsonToAsignaEntity(Asignacion asignacion);
	
	Asignacion asignaEntityToJson(AsignacionesEntity asignacionEntity);
	
	
	DatosVehiculo datosVehToJson(VehiculosEntity vehiculosEntity);
	
	// validar
	DatosBitacora datosBitToJson(VehiculosEntity vehiculosEntity);
	
}
