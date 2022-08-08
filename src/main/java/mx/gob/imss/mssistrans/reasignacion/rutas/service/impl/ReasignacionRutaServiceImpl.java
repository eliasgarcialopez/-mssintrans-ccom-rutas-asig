package mx.gob.imss.mssistrans.reasignacion.rutas.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import mx.gob.imss.mssistrans.reasignacion.rutas.dto.CatalogoGenerico;
import mx.gob.imss.mssistrans.reasignacion.rutas.dto.DatosVehiculo;
import mx.gob.imss.mssistrans.reasignacion.rutas.dto.ReasignacionRutaRequestDTO;
import mx.gob.imss.mssistrans.reasignacion.rutas.dto.ReasignacionRutasTablaResponse;
import mx.gob.imss.mssistrans.reasignacion.rutas.dto.Respuesta;
import mx.gob.imss.mssistrans.reasignacion.rutas.dto.TestResponse;
import mx.gob.imss.mssistrans.reasignacion.rutas.model.ChoferesEntity;
import mx.gob.imss.mssistrans.reasignacion.rutas.model.ReasignacionRutasEntity;
import mx.gob.imss.mssistrans.reasignacion.rutas.model.VehiculosEntity;
import mx.gob.imss.mssistrans.reasignacion.rutas.repository.ChoferesRepository;
import mx.gob.imss.mssistrans.reasignacion.rutas.repository.ReasignacionRutasRepository;
import mx.gob.imss.mssistrans.reasignacion.rutas.repository.VehiculosRepository;
import mx.gob.imss.mssistrans.reasignacion.rutas.service.ReasignacionRutaService;
import mx.gob.imss.mssistrans.reasignacion.rutas.util.ReasignacionRutasMapper;

/**
 * @author Brayan Ramiro Quezada Hernandez
 *
 */
@Service
public class ReasignacionRutaServiceImpl implements ReasignacionRutaService {

	@Autowired
	private ReasignacionRutasRepository reasignacionRutasRepository;
	@Autowired
	private ChoferesRepository choferesRepository;
	@Autowired 
	private VehiculosRepository vehiculosRepository;

	@Override
	public <T> Respuesta get(Pageable page) throws Exception {
		Respuesta<T> respuesta = new Respuesta<>();
		
		try {
			final Page consulta  = reasignacionRutasRepository.consulta(page);
			final List<ReasignacionRutasTablaResponse> content = 
					ReasignacionRutasMapper.INSTANCE.formatearListaResponse(consulta.getContent());
			Page<ReasignacionRutasTablaResponse> objetoMapeado = new PageImpl<>(
					content, 
					page, 
					consulta.getTotalElements());
			
			final List<ReasignacionRutasEntity> contentSecond = consulta.getContent();
			final List<TestResponse> lstTestResponse = new ArrayList<>();
			for(ReasignacionRutasEntity entity : contentSecond) {
				TestResponse response = new TestResponse();
				response.setIdReasignacion(entity.getIdReasignacion());
				response.setIdRuta(entity.getIdRuta());
				response.setCveEcco(entity.getIdVehiculoSust().getCveEcco());
				response.setNumPlacas(entity.getIdVehiculoSust().getNumPlacas());
				response.setNombreChofer(entity.getIdChoferSust().getNombreChofer());
				lstTestResponse.add(response);
			}
			Page<TestResponse> newObjetoMapeado = new PageImpl<>(
					lstTestResponse, 
					page, 
					consulta.getTotalElements());
			respuesta.setCodigo(HttpStatus.OK.value());
			respuesta.setError(false);
			respuesta.setMensaje("Éxito");
			respuesta.setDatos((T) newObjetoMapeado);
		} catch (Exception e) {
			respuesta.setCodigo(HttpStatus.NOT_FOUND.value());
			respuesta.setError(true);
			respuesta.setMensaje(e.getMessage());
		}
		return respuesta;
	}

	@Override
	@Transactional(rollbackOn = SQLException.class)
	public <T> Respuesta save(ReasignacionRutaRequestDTO requestDTO) throws Exception {
		Respuesta<T> response = new Respuesta<>();
		requestDTO.setIndActivo(1);
		requestDTO.setIndSistema(0);
		requestDTO.setFecAlta(new Date());
		ReasignacionRutasEntity entity = ReasignacionRutasMapper.INSTANCE.jsonAEntity(requestDTO);
		ReasignacionRutasEntity newEntity;
		
		try {
			newEntity = reasignacionRutasRepository.saveAndFlush(entity);
			response.setCodigo(HttpStatus.OK.value());
			response.setError(false);
			response.setMensaje("Éxito");
//			response.setDatos("");
		} catch (Exception e) {
			Log.error(e.getMessage());
			response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setError(true);
			response.setMensaje(e.getMessage());
			return response;
		}
		return response;
	}

	@Override
	public <T> Respuesta consultaId(Integer id) throws Exception {
		Respuesta<T> response = new Respuesta<>();
		ReasignacionRutasEntity entity = null;
		try {
			entity = reasignacionRutasRepository.consultaId(id);
			response.setCodigo(HttpStatus.OK.value());
			response.setError(false);
			response.setMensaje("Éxito");
			response.setDatos((T) entity);
		} catch (Exception e) {
			response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setError(false);
			response.setMensaje(e.getMessage());
			response.setDatos((T) "Registro con indicador activo 0");
			return response;
		}
		return response;
	}

	@Override
	@Transactional(rollbackOn = SQLException.class)
	public <T> Respuesta delete(int id) throws Exception {
		Respuesta<T> response = new Respuesta<>();
		try {
			reasignacionRutasRepository.delete(id);
			reasignacionRutasRepository.flush();
			response.setCodigo(HttpStatus.OK.value());
			response.setError(false);
			response.setMensaje("Éxito");
		} catch (Exception e) {
			Log.error(e.getMessage());
			response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setError(true);
			response.setMensaje(e.getMessage());
			return response;
		}
		return response;
	}

	@Override
	@Transactional(rollbackOn = SQLException.class)
	public <T> Respuesta actualizar(Integer id, ReasignacionRutaRequestDTO request) throws Exception {
		Respuesta<T> response = new Respuesta<>();
		try {
			reasignacionRutasRepository.actualizar(
					request.getMotivo(), 
					request.getSiniestro(),
					request.getIdVehiculoSust().getIdVehiculo(), 
					request.getIdChoferSust().getIdChofer(), 
					request.getCveMatricula(), id);
			reasignacionRutasRepository.flush();
			response.setCodigo(HttpStatus.OK.value());
			response.setError(false);
			response.setMensaje("Actualización Exitosa");
		} catch (Exception e) {
			Log.error(e.getMessage());
			response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setError(true);
			response.setMensaje(e.getMessage());
			return response;
		}
		return response;
	}
	
	@Override
	public Respuesta<List<CatalogoGenerico>> busquedaChoferes() {
		Respuesta<List<CatalogoGenerico>> response = new Respuesta<>();
		List<CatalogoGenerico> lstChoferes = new ArrayList<CatalogoGenerico>();
		
        try {
        	List<ChoferesEntity> lstChoferesEntity = choferesRepository.choferesActivos();
        	for (ChoferesEntity choferEntity : lstChoferesEntity) {
        		CatalogoGenerico catalogo = new CatalogoGenerico(choferEntity.getIdChofer(), choferEntity.getNombreChofer());
        		lstChoferes.add(catalogo);
        	}
			response.setDatos(lstChoferes);
			response.setCodigo(HttpStatus.OK.value());
            response.setMensaje("Exito");
            response.setError(false);
            
		} catch (Exception exc) {
            Log.error("Error en busqueda de choferes " + exc.getMessage());
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje("Error en busqueda de choferes " + exc.getMessage());
            response.setError(true);
		}
		
		return response;
	}

	@Override
	public Respuesta<List<DatosVehiculo>> busquedaVehiculosAsignables() {
		Respuesta<List<DatosVehiculo>> response = new Respuesta<>();
		List<DatosVehiculo> lstVehiculos = new ArrayList<DatosVehiculo>();
		
		try {
			List<VehiculosEntity> lstVehiculosEntity = vehiculosRepository.findVehiculoAsignables();
			for (VehiculosEntity vehiculoEntity : lstVehiculosEntity) {
				DatosVehiculo datosVehiculo = ReasignacionRutasMapper.INSTANCE.datosVehToJson(vehiculoEntity);
				lstVehiculos.add(datosVehiculo);
			}
			response.setDatos(lstVehiculos);
			response.setCodigo(HttpStatus.OK.value());
            response.setMensaje("Exito");
            response.setError(false);
			
		} catch (Exception exc) {
            Log.error("Error en busqueda de vehiculos asignables " + exc.getMessage());
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje("Error en busqueda de vehiculos asignables " + exc.getMessage());
            response.setError(true);
		}
		
		return response;
	}

}
