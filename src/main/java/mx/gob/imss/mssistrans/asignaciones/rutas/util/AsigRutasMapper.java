package mx.gob.imss.mssistrans.asignaciones.rutas.util;


import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssistrans.asignaciones.rutas.dto.AsigRutasResponse;
import mx.gob.imss.mssistrans.asignaciones.rutas.model.AsigRutasEntity;


@Mapper
public interface AsigRutasMapper {
	AsigRutasMapper INSTANCE = Mappers.getMapper(AsigRutasMapper.class);
	
	List<AsigRutasResponse> formatearListaArrendados ( List<AsigRutasEntity> consulta );
	/*
	VehiculosCromaticaResponse formatearListaResponse ( VehiculosArrendadosCromaticaEntity unidad );
	VehiculosCromaticaResponse EntityAJson ( VehiculosCromaticaEntity generaPDF );
	VehiculosCromaticaResponse EntityPDFToJson ( VehiculosCromaticaPDFEntity generaPDF );

	VehiculosCromaticaResponse entityAJson(VehiculosCromaticaEntity unidad);
	
	VehiculosCromaticaEntity JsonAEntity(VehiculosCromaticaResponse vehiculo);
	*/
}