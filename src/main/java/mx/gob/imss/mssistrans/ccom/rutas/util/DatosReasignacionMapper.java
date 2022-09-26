package mx.gob.imss.mssistrans.ccom.rutas.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mssistrans.ccom.rutas.dto.DetReasignacionesRutasResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.ReAsignacionRutasDTO;
import mx.gob.imss.mssistrans.ccom.rutas.model.DetReasignacionRutasEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.DetalleReAsignacionRutasEntity;



@Mapper
public interface DatosReasignacionMapper {
	DatosReasignacionMapper INSTANCE = Mappers.getMapper(DatosReasignacionMapper.class);
	//DatosAsigResponse EntityAJson ( DatosAsigEntity consultaGeneral );
	List<DetReasignacionesRutasResponse> EntityAJson ( List<DetReasignacionRutasEntity>  consultaGeneral );
	
	DetalleReAsignacionRutasEntity JsonAEntity ( ReAsignacionRutasDTO  consultaGeneral );
}