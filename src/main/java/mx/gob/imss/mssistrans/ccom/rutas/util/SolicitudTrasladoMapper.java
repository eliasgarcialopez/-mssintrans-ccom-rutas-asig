package mx.gob.imss.mssistrans.ccom.rutas.util;

import mx.gob.imss.mssistrans.ccom.rutas.dto.SolicitudTrasladoResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.SolicitudTraslado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface SolicitudTrasladoMapper {

    SolicitudTrasladoMapper INSTANCE = Mappers.getMapper(SolicitudTrasladoMapper.class);

    /**
     * se transforma una solicitud traslado dto a entity
     *
     * @param entityList
     * @return
     */
    @Mapping(target = "cveOrigen", ignore = true)
    @Mapping(target = "cveDestino", ignore = true)
    SolicitudTraslado jsonToSolicitudTrasladoEntity(SolicitudTrasladoResponse solicitud);

    /**
     * se transforma una solicitud traslado entity a dto
     *
     * @param entityList
     * @return
     */
    @Mapping(target = "cveOrigen", ignore = true)
    @Mapping(target = "cveDestino", ignore = true)
    SolicitudTrasladoResponse solicitudTrasladoEntityToJsonTo(SolicitudTraslado solicitud);

    /**
     * se transforma las solicitudes de traslado a un dto en lista
     *
     * @param entityList
     * @return
     */
    List<SolicitudTrasladoResponse> entityListToDto(List<SolicitudTraslado> entityList);

}
