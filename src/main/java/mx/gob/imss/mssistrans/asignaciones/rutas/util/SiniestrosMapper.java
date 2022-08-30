package mx.gob.imss.mssistrans.asignaciones.rutas.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssistrans.asignaciones.rutas.dto.DatosAsigResponse;
import mx.gob.imss.mssistrans.asignaciones.rutas.dto.RegistroRecorridoResponse;
import mx.gob.imss.mssistrans.asignaciones.rutas.dto.SiniestrosResponse;
import mx.gob.imss.mssistrans.asignaciones.rutas.dto.VehiculoResponse;
import mx.gob.imss.mssistrans.asignaciones.rutas.model.DatosAsigEntity;
import mx.gob.imss.mssistrans.asignaciones.rutas.model.RegistroRecorridoEntity;
import mx.gob.imss.mssistrans.asignaciones.rutas.model.SiniestrosEntity;
import mx.gob.imss.mssistrans.asignaciones.rutas.model.TripulacionAsigEntity;


@Mapper
public interface SiniestrosMapper {
	SiniestrosMapper INSTANCE = Mappers.getMapper(SiniestrosMapper.class);
	List<SiniestrosResponse> EntityAJson ( List<SiniestrosEntity> consultaGeneral );
}