package mx.gob.imss.mssistrans.asignaciones.rutas.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssistrans.asignaciones.rutas.dto.RutasResponse;
import mx.gob.imss.mssistrans.asignaciones.rutas.model.RutasAsigEntity;


@Mapper
public interface RutasMapper {
	RutasMapper INSTANCE = Mappers.getMapper(RutasMapper.class);
	//List<VehiculosArrendadosCromaticaResponse> formatearListaArrendados ( List<VehiculosArrendadosCromaticaEntity> consulta );
	
	List<RutasResponse> EntityAJson ( List<RutasAsigEntity> consultaGeneral );
}