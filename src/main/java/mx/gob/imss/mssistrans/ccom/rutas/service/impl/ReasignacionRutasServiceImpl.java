package mx.gob.imss.mssistrans.ccom.rutas.service.impl;

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
import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosAsigResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosAsigRutasResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.ReAsignacionRutasResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;
import mx.gob.imss.mssistrans.ccom.rutas.dto.SiniestrosResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.SolTrasladoResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.TripulacionAsigResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.DatosAsigEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.RutasAsigEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.SiniestrosEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.SolTrasladoEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigCam01Entity;
import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigCam02Entity;
import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigGroupEntity;

import mx.gob.imss.mssistrans.ccom.rutas.repository.DatosAsigRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.ReAsignacionRutasRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.RutasAsigRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.SiniestrosRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.SolTrasladoRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.TripulacionAsigCamillero01Repository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.TripulacionAsigCamillero02Repository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.TripulacionAsigRepository;
import mx.gob.imss.mssistrans.ccom.rutas.service.ReasignacionRutasService;
import mx.gob.imss.mssistrans.ccom.rutas.util.DatosAsigMapper;
import mx.gob.imss.mssistrans.ccom.rutas.util.ReAsignacionRutasMapper;
import mx.gob.imss.mssistrans.ccom.rutas.util.RutasMapper;
import mx.gob.imss.mssistrans.ccom.rutas.util.SiniestrosMapper;
import mx.gob.imss.mssistrans.ccom.rutas.util.SolTrasladoMapper;
import mx.gob.imss.mssistrans.ccom.rutas.util.TripulacionAsigMapper;
import mx.gob.imss.mssistrans.ccom.rutas.util.ValidaDatos;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Transactional(rollbackOn = SQLException.class)
@AllArgsConstructor
@Service
public class ReasignacionRutasServiceImpl implements ReasignacionRutasService {

	@Autowired
	private ReAsignacionRutasRepository reAsignacionRutasRepository;
	@Autowired
	private RutasAsigRepository rutasRepository;
	@Autowired
	private SolTrasladoRepository solTrasladoRepository;
	@Autowired
	private DatosAsigRepository datosRepository;
	@Autowired
	private TripulacionAsigRepository choferRepository;
	@Autowired
	private TripulacionAsigCamillero01Repository camillero01Repository;
	@Autowired
	private TripulacionAsigCamillero02Repository camillero02Repository;
	@Autowired
	private SiniestrosRepository siniestrosRepository;

	@Override
	public <T> Response<?> consultaVistaRapida(Integer pagina, Integer tamanio, String orden, String columna,
			String idRutaAsig, String idSolicitud) {
		Response<T> respuesta = new Response<>();
		String nomCol = ValidaDatos.getNameColReasig(columna);
		Pageable page = PageRequest.of(pagina, tamanio,
				Sort.by(Sort.Direction.fromString(orden.toUpperCase()), nomCol));
		try {
			Page consultaAsignacionRutas = null;
			if (idRutaAsig == null || idRutaAsig.equals(""))
				if (idSolicitud == null || idSolicitud.equals(""))
					consultaAsignacionRutas = reAsignacionRutasRepository.consultaGeneral(page);
				else
					consultaAsignacionRutas = reAsignacionRutasRepository.getConsultaByIdSolicitud(idSolicitud, page);
			else if (idSolicitud == null || idSolicitud.equals(""))
				consultaAsignacionRutas = reAsignacionRutasRepository.getConsultaByIdAsignacion(idRutaAsig, page);
			else
				consultaAsignacionRutas = reAsignacionRutasRepository.getConsultaById(idRutaAsig, idSolicitud, page);

			final List<ReAsignacionRutasResponse> content = (List<ReAsignacionRutasResponse>) ReAsignacionRutasMapper.INSTANCE
					.formatearListaArrendados(consultaAsignacionRutas.getContent());

			Page<ReAsignacionRutasResponse> objetoMapeado = new PageImpl<>(content, page,
					consultaAsignacionRutas.getTotalElements());

			return ValidaDatos.respReAsignacionRuta(respuesta, "Exito", objetoMapeado);
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}

	}

	@Override
	public Response delete(String idRutaAsignacion) {
		Response respuesta = new Response<>();
		try {
			reAsignacionRutasRepository.delete(idRutaAsignacion);
			reAsignacionRutasRepository.flush();
			return ValidaDatos.respAsignacionRuta(respuesta, "Exito", null);
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}
	}

	/****** HU006 - 26 **********/
	@Override
	public <T> Response getRutas(Integer idOoad, String rol) {
		Response<T> respuesta = new Response<>();
		List<RutasAsigEntity> consultaGeneral = null;
		try {
			if(rol.toUpperCase().equals("ADMINISTRADOR"))
				consultaGeneral = rutasRepository.getRutas();
			else
				consultaGeneral = rutasRepository.getRutasByOoad(idOoad);
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}

		List<DatosAsigRutasResponse> listaDeOoad = RutasMapper.INSTANCE.EntityAJson(consultaGeneral);
		return ValidaDatos.resp(respuesta, "Exito", listaDeOoad);
	}

	@Override
	public <T> Response getSolicitudTraslado(Integer idUnidadAdscripcion, Integer idVehiculo) {
		Response<T> respuesta = new Response<>();
		List<SolTrasladoEntity> consultaGeneral = null;
		try {
			consultaGeneral = solTrasladoRepository.getSolicitudTraslado(idUnidadAdscripcion, idVehiculo);
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}

		List<SolTrasladoResponse> listaDeSolicituTraslado = SolTrasladoMapper.INSTANCE.EntityAJson(consultaGeneral);
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
	public <T> Response getSiniestro() {
		Response<T> respuesta = new Response<>();
		List<SiniestrosEntity> consultaGeneral = null;
		try {
			consultaGeneral = siniestrosRepository.getSiniestro();
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}

		List<SiniestrosResponse> listaDeSiniestros = SiniestrosMapper.INSTANCE.EntityAJson(consultaGeneral);
		return ValidaDatos.respSiniestros(respuesta, "Exito", listaDeSiniestros);
	}

	@Override
	public <T> Response update(String idVehiculo, String idNuevoVehiculo, String idRuta, String idNuevaRuta,
			String idSolicitud, String idNuevaSolicitud, String desEstatus) {
		// TODO Auto-generated method stub
		Response<T> respuesta = new Response<>();
		try {
			System.out.println("idVehiculo: " + idVehiculo);
			System.out.println("idNuevoVehiculo: " + idNuevoVehiculo);
			System.out.println("idRuta: " + idRuta);
			System.out.println("idNuevaRuta: " + idNuevaRuta);
			System.out.println("idSolicitud: " + idSolicitud);
			System.out.println("idNuevaSolicitud: " + idNuevaSolicitud);
			System.out.println("desEstatus: " + desEstatus);

			datosRepository.update(idNuevoVehiculo, idNuevaRuta, idNuevaSolicitud, desEstatus, idVehiculo, idRuta,
					idSolicitud);

			datosRepository.flush();
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}
		return ValidaDatos.respDatosAsig(respuesta, "Exito", null);
	}

}
