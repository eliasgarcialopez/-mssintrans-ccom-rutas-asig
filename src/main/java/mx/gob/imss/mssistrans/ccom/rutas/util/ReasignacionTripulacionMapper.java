package mx.gob.imss.mssistrans.ccom.rutas.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssistrans.ccom.rutas.dto.ReasignacionTripulacionResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.TripulacionAsigResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.ReasignacionTripulacionGroupEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigGroupEntity;

@Mapper
public interface ReasignacionTripulacionMapper {
	ReasignacionTripulacionMapper INSTANCE = Mappers.getMapper(ReasignacionTripulacionMapper.class);
	List<ReasignacionTripulacionResponse> EntityAJson ( List<ReasignacionTripulacionGroupEntity> consultaGeneral );
}