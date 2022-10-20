package mx.gob.imss.mssistrans.ccom.rutas.util;

import mx.gob.imss.mssistrans.ccom.rutas.dto.TripulacionAsigResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigGroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TripulacionAsigMapper {
    TripulacionAsigMapper INSTANCE = Mappers.getMapper(TripulacionAsigMapper.class);

    List<TripulacionAsigResponse> entityAJson(List<TripulacionAsigGroupEntity> consultaGeneral);
}