package mx.gob.imss.mssistrans.rutas.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import mx.gob.imss.mssistrans.rutas.dto.OOADResponse;
import mx.gob.imss.mssistrans.rutas.dto.RutasRequest;
import mx.gob.imss.mssistrans.rutas.dto.RutasResponse;
import mx.gob.imss.mssistrans.rutas.dto.RutasTablaResponse;
import mx.gob.imss.mssistrans.rutas.dto.UnidadesAdscripcionResponse;
import mx.gob.imss.mssistrans.rutas.model.OOAD;
import mx.gob.imss.mssistrans.rutas.model.Rutas;
import mx.gob.imss.mssistrans.rutas.model.UnidadAdscripcion;


/**
 * Mapper para convertir de Dto a Entidad y viceversa.
 *
 *
 */
@Mapper
public interface OOADObjectMapper {

    OOADObjectMapper INSTANCE = Mappers.getMapper(OOADObjectMapper.class);

    /**
     * 
     *
     * @param entityList
     * @return
     * @deprecated
     */
    List<OOADResponse> entityListToDto(List<OOAD> entityList);
    
    
    

    /**
     * Transforma una Unidad del modelo a un DTO
     *
     * @param ooad
     * @return
     */

    OOADResponse entityToDto(OOAD ooad);

   
    
   
}
