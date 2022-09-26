package mx.gob.imss.mssistrans.ccom.rutas.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosAsigResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.DetRutasAsignacionesResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.ReAsignacionRutasDTO;
import mx.gob.imss.mssistrans.ccom.rutas.model.DatosAsigEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.DetRutasAsigEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.DetalleReAsignacionRutasEntity;



@Mapper
public interface DatosAsigMapper {
	DatosAsigMapper INSTANCE = Mappers.getMapper(DatosAsigMapper.class);
	DatosAsigResponse EntityAJson ( DatosAsigEntity consultaGeneral );
	List<DetRutasAsignacionesResponse> EntityAJson ( List<DetRutasAsigEntity>  consultaGeneral );
	
	DetalleReAsignacionRutasEntity JsonAEntity ( ReAsignacionRutasDTO  consultaGeneral );
}