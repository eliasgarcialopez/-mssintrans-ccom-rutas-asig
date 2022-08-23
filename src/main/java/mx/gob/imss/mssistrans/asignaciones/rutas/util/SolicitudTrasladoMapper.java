package mx.gob.imss.mssistrans.asignaciones.rutas.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssistrans.asignaciones.rutas.dto.SolicitudTrasladoResponse;
import mx.gob.imss.mssistrans.asignaciones.rutas.model.SolicitudTrasladoEntity;


@Mapper
public interface SolicitudTrasladoMapper {
	SolicitudTrasladoMapper INSTANCE = Mappers.getMapper(SolicitudTrasladoMapper.class);
	List<SolicitudTrasladoResponse> EntityAJson ( List<SolicitudTrasladoEntity> consultaGeneral );
}