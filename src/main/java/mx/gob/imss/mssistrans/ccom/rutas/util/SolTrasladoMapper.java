package mx.gob.imss.mssistrans.ccom.rutas.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssistrans.ccom.rutas.dto.SolTrasladoResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.SolTrasladoEntity;


@Mapper
public interface SolTrasladoMapper {
	SolTrasladoMapper INSTANCE = Mappers.getMapper(SolTrasladoMapper.class);
	List<SolTrasladoResponse> EntityAJson ( List<SolTrasladoEntity> consultaGeneral );
}