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
import mx.gob.imss.mssistrans.ccom.rutas.dto.AsigRutasResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosAsigResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosAsigRutasResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosUsuarioDTO;
import mx.gob.imss.mssistrans.ccom.rutas.dto.DetRutasAsignacionesResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.EccoResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.RegistroRecorridoDTO;
import mx.gob.imss.mssistrans.ccom.rutas.dto.RegistroRecorridoResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;
import mx.gob.imss.mssistrans.ccom.rutas.dto.SolTrasladoResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.TripulacionAsigResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.DatosAsigEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.DetRutasAsigEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.EccoEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.RegistroRecorridoEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.RutasAsigEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.SolTrasladoEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigCam01Entity;
import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigCam02Entity;
import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigGroupEntity;
import mx.gob.imss.mssistrans.ccom.rutas.repository.AsigRutasRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.DatosAsigRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.EccoRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.RegistroRecorridoRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.RutasAsigRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.SolTrasladoRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.TripulacionAsigCamillero01Repository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.TripulacionAsigCamillero02Repository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.TripulacionAsigRepository;
import mx.gob.imss.mssistrans.ccom.rutas.service.AsigRutasService;
import mx.gob.imss.mssistrans.ccom.rutas.util.AsigRutasMapper;
import mx.gob.imss.mssistrans.ccom.rutas.util.DatosAsigMapper;
import mx.gob.imss.mssistrans.ccom.rutas.util.EccoMapper;
import mx.gob.imss.mssistrans.ccom.rutas.util.RegistroRecorridoMapper;
import mx.gob.imss.mssistrans.ccom.rutas.util.RutasMapper;
import mx.gob.imss.mssistrans.ccom.rutas.util.SolTrasladoMapper;
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
public class AsigRutasServiceImpl implements AsigRutasService {

	@Autowired
	private AsigRutasRepository asigRutasRepository;
	@Autowired
	private RutasAsigRepository rutasRepository;
	@Autowired
	private SolTrasladoRepository solicitudTrasladoRepository;
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
	private RegistroRecorridoRepository regRecorrido1Repository;

	@Override
	public <T> Response<?> consultaVistaRapida(Integer pagina, Integer tamanio, String orden, String columna,
			String idRuta, String idSolicitud) {
		Response<T> respuesta = new Response<>();
		String nomCol = ValidaDatos.getNameColAsignacion(columna);
		Pageable page = PageRequest.of(pagina, tamanio,
				Sort.by(Sort.Direction.fromString(orden.toUpperCase()), nomCol));
		try {
			Page consultaAsignacioRutas = null;
			if (idRuta == null || idRuta.equals(""))
				if (idSolicitud == null || idSolicitud.equals(""))
					consultaAsignacioRutas = asigRutasRepository.consultaGeneral(page);
				else
					consultaAsignacioRutas = asigRutasRepository.getConsultaByIdSolicitud(idSolicitud, page);
			else if (idSolicitud == null || idSolicitud.equals(""))
				consultaAsignacioRutas = asigRutasRepository.getConsultaByIdAsignacion(idRuta, page);
			else
				consultaAsignacioRutas = asigRutasRepository.getConsultaById(idRuta, idSolicitud, page);

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
	public Response delete(String idControlRuta) {
		Response respuesta = new Response<>();
		try {
			asigRutasRepository.delete(idControlRuta);
			asigRutasRepository.flush();
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
			if (rol.toUpperCase().equals("ADMINISTRADOR"))
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
	public <T> Response getSolicitudTraslado(DatosUsuarioDTO datosUsuario, Integer idRuta) {
		Response<T> respuesta = new Response<>();
		List<SolTrasladoEntity> consultaGeneral = null;
		try {
			if (datosUsuario.getRol().toUpperCase().equals("ADMINISTRADOR"))
				consultaGeneral = solicitudTrasladoRepository.getSolicitudTraslado(idRuta);
			else
				consultaGeneral = solicitudTrasladoRepository.getSolicitudTraslado(datosUsuario.getIDOOAD(), idRuta);
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}

		List<SolTrasladoResponse> listaDeSolicituTraslado = SolTrasladoMapper.INSTANCE.EntityAJson(consultaGeneral);
		return ValidaDatos.respSolTras(respuesta, "Exito", listaDeSolicituTraslado);
	}

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

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public <T> Response getDatosAsignacion(Integer idControlRuta, Integer idRuta, Integer idSolicitud,
			Integer idVehiculo) {
		Response<T> respuesta = new Response<>();
		DatosAsigEntity consultaGeneral = null;
		try {
			if (idRuta != null && idSolicitud != null && idVehiculo != null) {
				if (!idRuta.equals("") && !idSolicitud.equals("") && !idVehiculo.equals("")) {
					consultaGeneral = datosRepository.getDatosAsigByidVehiculo(idRuta, idSolicitud, idVehiculo);
				} else if (!idControlRuta.equals(""))
					consultaGeneral = datosRepository.getDatosAsigByIdCtrlRuta(idControlRuta);
			} else if (idControlRuta != null)
				if (!idControlRuta.equals(""))
					consultaGeneral = datosRepository.getDatosAsigByIdCtrlRuta(idControlRuta);

		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}

		DatosAsigResponse listaDeSolicituTraslado = DatosAsigMapper.INSTANCE.EntityAJson(consultaGeneral);
		return ValidaDatos.resp(respuesta, "Exito", listaDeSolicituTraslado);
	}

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
	public <T> Response getRegistroRecorrido(Integer idControlRuta) {
		Response<T> respuesta = new Response<>();
		RegistroRecorridoEntity recorrido = null;
		try {
			recorrido = regRecorrido1Repository.getRegistroRecorrido(idControlRuta);
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}

		RegistroRecorridoResponse listaDeSolicituTraslado = RegistroRecorridoMapper.INSTANCE.EntityAJson(recorrido);
		return ValidaDatos.resp(respuesta, "Exito", listaDeSolicituTraslado);
	}

	@Override
	public <T> Response update(RegistroRecorridoDTO datosRecorrido) {
		// TODO Auto-generated method stub
		Response<T> respuesta = new Response<>();
		try {
			regRecorrido1Repository.update(datosRecorrido.getHoraInicioAsignacion(), datosRecorrido.getIdRuta1(),
					datosRecorrido.getHrInicio1(), datosRecorrido.getHrFin1(), datosRecorrido.getIdRuta2(),
					datosRecorrido.getHrInicio2(), datosRecorrido.getHrFin2(), datosRecorrido.getIdRuta3(),
					datosRecorrido.getHrInicio3(), datosRecorrido.getHrFin3(), datosRecorrido.getEstatusTraslado(),
					datosRecorrido.getIdVehiculo(), datosRecorrido.getIdRuta());

			datosRepository.flush();
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}
		return ValidaDatos.respDatosAsig(respuesta, "Exito", null);
	}

	@Override
	public <T> Response getDetalleRutasyAsig(Integer idControlRuta) {
		Response<T> respuesta = new Response<>();
		DatosAsigEntity datosAsig = null;
		List<TripulacionAsigGroupEntity> tripulacionAsigEntity = new ArrayList<TripulacionAsigGroupEntity>();
		RegistroRecorridoEntity registroRecorrido = null;
		List<DetRutasAsigEntity> detalleRutasAsignaciones = new ArrayList<DetRutasAsigEntity>();
		DetRutasAsigEntity detRutasAsignaciones = new DetRutasAsigEntity();
		try {
			datosAsig = datosRepository.getDatosAsigByIdCtrlRuta(idControlRuta);
			tripulacionAsigEntity = obtenerTripulacion(idControlRuta);
			registroRecorrido = regRecorrido1Repository.getRegistroRecorrido(idControlRuta);
			detRutasAsignaciones.setDatosAsigEntity(datosAsig);
			detRutasAsignaciones.setTripulacionAsigGroupEntity(tripulacionAsigEntity);
			detRutasAsignaciones.setRegistroRecorridoEntity(registroRecorrido);
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

}
