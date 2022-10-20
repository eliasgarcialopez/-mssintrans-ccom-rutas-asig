package mx.gob.imss.mssistrans.ccom.rutas.util;

import mx.gob.imss.mssistrans.ccom.rutas.dto.SiniestrosResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.SiniestrosEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SiniestrosMapper {
    SiniestrosMapper INSTANCE = Mappers.getMapper(SiniestrosMapper.class);

    List<SiniestrosResponse> entityAJson(List<SiniestrosEntity> consultaGeneral);
}