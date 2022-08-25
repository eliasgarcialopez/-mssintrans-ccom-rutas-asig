package mx.gob.imss.mssistrans.asignaciones.rutas.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssistrans.asignaciones.rutas.dto.TripulacionAsigResponse;
import mx.gob.imss.mssistrans.asignaciones.rutas.model.TripulacionAsigGroupEntity;


@Mapper
public interface TripulacionAsigMapper {
	TripulacionAsigMapper INSTANCE = Mappers.getMapper(TripulacionAsigMapper.class);
	List<TripulacionAsigResponse> EntityAJson ( List<TripulacionAsigGroupEntity> consultaGeneral );
}