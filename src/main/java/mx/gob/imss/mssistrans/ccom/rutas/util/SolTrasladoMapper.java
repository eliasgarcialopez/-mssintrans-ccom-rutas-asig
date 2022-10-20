package mx.gob.imss.mssistrans.ccom.rutas.util;

import mx.gob.imss.mssistrans.ccom.rutas.dto.SolTrasladoResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.SolTrasladoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface SolTrasladoMapper {
    SolTrasladoMapper INSTANCE = Mappers.getMapper(SolTrasladoMapper.class);

    List<SolTrasladoResponse> entityAJson(List<SolTrasladoEntity> consultaGeneral);
}