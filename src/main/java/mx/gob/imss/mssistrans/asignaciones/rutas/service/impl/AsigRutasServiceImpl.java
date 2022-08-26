package mx.gob.imss.mssistrans.asignaciones.rutas.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import mx.gob.imss.mssistrans.asignaciones.rutas.dto.AsigRutasResponse;
import mx.gob.imss.mssistrans.asignaciones.rutas.dto.DatosAsigResponse;
import mx.gob.imss.mssistrans.asignaciones.rutas.dto.RegistroRecorridoResponse;
import mx.gob.imss.mssistrans.asignaciones.rutas.dto.Response;
import mx.gob.imss.mssistrans.asignaciones.rutas.dto.RutasResponse;
import mx.gob.imss.mssistrans.asignaciones.rutas.dto.SolicitudTrasladoResponse;
import mx.gob.imss.mssistrans.asignaciones.rutas.dto.TripulacionAsigResponse;
import mx.gob.imss.mssistrans.asignaciones.rutas.model.DatosAsigEntity;
import mx.gob.imss.mssistrans.asignaciones.rutas.model.RegistroRecorridoEntity;
import mx.gob.imss.mssistrans.asignaciones.rutas.model.RutasAsigEntity;
import mx.gob.imss.mssistrans.asignaciones.rutas.model.SolicitudTrasladoEntity;
import mx.gob.imss.mssistrans.asignaciones.rutas.model.TripulacionAsigCam01Entity;
import mx.gob.imss.mssistrans.asignaciones.rutas.model.TripulacionAsigCam02Entity;
import mx.gob.imss.mssistrans.asignaciones.rutas.model.TripulacionAsigEntity;
import mx.gob.imss.mssistrans.asignaciones.rutas.model.TripulacionAsigGroupEntity;
import mx.gob.imss.mssistrans.asignaciones.rutas.repository.AsigRutasRepository;
import mx.gob.imss.mssistrans.asignaciones.rutas.repository.DatosAsigRepository;
import mx.gob.imss.mssistrans.asignaciones.rutas.repository.RegistroRecorridoRepository;
import mx.gob.imss.mssistrans.asignaciones.rutas.repository.RutasAsigRepository;
import mx.gob.imss.mssistrans.asignaciones.rutas.repository.SolicitudTrasladoRepository;
import mx.gob.imss.mssistrans.asignaciones.rutas.repository.TripulacionAsigCamillero01Repository;
import mx.gob.imss.mssistrans.asignaciones.rutas.repository.TripulacionAsigCamillero02Repository;
import mx.gob.imss.mssistrans.asignaciones.rutas.repository.TripulacionAsigRepository;
import mx.gob.imss.mssistrans.asignaciones.rutas.service.AsigRutasService;
import mx.gob.imss.mssistrans.asignaciones.rutas.util.AsigRutasMapper;
import mx.gob.imss.mssistrans.asignaciones.rutas.util.RutasMapper;
import mx.gob.imss.mssistrans.asignaciones.rutas.util.SolicitudTrasladoMapper;
import mx.gob.imss.mssistrans.asignaciones.rutas.util.TripulacionAsigMapper;
import mx.gob.imss.mssistrans.asignaciones.rutas.util.ValidaDatos;
import mx.gob.imss.mssistrans.asignaciones.rutas.util.DatosAsigMapper;
import mx.gob.imss.mssistrans.asignaciones.rutas.util.RegistroRecorridoMapper;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Transactional(rollbackOn = SQLException.class)
@AllArgsConstructor
@Service
public class AsigRutasServiceImpl implements AsigRutasService {

	@Autowired
	private AsigRutasRepository asigRutasRepository;
	@Autowired
	private RutasAsigRepository rutasRepository;
	@Autowired
	private SolicitudTrasladoRepository solicitudTrasladoRepository;
	@Autowired
	private DatosAsigRepository datosRepository;
	@Autowired
	private TripulacionAsigRepository choferRepository;
	@Autowired
	private TripulacionAsigCamillero01Repository camillero01Repository;
	@Autowired
	private TripulacionAsigCamillero02Repository camillero02Repository;
	@Autowired
	private RegistroRecorridoRepository regRecorridoRepository;

	@Override
	public <T> Response consultaGeneral(Integer pagina, Integer tamanio, String orden, String columna) {
		Response<T> respuesta = new Response<>();
		String nomCol = ValidaDatos.getNameCol(columna);
		Pageable page = PageRequest.of(pagina, tamanio,
				Sort.by(Sort.Direction.fromString(orden.toUpperCase()), nomCol));
		try {
			Page consultaAsignacioRutas = asigRutasRepository.consultaGeneral(page);
			final List<AsigRutasResponse> content = (List<AsigRutasResponse>) AsigRutasMapper.INSTANCE
					.formatearListaArrendados(consultaAsignacioRutas.getContent());
			Page<AsigRutasResponse> objetoMapeado = new PageImpl<>(content, page,
					consultaAsignacioRutas.getTotalElements());
			return ValidaDatos.respAsignacionRuta(respuesta, "Exito", objetoMapeado);
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}

	}

	@Override
	public <T> Response<?> consultaById(Integer pagina, Integer tamanio, String idRutaAsig, String idSolicitud) {
		Response<T> respuesta = new Response<>();
		Pageable page = PageRequest.of(pagina, tamanio, Sort.by("ID_RUTA"));
		try {
			Page consultaAsignacioRutas = null;
			if (!idRutaAsig.equals("") && !idSolicitud.equals(""))
				consultaAsignacioRutas = asigRutasRepository.getConsultaById(idRutaAsig, idSolicitud, page);
			else if (idSolicitud.equals("") && !idRutaAsig.equals(""))
				consultaAsignacioRutas = asigRutasRepository.getConsultaByIdAsignacion(idRutaAsig, page);
			else if (idRutaAsig.equals("") && !idSolicitud.equals(""))
				consultaAsignacioRutas = asigRutasRepository.getConsultaByIdSolicitud(idSolicitud, page);

			final List<AsigRutasResponse> content = (List<AsigRutasResponse>) AsigRutasMapper.INSTANCE
					.formatearListaArrendados(consultaAsignacioRutas.getContent());

			Page<AsigRutasResponse> objetoMapeado = new PageImpl<>(content, page,
					consultaAsignacioRutas.getTotalElements());

			return ValidaDatos.respAsignacionRuta(respuesta, "Exito", objetoMapeado);
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}

	}

	@Override
	public Response delete(String idRutaAsignacion) {
		Response respuesta = new Response<>();
		try {
			asigRutasRepository.delete(idRutaAsignacion);
			asigRutasRepository.flush();
			return ValidaDatos.respAsignacionRuta(respuesta, "Exito", null);
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}
	}

	/****** HU006 - 26 **********/
	@Override
	public <T> Response getRutas(Integer idOoad) {
		Response<T> respuesta = new Response<>();
		List<RutasAsigEntity> consultaGeneral = null;
		try {
			consultaGeneral = rutasRepository.getRutas(idOoad);
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}

		List<RutasResponse> listaDeOoad = RutasMapper.INSTANCE.EntityAJson(consultaGeneral);
		return ValidaDatos.respRutas(respuesta, "Exito", listaDeOoad);
	}

	@Override
	public <T> Response getSolicitudTraslado(Integer idUnidadAdscripcion, Integer idVehiculo) {
		Response<T> respuesta = new Response<>();
		List<SolicitudTrasladoEntity> consultaGeneral = null;
		try {
			consultaGeneral = solicitudTrasladoRepository.getSolicitudTraslado(idUnidadAdscripcion, idVehiculo);
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}

		List<SolicitudTrasladoResponse> listaDeSolicituTraslado = SolicitudTrasladoMapper.INSTANCE
				.EntityAJson(consultaGeneral);
		return ValidaDatos.respSolTras(respuesta, "Exito", listaDeSolicituTraslado);
	}

	@Override
	public <T> Response getDatosAsignacion(Integer idVehiculo, Integer idRuta, Integer idSolicitud) {
		Response<T> respuesta = new Response<>();
		List<DatosAsigEntity> consultaGeneral = null;
		try {
			consultaGeneral = datosRepository.getDatosAsignacion(idVehiculo, idRuta, idSolicitud);
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}

		List<DatosAsigResponse> listaDeSolicituTraslado = DatosAsigMapper.INSTANCE.EntityAJson(consultaGeneral);
		return ValidaDatos.respDatosAsig(respuesta, "Exito", listaDeSolicituTraslado);
	}

	@Override
	public <T> Response getTripulacionAsignada(Integer idRuta, Integer idVehiculo, Integer idSolicitud) {
		Response<T> respuesta = new Response<>();
		List<TripulacionAsigGroupEntity> tripulacionAsigGroupEntity = new ArrayList<TripulacionAsigGroupEntity>();
		try {
			TripulacionAsigGroupEntity tripulacionAsigEntity = new TripulacionAsigGroupEntity();
			TripulacionAsigCam01Entity getTripulante = null;
			TripulacionAsigCam02Entity getTripulante2 = null;
			TripulacionAsigEntity getChofer = null;
			getChofer = choferRepository.getDatosChofer(idRuta, idVehiculo);
			tripulacionAsigEntity.setIdVehiculo(getChofer.getIdVehiculo());
			tripulacionAsigEntity.setNombreChofer(getChofer.getNomTripulante());
			tripulacionAsigEntity.setNumTarjetaDig(getChofer.getNumTarjetaDig());
			tripulacionAsigEntity.setMatriculaChofer(getChofer.getCveMatricula());
			getTripulante = camillero01Repository.getCamillero1(idVehiculo, idRuta, idSolicitud);
			tripulacionAsigEntity.setNombreCamillero1(getTripulante.getNomTripulante());
			tripulacionAsigEntity.setMatriculaCamillero1(getTripulante.getCveMatricula());
			getTripulante2 = camillero02Repository.getDatosCamillero2(idVehiculo, idRuta, idSolicitud);
			tripulacionAsigEntity.setNombreCamillero2(getTripulante2.getNomTripulante());
			tripulacionAsigEntity.setMatriculaCamillero2(getTripulante2.getCveMatricula());

			tripulacionAsigGroupEntity.add(tripulacionAsigEntity);
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}

		List<TripulacionAsigResponse> listaDeTripulacionAsignada = TripulacionAsigMapper.INSTANCE
				.EntityAJson(tripulacionAsigGroupEntity);
		return ValidaDatos.respTripulacionAsig(respuesta, "Exito", listaDeTripulacionAsignada);
	}
	

	@Override
	public <T> Response getRegistroRecorrido(Integer idVehiculo, Integer idRuta) {
		Response<T> respuesta = new Response<>();
		RegistroRecorridoEntity consultaGeneral = null;
		try {
			consultaGeneral = regRecorridoRepository.getRegistroRecorrido(idVehiculo,idRuta);
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}

		RegistroRecorridoResponse listaDeSolicituTraslado = RegistroRecorridoMapper.INSTANCE.EntityAJson(consultaGeneral);
		return ValidaDatos.respRegistroRecorrido(respuesta, "Exito", listaDeSolicituTraslado);
	}
}
