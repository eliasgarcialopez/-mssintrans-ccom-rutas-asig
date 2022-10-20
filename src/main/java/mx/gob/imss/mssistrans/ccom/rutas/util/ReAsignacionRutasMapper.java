package mx.gob.imss.mssistrans.ccom.rutas.util;


import mx.gob.imss.mssistrans.ccom.rutas.dto.ReAsignacionRutasResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.ReAsignacionRutasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface ReAsignacionRutasMapper {
    ReAsignacionRutasMapper INSTANCE = Mappers.getMapper(ReAsignacionRutasMapper.class);

    List<ReAsignacionRutasResponse> formatearListaArrendados(List<ReAsignacionRutasEntity> consulta);
}