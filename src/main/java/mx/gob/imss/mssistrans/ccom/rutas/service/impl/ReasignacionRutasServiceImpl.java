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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import mx.gob.imss.mssistrans.ccom.rutas.dto.AsigRutasResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosUsuario;
import mx.gob.imss.mssistrans.ccom.rutas.dto.DetReasignacionesRutasResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.ReasignacionEccoResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.ReasignacionTripulacionResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;
import mx.gob.imss.mssistrans.ccom.rutas.dto.SiniestrosResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.DatosControlRutasEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.DetReasignacionRutasEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.ReasignacionEccoEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.ReasignacionTripulacionEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.ReasignacionTripulacionGroupEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.SiniestrosEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigCam01Entity;
import mx.gob.imss.mssistrans.ccom.rutas.model.TripulacionAsigCam02Entity;
import mx.gob.imss.mssistrans.ccom.rutas.repository.AsigRutasRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.DatosAsigRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.DatosControlRutasRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.ReAsignacionRutasRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.ReasignacionEccoRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.ReasignacionTripulacionRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.SiniestrosRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.TripulacionAsigCamillero01Repository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.TripulacionAsigCamillero02Repository;
import mx.gob.imss.mssistrans.ccom.rutas.service.ReasignacionRutasService;
import mx.gob.imss.mssistrans.ccom.rutas.util.AsigRutasMapper;
import mx.gob.imss.mssistrans.ccom.rutas.util.DatosReasignacionMapper;
import mx.gob.imss.mssistrans.ccom.rutas.util.EccoMapper;
import mx.gob.imss.mssistrans.ccom.rutas.util.ReasignacionTripulacionMapper;
import mx.gob.imss.mssistrans.ccom.rutas.util.SiniestrosMapper;
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
	private AsigRutasRepository asigRutasRepository;

	@Autowired
	private ReasignacionEccoRepository eccoRepository;
	
	@Autowired
	private DatosAsigRepository datosRepository;
	
	@Autowired
	private DatosControlRutasRepository datosCRRepository;
	
	@Autowired
	private ReasignacionTripulacionRepository choferRepository;
	
	@Autowired
	private TripulacionAsigCamillero01Repository camillero01Repository;
	
	@Autowired
	private TripulacionAsigCamillero02Repository camillero02Repository;
	
	@Autowired
	private SiniestrosRepository siniestrosRepository;

	@Autowired
	private ReAsignacionRutasRepository reAsignacionRutasRepository;
	
	private static final String EMPTY_STRING = "";
	
	@Override
	public <T> Response<?> consultaVistaRapida(Integer pagina, Integer tamanio, String orden, String columna,
			String idRuta, String idSolicitud) {
		Response<T> respuesta = new Response<>();
		String nomCol = ValidaDatos.getNameColAsignacion(columna);
		Pageable page = PageRequest.of(pagina, tamanio,
				Sort.by(Sort.Direction.fromString(orden.toUpperCase()), nomCol));
		try {
			Page consultaAsignacionRutas = null;
			if (idRuta == null || idRuta.equals(EMPTY_STRING))
				if (idSolicitud == null || idSolicitud.equals(EMPTY_STRING))
					consultaAsignacionRutas = asigRutasRepository.consultaGeneralRasignaciones(page);
				else
					consultaAsignacionRutas = asigRutasRepository.getConsultaByIdSolicitudReasignaciones(idSolicitud, page);
			else if (idSolicitud == null || idSolicitud.equals(EMPTY_STRING))
				consultaAsignacionRutas = asigRutasRepository.getConsultaByIdAsignacionReasignaciones(idRuta, page);
			else
				consultaAsignacionRutas = asigRutasRepository.getConsultaByIdReasignaciones(idRuta, idSolicitud, page);
			final List<AsigRutasResponse> content = (List<AsigRutasResponse>) AsigRutasMapper.INSTANCE
					.formatearListaArrendados(consultaAsignacionRutas.getContent());

			Page<AsigRutasResponse> objetoMapeado = new PageImpl<>(content, page,
					consultaAsignacionRutas.getTotalElements());

			return ValidaDatos.resp(respuesta, "Exito", objetoMapeado);

		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}

	}

	@Override
	public Response delete(String idReAsignacion) {
		Response respuesta = new Response<>();
		try {
			String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user.equals("denegado")) {
				return ValidaDatos.noAutorizado(respuesta);
			}
	
			Gson gson = new Gson();
			DatosUsuario datosUsuarios = gson.fromJson(user, DatosUsuario.class);
			asigRutasRepository.deleteReasignacion(datosUsuarios.getMatricula(), idReAsignacion);
			asigRutasRepository.flush();

			asigRutasRepository.actalizarCOntrolRutasReasignacion(idReAsignacion);
			asigRutasRepository.flush();
			return ValidaDatos.resp(respuesta, "Exito", null);
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}
	}

	/****** HU006 - 28 **********/

	@Override
	public <T> Response getDetalleReAsignacion(Integer idControlRuta) {
		Response<T> respuesta = new Response<>();
		DatosControlRutasEntity datosCR = null;
		ReasignacionTripulacionGroupEntity tripulacionAsigEntity = new ReasignacionTripulacionGroupEntity();
		
		List<DetReasignacionRutasEntity> detalleReAsignaciones = new ArrayList<DetReasignacionRutasEntity>();
		DetReasignacionRutasEntity detRutasAsignaciones = new DetReasignacionRutasEntity();
		try {
			datosCR = datosCRRepository.getDatosAsigByIdCtrlRuta(idControlRuta);
			tripulacionAsigEntity = obtenerTripulacion (idControlRuta, null, null, null);

			detRutasAsignaciones.setDatosReasignacion(datosCR);
			detRutasAsignaciones.setTripulacion(tripulacionAsigEntity);
			detalleReAsignaciones.add(detRutasAsignaciones);
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}

		List<DetReasignacionesRutasResponse> listaDeSolicituTraslado = DatosReasignacionMapper.INSTANCE.EntityAJson(detalleReAsignaciones);
		return ValidaDatos.resp(respuesta, "Exito", listaDeSolicituTraslado);
	}


	@Override
	public <T> Response getEcco() {
		Response<T> respuesta = new Response<>();
		List<ReasignacionEccoEntity> consultaGeneral = null;
		try {
			consultaGeneral = eccoRepository.getEcco();
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}

		List<ReasignacionEccoResponse> listaDeEccos = EccoMapper.INSTANCE.EntityToJson(consultaGeneral);
		return ValidaDatos.resp(respuesta, "Exito", listaDeEccos);
	}

	@Override
	public <T> Response getTripulacionAsignada(Integer idControlRuta, Integer idRuta, Integer idSolicitud,
			Integer idVehiculo){
		Response<T> respuesta = new Response<>();
		List<ReasignacionTripulacionGroupEntity> tripulacionAsigGroupEntity = new ArrayList<ReasignacionTripulacionGroupEntity>();

		try {
			tripulacionAsigGroupEntity.add(obtenerTripulacion(idControlRuta, idRuta, idSolicitud, idVehiculo));

		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}

		List<ReasignacionTripulacionResponse> listaDeTripulacionAsignada = ReasignacionTripulacionMapper.INSTANCE
				.EntityAJson(tripulacionAsigGroupEntity);
		return ValidaDatos.resp(respuesta, "Exito", listaDeTripulacionAsignada);
	}


	private ReasignacionTripulacionGroupEntity obtenerTripulacion(Integer idControlRuta, Integer idRuta, Integer idSolicitud,
			Integer idVehiculo){
		ReasignacionTripulacionGroupEntity tripulacionAsigEntity = new ReasignacionTripulacionGroupEntity();
		TripulacionAsigCam01Entity getTripulante = null;
		TripulacionAsigCam02Entity getTripulante2 = null;
		ReasignacionTripulacionEntity getChofer = null;

		if (idRuta != null && idSolicitud != null && idVehiculo != null) {
			if (!idRuta.equals(EMPTY_STRING) && !idSolicitud.equals(EMPTY_STRING) && !idVehiculo.equals(EMPTY_STRING)) {
				getChofer = choferRepository.getDatosChoferByidVehiculo(idRuta, idSolicitud, idVehiculo);
				getTripulante = camillero01Repository.getCamillero1ByIdVehiculo(idRuta, idSolicitud, idVehiculo);
				getTripulante2 = camillero02Repository.getCamillero2ByIdVehiculo(idRuta, idSolicitud, idVehiculo);
			} else if (!idControlRuta.equals(EMPTY_STRING)) {
				getChofer = choferRepository.getDatosChofer(idControlRuta);
				getTripulante = camillero01Repository.getCamillero1(idControlRuta);
				getTripulante2 = camillero02Repository.getCamillero2(idControlRuta);
			}
		} else if (idControlRuta != null)
			if (!idControlRuta.equals(EMPTY_STRING)) {
				getChofer = choferRepository.getDatosChofer(idControlRuta);
				getTripulante = camillero01Repository.getCamillero1(idControlRuta);
				getTripulante2 = camillero02Repository.getCamillero2(idControlRuta);
			}
		if (getChofer != null) {
			tripulacionAsigEntity.setIdControlRuta(idControlRuta!=null?idControlRuta.toString():EMPTY_STRING);
			tripulacionAsigEntity.setIdPersonalAmbulancia(getChofer.getIdPersonalAmbulancia());
			tripulacionAsigEntity.setIdChofer(getChofer.getIdChofer());
			tripulacionAsigEntity.setNombreChofer(getChofer.getNomTripulante());
			tripulacionAsigEntity.setNumTarjetaDig(getChofer.getNumTarjetaDig());
			tripulacionAsigEntity.setMatriculaChofer(getChofer.getCveMatricula());
			tripulacionAsigEntity.setDesMotivoReasig(getChofer.getDesMotivoReasig());
			tripulacionAsigEntity.setDesSiniestro(getChofer.getDesSiniestro());
			tripulacionAsigEntity.setIdReasignacion(getChofer.getIdReasignacion());
			tripulacionAsigEntity.setNombreCamillero1(getTripulante.getNomTripulante());
			tripulacionAsigEntity.setMatriculaCamillero1(getTripulante.getCveMatricula());
			tripulacionAsigEntity.setNombreCamillero2(getTripulante2.getNomTripulante());
			tripulacionAsigEntity.setMatriculaCamillero2(getTripulante2.getCveMatricula());
			//tripulacionAsigGroupEntity.add(tripulacionAsigEntity);
		}
		return tripulacionAsigEntity;
	}

	@Override
	public <T> Response getSiniestro() {
		Response<T> respuesta = new Response<>();
		List<SiniestrosEntity> getSiniestros = null;
		try {
			getSiniestros = siniestrosRepository.getSiniestro();
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}

		List<SiniestrosResponse> listaDeSiniestros = SiniestrosMapper.INSTANCE.EntityAJson(getSiniestros);
		return ValidaDatos.respSiniestros(respuesta, "Exito", listaDeSiniestros);
	}

	@Override
	public <T> Response save(Integer idVehiculo, Integer idRuta, Integer idChofer, String desMotivoReasig,
			String desSiniestro, Integer idVehiculoSust, Integer idChoferSust, Integer idAsignacion
			, String cveMatricula) {
		// TODO Auto-generated method stub
		Response<T> respuesta = new Response<>();
		try {
			if(idVehiculoSust == null || idVehiculoSust.equals(EMPTY_STRING))
				idVehiculoSust = idVehiculo;
			if(idChoferSust == null || idChoferSust.equals(EMPTY_STRING))
				idChoferSust = idChofer;
			reAsignacionRutasRepository.save(idVehiculo, idRuta, idChofer, desMotivoReasig, desSiniestro
					, idVehiculoSust, idChoferSust, idAsignacion, cveMatricula);
			datosRepository.flush();
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}
		return ValidaDatos.resp(respuesta, "Exito", null);
	}

	@Override
	public <T> Response update(String desSiniestro, Integer idVehiculoSust, String desMotivoReasignacion, Integer idVehiculo, Integer idRuta, Integer idChofer) {
		Response<T> respuesta = new Response<>();
		try {
			String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user.equals("denegado")) {
				return ValidaDatos.noAutorizado(respuesta);
			}

			Gson gson = new Gson();
			DatosUsuario datosUsuarios = gson.fromJson(user, DatosUsuario.class);
			
			if (desSiniestro != null && idVehiculoSust != null && desMotivoReasignacion != null)
				if (!desSiniestro.equals(EMPTY_STRING) && !idVehiculoSust.equals(EMPTY_STRING) && !desMotivoReasignacion.equals(EMPTY_STRING))
					reAsignacionRutasRepository.update(desSiniestro, idVehiculoSust, desMotivoReasignacion, datosUsuarios.getMatricula(), idVehiculo,
							idRuta, idChofer);
				else if (idVehiculoSust != null && desMotivoReasignacion != null)
					if (!idVehiculoSust.equals(EMPTY_STRING) && !desMotivoReasignacion.equals(EMPTY_STRING))
						reAsignacionRutasRepository.update(idVehiculoSust, desMotivoReasignacion, datosUsuarios.getMatricula(), idVehiculo, idRuta,
								idChofer);
			if (desSiniestro != null && desMotivoReasignacion != null)
				if (!desSiniestro.equals(EMPTY_STRING) && !desMotivoReasignacion.equals(EMPTY_STRING))
					reAsignacionRutasRepository.update(desSiniestro, desMotivoReasignacion, datosUsuarios.getMatricula(), idVehiculo, idRuta,
							idChofer);
			if (desSiniestro != null && idVehiculoSust != null)
				if (!desSiniestro.equals(EMPTY_STRING) && !idVehiculoSust.equals(EMPTY_STRING))
					reAsignacionRutasRepository.update(desSiniestro, idVehiculoSust, datosUsuarios.getMatricula(), idVehiculo, idRuta, idChofer);
			if (desSiniestro != null)
				if (!desSiniestro.equals(EMPTY_STRING))
					reAsignacionRutasRepository.update(desSiniestro, datosUsuarios.getMatricula(), idVehiculo, idRuta, idChofer);
			if (idVehiculoSust != null)
				if (!idVehiculoSust.equals(EMPTY_STRING))
					reAsignacionRutasRepository.update(idVehiculoSust, datosUsuarios.getMatricula(), idVehiculo, idRuta, idChofer);
			if (desMotivoReasignacion != null)
				if (!desMotivoReasignacion.equals(EMPTY_STRING))
					reAsignacionRutasRepository.updateReasig(desMotivoReasignacion, datosUsuarios.getMatricula(), idVehiculo, idRuta, idChofer);
			datosRepository.flush();
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}
		return ValidaDatos.resp(respuesta, "Exito", null);
	}
		

}
