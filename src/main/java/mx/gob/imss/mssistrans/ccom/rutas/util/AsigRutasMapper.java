package mx.gob.imss.mssistrans.ccom.rutas.util;


import mx.gob.imss.mssistrans.ccom.rutas.dto.AsigRutasResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.AsigRutasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AsigRutasMapper {
    AsigRutasMapper INSTANCE = Mappers.getMapper(AsigRutasMapper.class);

    List<AsigRutasResponse> formatearListaArrendados(List<AsigRutasEntity> consulta);

    AsigRutasResponse entityToJson(AsigRutasEntity entity);
}