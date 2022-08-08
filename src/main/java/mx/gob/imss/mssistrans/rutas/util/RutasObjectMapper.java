package mx.gob.imss.mssistrans.rutas.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import mx.gob.imss.mssistrans.rutas.dto.RutasRequest;
import mx.gob.imss.mssistrans.rutas.dto.RutasResponse;
import mx.gob.imss.mssistrans.rutas.dto.RutasTablaResponse;
import mx.gob.imss.mssistrans.rutas.model.Rutas;


/**
 * Mapper para convertir de Dto a Entidad y viceversa.
 *
 *
 */
@Mapper
public interface RutasObjectMapper {

    RutasObjectMapper INSTANCE = Mappers.getMapper(RutasObjectMapper.class);

    /**
     * 
     *
     * @param entityList
     * @return
     * @deprecated
     */
    List<RutasTablaResponse> entityListToDto(List<Rutas> entityList);
    
    
    

    /**
     * Transforma una Ruta del modelo a un DTO
     *
     * @param arrendatarios
     * @return
     */

   RutasTablaResponse entityToDto(Rutas rutas);

    /**
     * Transorma el DTO a una Ruta del modelo
     *
     * @param json
     * @return
     */
    Rutas dtoToEntity(RutasRequest json);
    void updateFromDto(RutasRequest rutasDto, @MappingTarget Rutas rutas);
    
    /**
     * Transorma un Entity de Rutas a RutasResponseDTO 
     *
     * @param json
     * @return
     */
    @Mapping(target = "idOOAD", ignore = true)
    @Mapping(target= "destinos", ignore= true)
    RutasResponse entityToDTO(Rutas rutas);
    
   
}
