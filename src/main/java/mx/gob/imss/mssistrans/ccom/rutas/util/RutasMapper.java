package mx.gob.imss.mssistrans.ccom.rutas.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosAsigRutasResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.RutasAsigEntity;



@Mapper
public interface RutasMapper {
	RutasMapper INSTANCE = Mappers.getMapper(RutasMapper.class);
	//List<VehiculosArrendadosCromaticaResponse> formatearListaArrendados ( List<VehiculosArrendadosCromaticaEntity> consulta );
	
	List<DatosAsigRutasResponse> EntityAJson ( List<RutasAsigEntity> consultaGeneral );
}