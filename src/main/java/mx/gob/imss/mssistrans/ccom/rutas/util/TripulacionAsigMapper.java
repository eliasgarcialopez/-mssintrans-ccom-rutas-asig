package mx.gob.imss.mssistrans.ccom.rutas.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssistrans.ccom.rutas.dto.TripulacionAsigResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigGroupEntity;

@Mapper
public interface TripulacionAsigMapper {
	TripulacionAsigMapper INSTANCE = Mappers.getMapper(TripulacionAsigMapper.class);
	List<TripulacionAsigResponse> EntityAJson ( List<TripulacionAsigGroupEntity> consultaGeneral );
}