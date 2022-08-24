package mx.gob.imss.mssintetrans.ccom.rutas.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssintetrans.ccom.rutas.dto.SolicitudTrasladoResponse;
import mx.gob.imss.mssintetrans.ccom.rutas.model.SolicitudTraslado;



@Mapper
public interface SolicitudTrasladoMapper {
	
	SolicitudTrasladoMapper INSTANCE = Mappers.getMapper(SolicitudTrasladoMapper.class);
	
	 /**
     * se transforma una solicitud traslado dto a entity
     *
     * @param entityList
     * @return
     * @deprecated
     */
	SolicitudTraslado jsonToSolicitudTrasladoEntity(SolicitudTrasladoResponse solicitud);
	 /**
     * se transforma una solicitud traslado entity a dto
     *
     * @param entityList
     * @return
     * @deprecated
     */
	SolicitudTrasladoResponse solicitudTrasladoEntityToJsonTo(SolicitudTraslado solicitud);
    /**
     * 
     *se transforma las solicitudes de traslado a un dto en lista
     * @param entityList
     * @return
     * @deprecated
     */
    List<SolicitudTrasladoResponse> entityListToDto(List<SolicitudTraslado> entityList);
	
}
