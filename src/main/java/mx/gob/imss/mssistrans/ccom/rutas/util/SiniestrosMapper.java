package mx.gob.imss.mssistrans.ccom.rutas.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssistrans.ccom.rutas.dto.SiniestrosResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.SiniestrosEntity;

@Mapper
public interface SiniestrosMapper {
	SiniestrosMapper INSTANCE = Mappers.getMapper(SiniestrosMapper.class);
	List<SiniestrosResponse> EntityAJson ( List<SiniestrosEntity> consultaGeneral );
}