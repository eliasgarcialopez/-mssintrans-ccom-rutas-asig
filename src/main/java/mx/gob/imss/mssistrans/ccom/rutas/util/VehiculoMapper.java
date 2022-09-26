package mx.gob.imss.mssistrans.ccom.rutas.util;

import java.util.List;

import mx.gob.imss.mssistrans.ccom.rutas.model.Vehiculos;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssistrans.ccom.rutas.dto.VehiculoResponse;


@Mapper
public interface VehiculoMapper {
	
	VehiculoMapper INSTANCE = Mappers.getMapper(VehiculoMapper.class);
	
	 /**
     * se transforma un vehiculo dto a entity
     *
     * @param entityList
     * @return
     */
	Vehiculos jsonToVehiculoEntity(VehiculoResponse vehiculoResp);
	 /**
     * se transforma un vehiculo  entity a dto
     *
     * @param entityList
     * @return
     */
	VehiculoResponse vehiculoEntityToJsonTo(Vehiculos vehiculo);
    /**
     * 
     *se transforma las solicitudes de traslado a un dto en lista
     * @param entityList
     * @return
     */
    List<VehiculoResponse> entityListToDto(List<Vehiculos> entityList);
	
}
