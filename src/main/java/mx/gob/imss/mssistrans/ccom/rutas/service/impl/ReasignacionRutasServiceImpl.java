package mx.gob.imss.mssistrans.ccom.rutas.service.impl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
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
import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosUsuarioDTO;
import mx.gob.imss.mssistrans.ccom.rutas.dto.DetRutasAsignacionesResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.EccoResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.ReAsignacionRutasDTO;
import mx.gob.imss.mssistrans.ccom.rutas.dto.ReAsignacionRutasResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;
import mx.gob.imss.mssistrans.ccom.rutas.dto.SiniestrosResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.TripulacionAsigResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.DatosAsigEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.DetRutasAsigEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.DetalleReAsignacionRutasEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.EccoEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.ReAsignacionRutasEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.SiniestrosEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigCam01Entity;
import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigCam02Entity;
import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigGroupEntity;

import mx.gob.imss.mssistrans.ccom.rutas.repository.DatosAsigRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.DetalleReAsignacionRutasRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.EccoRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.ReAsignacionRutasRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.SiniestrosRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.TripulacionAsigCamillero01Repository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.TripulacionAsigCamillero02Repository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.TripulacionAsigRepository;
import mx.gob.imss.mssistrans.ccom.rutas.service.ReasignacionRutasService;
import mx.gob.imss.mssistrans.ccom.rutas.util.DatosAsigMapper;
import mx.gob.imss.mssistrans.ccom.rutas.util.EccoMapper;
import mx.gob.imss.mssistrans.ccom.rutas.util.ReAsignacionRutasMapper;
import mx.gob.imss.mssistrans.ccom.rutas.util.SiniestrosMapper;
import mx.gob.imss.mssistrans.ccom.rutas.util.TripulacionAsigMapper;
import mx.gob.imss.mssistrans.ccom.rutas.util.ValidaDatos;

/**
 * @author opimentel
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Transactional(rollbackOn = SQLException.class)
@AllArgsConstructor
@Service
public class ReasignacionRutasServiceImpl implements ReasignacionRutasService {

	@Autowired
	private ReAsignacionRutasRepository reAsignacionRutasRepository;
	@Autowired
	private EccoRepository eccoRepository;
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

	@Autowired
	private DetalleReAsignacionRutasRepository detReAsignacionRutasRepository;

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
	public Response delete(String idReAsignacion) {
		Response respuesta = new Response<>();
		try {
			reAsignacionRutasRepository.delete(idReAsignacion);
			reAsignacionRutasRepository.flush();
			return ValidaDatos.respAsignacionRuta(respuesta, "Exito", null);
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}
	}

	/****** HU006 - 26 **********/
	/*
	 * @Override public <T> Response getRutas(Integer idOoad, String rol) {
	 * Response<T> respuesta = new Response<>(); List<RutasAsigEntity>
	 * consultaGeneral = null; try { if(rol.toUpperCase().equals("ADMINISTRADOR"))
	 * consultaGeneral = rutasRepository.getRutas(); else consultaGeneral =
	 * rutasRepository.getRutasByOoad(idOoad); } catch (Exception e) { return
	 * ValidaDatos.errorException(respuesta, e); }
	 * 
	 * List<DatosAsigRutasResponse> listaDeOoad =
	 * RutasMapper.INSTANCE.EntityAJson(consultaGeneral); return
	 * ValidaDatos.resp(respuesta, "Exito", listaDeOoad); }
	 * 
	 * @Override public <T> Response getSolicitudTraslado(DatosUsuarioDTO
	 * datosUsuario, Integer idRuta) { Response<T> respuesta = new Response<>();
	 * List<SolTrasladoEntity> consultaGeneral = null; try { if
	 * (datosUsuario.getRol().toUpperCase().equals("ADMINISTRADOR")) consultaGeneral
	 * = solTrasladoRepository.getSolicitudTraslado(idRuta); else consultaGeneral =
	 * solTrasladoRepository.getSolicitudTraslado(datosUsuario.getIDOOAD(), idRuta);
	 * } catch (Exception e) { return ValidaDatos.errorException(respuesta, e); }
	 * 
	 * List<SolTrasladoResponse> listaDeSolicituTraslado =
	 * SolTrasladoMapper.INSTANCE.EntityAJson(consultaGeneral); return
	 * ValidaDatos.respSolTras(respuesta, "Exito", listaDeSolicituTraslado); }
	 */
	@Override
	public <T> Response getEcco(DatosUsuarioDTO datosUsuarios, Integer idRuta) {
		Response<T> respuesta = new Response<>();
		List<EccoEntity> consultaGeneral = null;
		try {
			consultaGeneral = eccoRepository.getEcco(idRuta);
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}

		List<EccoResponse> listaDeOoad = EccoMapper.INSTANCE.EntityAJson(consultaGeneral);
		return ValidaDatos.resp(respuesta, "Exito", listaDeOoad);
	}

	/*
	 * @Override public <T> Response getDatosReAsignacion(Integer idVehiculo,
	 * Integer idRuta, Integer idSolicitud) { Response<T> respuesta = new
	 * Response<>(); DatosAsigEntity consultaGeneral = null; try { consultaGeneral =
	 * datosRepository.getDatosAsignacion(idVehiculo, idRuta, idSolicitud); } catch
	 * (Exception e) { return ValidaDatos.errorException(respuesta, e); }
	 * 
	 * DatosAsigResponse listaDeSolicituTraslado =
	 * DatosAsigMapper.INSTANCE.EntityAJson(consultaGeneral); return
	 * ValidaDatos.resp(respuesta, "Exito", listaDeSolicituTraslado); }
	 */
	@Override
	public <T> Response getTripulacionAsignada(Integer idControlRuta) {
		Response<T> respuesta = new Response<>();
		List<TripulacionAsigGroupEntity> tripulacionAsigGroupEntity = new ArrayList<TripulacionAsigGroupEntity>();

		try {
			tripulacionAsigGroupEntity = obtenerTripulacion(idControlRuta);

		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}

		List<TripulacionAsigResponse> listaDeTripulacionAsignada = TripulacionAsigMapper.INSTANCE
				.EntityAJson(tripulacionAsigGroupEntity);
		return ValidaDatos.resp(respuesta, "Exito", listaDeTripulacionAsignada);
	}

	@Override
	public <T> Response save(ReAsignacionRutasDTO reAsignacionRutas) {
		// TODO Auto-generated method stub
		Response<T> respuesta = new Response<>();
		try {
			LocalDate hoy = LocalDate.now();
			LocalTime ahora = LocalTime.now();

			LocalDateTime fecha = LocalDateTime.of(hoy, ahora);
			DetalleReAsignacionRutasEntity reasignacionData = DatosAsigMapper.INSTANCE.JsonAEntity(reAsignacionRutas);

			reasignacionData.setFecAlta(fecha);
			detReAsignacionRutasRepository.save(reasignacionData);
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}
		return ValidaDatos.respDatosAsig(respuesta, "Exito", null);
	}

	/*
	 * @Override public <T> Response update(RegistroRecorridoDTO datosRecorrido) {
	 * // TODO Auto-generated method stub Response<T> respuesta = new Response<>();
	 * try { regRecorridoRepository.update(datosRecorrido.getHoraInicioAsignacion(),
	 * datosRecorrido.getIdRuta1(), datosRecorrido.getHrInicio1(),
	 * datosRecorrido.getHrFin1(), datosRecorrido.getIdRuta2(),
	 * datosRecorrido.getHrInicio2(), datosRecorrido.getHrFin2(),
	 * datosRecorrido.getIdRuta3(), datosRecorrido.getHrInicio3(),
	 * datosRecorrido.getHrFin3(), datosRecorrido.getEstatusTraslado(),
	 * datosRecorrido.getIdVehiculo(), datosRecorrido.getIdRuta());
	 * 
	 * datosRepository.flush(); } catch (Exception e) { return
	 * ValidaDatos.errorException(respuesta, e); } return
	 * ValidaDatos.respDatosAsig(respuesta, "Exito", null); }
	 */
	@Override
	public <T> Response getDetalleRutasyAsig(Integer idControlRuta) {
		Response<T> respuesta = new Response<>();
		DatosAsigEntity datosAsig = null;
		List<TripulacionAsigGroupEntity> tripulacionAsigEntity = new ArrayList<TripulacionAsigGroupEntity>();
		List<DetRutasAsigEntity> detalleRutasAsignaciones = new ArrayList<DetRutasAsigEntity>();
		DetRutasAsigEntity detRutasAsignaciones = new DetRutasAsigEntity();
		try {
			datosAsig = datosRepository.getDatosAsignacion(idControlRuta);
			tripulacionAsigEntity = obtenerTripulacion(idControlRuta);
			detRutasAsignaciones.setDatosAsigEntity(datosAsig);
			detRutasAsignaciones.setTripulacionAsigGroupEntity(tripulacionAsigEntity);
			detalleRutasAsignaciones.add(detRutasAsignaciones);
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}

		List<DetRutasAsignacionesResponse> listaDeSolicituTraslado = DatosAsigMapper.INSTANCE
				.EntityAJson(detalleRutasAsignaciones);
		return ValidaDatos.resp(respuesta, "Exito", listaDeSolicituTraslado);
	}

	private List<TripulacionAsigGroupEntity> obtenerTripulacion(Integer idControlRuta) {
		List<TripulacionAsigGroupEntity> tripulacionAsigGroupEntity = new ArrayList<TripulacionAsigGroupEntity>();

		TripulacionAsigGroupEntity tripulacionAsigEntity = new TripulacionAsigGroupEntity();
		TripulacionAsigCam01Entity getTripulante = null;
		TripulacionAsigCam02Entity getTripulante2 = null;
		TripulacionAsigEntity getChofer = null;
		getChofer = choferRepository.getDatosChofer(idControlRuta);
		if (getChofer != null) {
			tripulacionAsigEntity.setIdControlRuta(getChofer.getIdControlRuta());
			tripulacionAsigEntity.setIdPersonalAmbulancia(getChofer.getIdPersonalAmbulancia());
			tripulacionAsigEntity.setNombreChofer(getChofer.getNomTripulante());
			tripulacionAsigEntity.setNumTarjetaDig(getChofer.getNumTarjetaDig());
			tripulacionAsigEntity.setMatriculaChofer(getChofer.getCveMatricula());
			getTripulante = camillero01Repository.getCamillero1(idControlRuta);
			tripulacionAsigEntity.setNombreCamillero1(getTripulante.getNomTripulante());
			tripulacionAsigEntity.setMatriculaCamillero1(getTripulante.getCveMatricula());
			getTripulante2 = camillero02Repository.getDatosCamillero2(idControlRuta);
			tripulacionAsigEntity.setNombreCamillero2(getTripulante2.getNomTripulante());
			tripulacionAsigEntity.setMatriculaCamillero2(getTripulante2.getCveMatricula());
			tripulacionAsigGroupEntity.add(tripulacionAsigEntity);
		}
		return tripulacionAsigGroupEntity;
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
