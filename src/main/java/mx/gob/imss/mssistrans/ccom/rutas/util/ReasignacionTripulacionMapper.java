package mx.gob.imss.mssistrans.ccom.rutas.util;

import mx.gob.imss.mssistrans.ccom.rutas.dto.ReasignacionTripulacionResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.ReasignacionTripulacionGroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ReasignacionTripulacionMapper {
    ReasignacionTripulacionMapper INSTANCE = Mappers.getMapper(ReasignacionTripulacionMapper.class);

    List<ReasignacionTripulacionResponse> entityAJson(List<ReasignacionTripulacionGroupEntity> consultaGeneral);
}