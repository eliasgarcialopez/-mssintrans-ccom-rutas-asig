package mx.gob.imss.mssistrans.ccom.rutas.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosAsigResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.DatosAsigEntity;



@Mapper
public interface DatosAsigMapper {
	DatosAsigMapper INSTANCE = Mappers.getMapper(DatosAsigMapper.class);
	List<DatosAsigResponse> EntityAJson ( List<DatosAsigEntity> consultaGeneral );
}