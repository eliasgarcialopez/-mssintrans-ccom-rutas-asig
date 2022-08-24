package mx.gob.imss.mssintetrans.ccom.rutas.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssintetrans.ccom.rutas.dto.SolicitudTrasladoResponse;
import mx.gob.imss.mssintetrans.ccom.rutas.dto.VehiculoResponse;
import mx.gob.imss.mssintetrans.ccom.rutas.model.SolicitudTraslado;
import mx.gob.imss.mssintetrans.ccom.rutas.model.Vehiculos;



@Mapper
public interface VehiculoMapper {
	
	VehiculoMapper INSTANCE = Mappers.getMapper(VehiculoMapper.class);
	
	 /**
     * se transforma un vehiculo dto a entity
     *
     * @param entityList
     * @return
     * @deprecated
     */
	Vehiculos jsonToVehiculoEntity(VehiculoResponse vehiculoResp);
	 /**
     * se transforma un vehiculo  entity a dto
     *
     * @param entityList
     * @return
     * @deprecated
     */
	VehiculoResponse vehiculoEntityToJsonTo(Vehiculos vehiculo);
    /**
     * 
     *se transforma las solicitudes de traslado a un dto en lista
     * @param entityList
     * @return
     * @deprecated
     */
    List<VehiculoResponse> entityListToDto(List<Vehiculos> entityList);
	
}
