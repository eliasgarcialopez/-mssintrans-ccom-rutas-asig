package mx.gob.imss.mssistrans.ccom.rutas.util;

import mx.gob.imss.mssistrans.ccom.rutas.dto.DetReasignacionesRutasResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.ReAsignacionRutasDTO;
import mx.gob.imss.mssistrans.ccom.rutas.model.DetReasignacionRutasEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.DetalleReAsignacionRutasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface DatosReasignacionMapper {
    DatosReasignacionMapper INSTANCE = Mappers.getMapper(DatosReasignacionMapper.class);

    List<DetReasignacionesRutasResponse> entityAJson(List<DetReasignacionRutasEntity> consultaGeneral);

    DetalleReAsignacionRutasEntity jsonAEntity(ReAsignacionRutasDTO consultaGeneral);
}