package mx.gob.imss.mssistrans.reasignacion.rutas.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssistrans.reasignacion.rutas.dto.DatosVehiculo;
import mx.gob.imss.mssistrans.reasignacion.rutas.dto.ReasignacionRutaRequestDTO;
import mx.gob.imss.mssistrans.reasignacion.rutas.dto.ReasignacionRutasTablaResponse;
import mx.gob.imss.mssistrans.reasignacion.rutas.model.ReasignacionRutasEntity;
import mx.gob.imss.mssistrans.reasignacion.rutas.model.VehiculosEntity;

@Mapper
public interface ReasignacionRutasMapper {

	ReasignacionRutasMapper INSTANCE = Mappers.getMapper(ReasignacionRutasMapper.class);
	
	ReasignacionRutasEntity jsonAEntity(ReasignacionRutaRequestDTO request);
	DatosVehiculo datosVehToJson(VehiculosEntity vehiculosEntity);
	List<ReasignacionRutasTablaResponse> formatearListaResponse(List<ReasignacionRutasEntity> consulta);
	
}
