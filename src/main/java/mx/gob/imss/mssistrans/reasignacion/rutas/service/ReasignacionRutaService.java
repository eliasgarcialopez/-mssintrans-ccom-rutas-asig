package mx.gob.imss.mssistrans.reasignacion.rutas.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import mx.gob.imss.mssistrans.reasignacion.rutas.dto.CatalogoGenerico;
import mx.gob.imss.mssistrans.reasignacion.rutas.dto.DatosVehiculo;
import mx.gob.imss.mssistrans.reasignacion.rutas.dto.ReasignacionRutaRequestDTO;
import mx.gob.imss.mssistrans.reasignacion.rutas.dto.Respuesta;

public interface ReasignacionRutaService {

	<T>Respuesta get(Pageable page) throws Exception;
	<T>Respuesta save(ReasignacionRutaRequestDTO requestDTO) throws Exception;
	<T>Respuesta consultaId(Integer id) throws Exception;
	<T>Respuesta delete(int id) throws Exception;
	<T>Respuesta actualizar(Integer id, ReasignacionRutaRequestDTO request) throws Exception;
	Respuesta<List<CatalogoGenerico>> busquedaChoferes();
	Respuesta<List<DatosVehiculo>> busquedaVehiculosAsignables();
}
