package mx.gob.imss.mssistrans.ccom.rutas.util;


import mx.gob.imss.mssistrans.ccom.rutas.dto.RegistroRecorridoResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.RegistroRecorridoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RegistroRecorridoMapper {
    RegistroRecorridoMapper INSTANCE = Mappers.getMapper(RegistroRecorridoMapper.class);

    RegistroRecorridoResponse entityAJson(RegistroRecorridoEntity consultaGeneral);
}