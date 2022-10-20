package mx.gob.imss.mssistrans.ccom.rutas.util;

import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosAsigResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.DetRutasAsignacionesResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.ReAsignacionRutasDTO;
import mx.gob.imss.mssistrans.ccom.rutas.model.DatosAsigEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.DetRutasAsigEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.DetalleReAsignacionRutasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface DatosAsigMapper {
    DatosAsigMapper INSTANCE = Mappers.getMapper(DatosAsigMapper.class);

    DatosAsigResponse entityAJson(DatosAsigEntity consultaGeneral);

    DetRutasAsignacionesResponse entityAJson(DetRutasAsigEntity entity);
    List<DetRutasAsignacionesResponse> entityAJson(List<DetRutasAsigEntity> consultaGeneral);


    DetalleReAsignacionRutasEntity jsonAEntity(ReAsignacionRutasDTO consultaGeneral);
}