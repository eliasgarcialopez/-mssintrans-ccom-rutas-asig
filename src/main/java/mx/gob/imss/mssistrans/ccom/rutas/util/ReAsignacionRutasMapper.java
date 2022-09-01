package mx.gob.imss.mssistrans.ccom.rutas.util;


import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssistrans.ccom.rutas.dto.ReAsignacionRutasResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.ReAsignacionRutasEntity;


@Mapper
public interface ReAsignacionRutasMapper {
	ReAsignacionRutasMapper INSTANCE = Mappers.getMapper(ReAsignacionRutasMapper.class);
	
	List<ReAsignacionRutasResponse> formatearListaArrendados ( List<ReAsignacionRutasEntity> consulta );
	/*
	VehiculosCromaticaResponse formatearListaResponse ( VehiculosArrendadosCromaticaEntity unidad );
	VehiculosCromaticaResponse EntityAJson ( VehiculosCromaticaEntity generaPDF );
	VehiculosCromaticaResponse EntityPDFToJson ( VehiculosCromaticaPDFEntity generaPDF );

	VehiculosCromaticaResponse entityAJson(VehiculosCromaticaEntity unidad);
	
	VehiculosCromaticaEntity JsonAEntity(VehiculosCromaticaResponse vehiculo);
	*/
}