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
import mx.gob.imss.mssistrans.ccom.rutas.dto.EccoResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.RegistroRecorridoDTO;
import mx.gob.imss.mssistrans.ccom.rutas.dto.RegistroRecorridoResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;
import mx.gob.imss.mssistrans.ccom.rutas.dto.SolTrasladoResponse;
import mx.gob.imss.mssistrans.ccom.rutas.dto.TripulacionAsigResponse;
import mx.gob.imss.mssistrans.ccom.rutas.model.DatosAsigEntity;
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
	private RegistroRecorridoRepository regRecorridoRepository;

	@Override
	public <T> Response<?> consultaVistaRapida(Integer pagina, Integer tamanio, String orden, String columna,
			String idRutaAsig, String idSolicitud) {
		Response<T> respuesta = new Response<>();
		String nomCol = ValidaDatos.getNameColAsignacion(columna);
		Pageable page = PageRequest.of(pagina, tamanio,
				Sort.by(Sort.Direction.fromString(orden.toUpperCase()), nomCol));
		try {
			Page consultaAsignacioRutas = null;
			if (idRutaAsig == null || idRutaAsig.equals(""))
				if (idSolicitud == null || idSolicitud.equals(""))
					consultaAsignacioRutas = asigRutasRepository.consultaGeneral(page);
				else
					consultaAsignacioRutas = asigRutasRepository.getConsultaByIdSolicitud(idSolicitud, page);
			else if (idSolicitud == null || idSolicitud.equals(""))
				consultaAsignacioRutas = asigRutasRepository.getConsultaByIdAsignacion(idRutaAsig, page);
			else
				consultaAsignacioRutas = asigRutasRepository.getConsultaById(idRutaAsig, idSolicitud, page);

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
			consultaGeneral = regRecorridoRepository.getRegistroRecorrido(idVehiculo, idRuta);
		} catch (Exception e) {
			return ValidaDatos.errorException(respuesta, e);
		}

		RegistroRecorridoResponse listaDeSolicituTraslado = RegistroRecorridoMapper.INSTANCE
				.EntityAJson(consultaGeneral);
		return ValidaDatos.respRegistroRecorrido(respuesta, "Exito", listaDeSolicituTraslado);
	}

	@Override
	public <T> Response update(RegistroRecorridoDTO datosRecorrido) {
		// TODO Auto-generated method stub
		Response<T> respuesta = new Response<>();
		try {
			regRecorridoRepository.update(datosRecorrido.getHoraInicioAsignacion(), datosRecorrido.getIdRuta1(),
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

}
