package mx.gob.imss.mssistrans.ccom.rutas.util;

import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosAsigRutasResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.RutasAsigEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface RutasMapper {
    RutasMapper INSTANCE = Mappers.getMapper(RutasMapper.class);

    List<DatosAsigRutasResponse> entityAJson(List<RutasAsigEntity> consultaGeneral);
}