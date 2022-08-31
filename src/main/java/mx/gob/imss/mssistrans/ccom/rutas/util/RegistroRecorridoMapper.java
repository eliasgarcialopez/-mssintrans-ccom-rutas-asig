package mx.gob.imss.mssistrans.ccom.rutas.util;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssistrans.ccom.rutas.dto.RegistroRecorridoResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.RegistroRecorridoEntity;

@Mapper
public interface RegistroRecorridoMapper {
	RegistroRecorridoMapper INSTANCE = Mappers.getMapper(RegistroRecorridoMapper.class);
	RegistroRecorridoResponse EntityAJson ( RegistroRecorridoEntity consultaGeneral );
}