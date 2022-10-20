package mx.gob.imss.mssistrans.ccom.rutas.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssistrans.ccom.rutas.dto.EccoResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.ReasignacionEccoResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.EccoEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.ReasignacionEccoEntity;


@Mapper
public interface EccoMapper {
    EccoMapper INSTANCE = Mappers.getMapper(EccoMapper.class);

    List<EccoResponse> eccoEntityToJson(List<EccoEntity> consultaGeneral);
    EccoResponse eccoEntityToJson(EccoEntity entitty);

    List<ReasignacionEccoResponse> entityToJson(List<ReasignacionEccoEntity> consultaGeneral);
    ReasignacionEccoResponse entityToJson(ReasignacionEccoEntity consultaGeneral);
}