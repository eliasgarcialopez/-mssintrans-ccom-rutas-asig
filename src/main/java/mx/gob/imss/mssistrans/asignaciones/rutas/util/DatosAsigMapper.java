package mx.gob.imss.mssistrans.asignaciones.rutas.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssistrans.asignaciones.rutas.dto.DatosAsigResponse;
import mx.gob.imss.mssistrans.asignaciones.rutas.dto.VehiculoResponse;
import mx.gob.imss.mssistrans.asignaciones.rutas.model.DatosAsigEntity;
import mx.gob.imss.mssistrans.asignaciones.rutas.model.TripulacionAsigEntity;


@Mapper
public interface DatosAsigMapper {
	DatosAsigMapper INSTANCE = Mappers.getMapper(DatosAsigMapper.class);
	List<DatosAsigResponse> EntityAJson ( List<DatosAsigEntity> consultaGeneral );
}