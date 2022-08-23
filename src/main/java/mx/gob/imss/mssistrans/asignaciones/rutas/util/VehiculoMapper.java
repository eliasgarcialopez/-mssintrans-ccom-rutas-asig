package mx.gob.imss.mssistrans.asignaciones.rutas.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssistrans.asignaciones.rutas.dto.VehiculoResponse;
import mx.gob.imss.mssistrans.asignaciones.rutas.model.VehiculoEntity;


@Mapper
public interface VehiculoMapper {
	VehiculoMapper INSTANCE = Mappers.getMapper(VehiculoMapper.class);
	List<VehiculoResponse> EntityAJson ( List<VehiculoEntity> consultaGeneral );
}