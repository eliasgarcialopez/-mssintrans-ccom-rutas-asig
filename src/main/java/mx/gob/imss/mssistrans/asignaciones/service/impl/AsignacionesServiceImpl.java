package mx.gob.imss.mssistrans.asignaciones.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.asignaciones.dto.Asignacion;
import mx.gob.imss.mssistrans.asignaciones.dto.CatalogoGenerico;
import mx.gob.imss.mssistrans.asignaciones.dto.DatosAsignacion;
import mx.gob.imss.mssistrans.asignaciones.dto.DatosVehiculo;
import mx.gob.imss.mssistrans.asignaciones.dto.Respuesta;
import mx.gob.imss.mssistrans.asignaciones.dto.RespuestaTO;
import mx.gob.imss.mssistrans.asignaciones.model.AsignacionesEntity;
import mx.gob.imss.mssistrans.asignaciones.model.ChoferesEntity;
import mx.gob.imss.mssistrans.asignaciones.model.RutasEntity;
import mx.gob.imss.mssistrans.asignaciones.model.TarjetasElectronicasEntity;
import mx.gob.imss.mssistrans.asignaciones.model.VehiculosEntity;
import mx.gob.imss.mssistrans.asignaciones.repository.AsignacionesRepository;
import mx.gob.imss.mssistrans.asignaciones.repository.ChoferesRepository;
import mx.gob.imss.mssistrans.asignaciones.repository.RutasRepository;
import mx.gob.imss.mssistrans.asignaciones.repository.TarjetasElectronicasRepository;
import mx.gob.imss.mssistrans.asignaciones.repository.VehiculosRepository;
import mx.gob.imss.mssistrans.asignaciones.service.AsignacionesService;
import mx.gob.imss.mssistrans.asignaciones.util.AsignacionesMapper;

/**
 * Servicio para las Asignaciones
 * @author gsz
 */
@Service
@AllArgsConstructor
@Slf4j
public class AsignacionesServiceImpl implements AsignacionesService {
	
	private final VehiculosRepository vehiculosRepository;
	
	private final ChoferesRepository choferesRepository;
	
	private final RutasRepository rutasRespository;
	
	private final TarjetasElectronicasRepository tarjetasRepository;
	
	private final AsignacionesRepository asignacionesRepository;
	
	@Override
	public RespuestaTO busquedaAsignacionesByOoad(Integer pagina, Integer tamanio, String orden, Integer idOoad) {
		RespuestaTO<List<DatosAsignacion>> response = new RespuestaTO<>();
	    List<DatosAsignacion> lstAsignaciones = new ArrayList<DatosAsignacion>();
	    if (orden.equals("2"))
	    	orden = "CH.NOM_CHOFER";
	    else if (orden.equals("3"))
	    	orden = "SV.CVE_ECCO";
	    else
	    	orden = "NUM_ASIGNACION";
	    Pageable page = PageRequest.of(pagina, tamanio, Sort.by(orden));
		
		try {
			Page consultaGeneral = asignacionesRepository.findAsignacionesByOoad(idOoad,page);
			List<AsignacionesEntity> lstAsignacionesEntity = consultaGeneral.getContent();
			//List<AsignacionesEntity> lstAsignacionesEntity = asignacionesRepository.findAsignacionesByOoad(idOoad);
			for (AsignacionesEntity asignacionEntity : lstAsignacionesEntity) {
				DatosAsignacion asignacion = new DatosAsignacion();
				asignacion.setIdAsignacion(asignacionEntity.getIdAsignacion());
				asignacion.setNumAsignacion(asignacionEntity.getNumAsignacion());
			    asignacion.setCveEcco(asignacionEntity.getVehiculo().getCveEcco());
			    asignacion.setNumRuta(asignacionEntity.getRuta().getNumRuta());
			    asignacion.setNomChofer(asignacionEntity.getChofer().getNombreChofer());
				lstAsignaciones.add(asignacion);
				
			}
			Page<DatosAsignacion> paginaAsignaciones = new PageImpl<>(lstAsignaciones, page,
                    consultaGeneral.getTotalElements());
			response.setDatos(paginaAsignaciones);
        	response.setCodigo(HttpStatus.OK.value());
            response.setMensaje("Exito");
            response.setError(false);
			
		} catch (Exception exc) {
			exc.printStackTrace();
            log.error("Error en busqueda general de asignaciones ", exc.getMessage());
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje("Error en busqueda general de asignaciones " + exc.getMessage());
            response.setError(true);
		}
		
		return response;
	}
	
	@Override
	public Respuesta<List<DatosAsignacion>> busquedaAsignacionesByNum(String numAsignacion, Integer idOoad) {
		Respuesta<List<DatosAsignacion>> response = new Respuesta<>();
	    List<DatosAsignacion> lstAsignaciones = new ArrayList<DatosAsignacion>();
		try {
			List<AsignacionesEntity> lstAsignacionesEntity = (List<AsignacionesEntity>) asignacionesRepository.findAsignacionesByNum(numAsignacion, idOoad);
			for (AsignacionesEntity asignacionEntity : lstAsignacionesEntity) {
				DatosAsignacion asignacion = new DatosAsignacion();
				asignacion.setIdAsignacion(asignacionEntity.getIdAsignacion());
				asignacion.setNumAsignacion(asignacionEntity.getNumAsignacion());
			    asignacion.setCveEcco(asignacionEntity.getVehiculo().getCveEcco());
			    asignacion.setNumRuta(asignacionEntity.getRuta().getNumRuta());
			    asignacion.setNomChofer(asignacionEntity.getChofer().getNombreChofer());
				lstAsignaciones.add(asignacion);
			}
			response.setDatos(lstAsignaciones);
        	response.setCodigo(HttpStatus.OK.value());
            response.setMensaje("Exito");
            response.setError(false);
			
		} catch (Exception exc) {
            log.error("Error en busqueda de asignaciones por folio ", exc.getMessage());
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje("Error en busqueda de asignaciones por folio " + exc.getMessage());
            response.setError(true);
		}
		
		return response;
	}

	@Override
	public Respuesta<List<DatosVehiculo>> busquedaVehiculosAsignables(Integer idOoad) {
		Respuesta<List<DatosVehiculo>> response = new Respuesta<>();
		List<DatosVehiculo> lstVehiculos = new ArrayList<DatosVehiculo>();
		
		try {
			List<VehiculosEntity> lstVehiculosEntity = vehiculosRepository.findVehiculoAsignables(idOoad);
			for (VehiculosEntity vehiculoEntity : lstVehiculosEntity) {
				DatosVehiculo datosVehiculo = AsignacionesMapper.INSTANCE.datosVehToJson(vehiculoEntity);
				lstVehiculos.add(datosVehiculo);
			}
			response.setDatos(lstVehiculos);
			response.setCodigo(HttpStatus.OK.value());
            response.setMensaje("Exito");
            response.setError(false);
			
		} catch (Exception exc) {
            log.error("Error en busqueda de vehiculos asignables ", exc.getMessage());
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje("Error en busqueda de vehiculos asignables " + exc.getMessage());
            response.setError(true);
		}
		
		return response;
	}

	@Override
	public Respuesta<List<CatalogoGenerico>> busquedaChoferes(Integer idOoad) {
		Respuesta<List<CatalogoGenerico>> response = new Respuesta<>();
		List<CatalogoGenerico> lstChoferes = new ArrayList<CatalogoGenerico>();
		
        try {
        	List<ChoferesEntity> lstChoferesEntity = choferesRepository.findChoferesActivos(idOoad);
        	for (ChoferesEntity choferEntity : lstChoferesEntity) {
        		CatalogoGenerico catalogo = new CatalogoGenerico(choferEntity.getIdChofer(), choferEntity.getNombreChofer(), null);
        		lstChoferes.add(catalogo);
        	}
			response.setDatos(lstChoferes);
			response.setCodigo(HttpStatus.OK.value());
            response.setMensaje("Exito");
            response.setError(false);
            
		} catch (Exception exc) {
            log.error("Error en busqueda de choferes ", exc.getMessage());
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje("Error en busqueda de choferes " + exc.getMessage());
            response.setError(true);
		}
		
		return response;
	}
	
	@Override
	public Respuesta<List<CatalogoGenerico>> busquedaRutas(Integer idOoad) {
		Respuesta<List<CatalogoGenerico>> response = new Respuesta<>();
		List<CatalogoGenerico> lstRutas = new ArrayList<CatalogoGenerico>();
		
        try {
        	List<RutasEntity> lstRutasEntity = rutasRespository.findRutasActivas(idOoad);
        	for (RutasEntity rutasEntity : lstRutasEntity) {
        		CatalogoGenerico catalogo = new CatalogoGenerico(rutasEntity.getIdRuta(), rutasEntity.getNumRuta(), rutasEntity.getDesServicio());
        		lstRutas.add(catalogo);
        	}
			response.setDatos(lstRutas);
			response.setCodigo(HttpStatus.OK.value());
            response.setMensaje("Exito");
            response.setError(false);
            
		} catch (Exception exc) {
            log.error("Error en busqueda de choferes ", exc.getMessage());
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje("Error en busqueda de choferes " + exc.getMessage());
            response.setError(true);
		}
		
		return response;
	}

	@Override
	public Respuesta<List<CatalogoGenerico>> busquedaTarjetasDigitales(Integer idOoad) {
        Respuesta<List<CatalogoGenerico>> response = new Respuesta<>();
        List<CatalogoGenerico> lstTarjetas = new ArrayList<CatalogoGenerico>();
		
        try {
        	TarjetasElectronicasEntity tarjetaEntity = tarjetasRepository.findTarjetasDigitalesByOoad(idOoad);
        	ArrayList<String> lstTarjetasUsadas = asignacionesRepository.findTarjetasByOoad(idOoad); // Para descartar tarjetas utilizadas
        	int folioInicial = Integer.parseInt(tarjetaEntity.getNumFolioInicial());
        	int folioFinal = Integer.parseInt(tarjetaEntity.getNumFolioFinal());
        	for (int i=folioInicial ; i<=folioFinal ; i++) {
            	CatalogoGenerico catalogo = new CatalogoGenerico(i, String.format("%010d",i), null);
            	if (lstTarjetasUsadas.contains(String.format("%010d",i)))  continue; 
        		lstTarjetas.add(catalogo);
        	}
        	response.setDatos(lstTarjetas);
        	response.setCodigo(HttpStatus.OK.value());
            response.setMensaje("Exito");
            response.setError(false);
			
		} catch (Exception exc) {
            log.error("Error en busqueda de tarjetas digitales ", exc.getMessage());
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje("Error en busqueda de tarjetas digitales " + exc.getMessage());
            response.setError(true);
		}
		
		return response;
	}

	@Override
	public Respuesta<Asignacion> registraAsignacion(Asignacion asignacion) {
		Respuesta<Asignacion> response = new Respuesta<Asignacion>();
		
        try {
        	log.info("Creando asignacion");
        	AsignacionesEntity asignacionEntity = AsignacionesMapper.INSTANCE.jsonToAsignaEntity(asignacion);
        	asignacionEntity.setVehiculo(vehiculosRepository.getById(asignacion.getIdVehiculo()));
        	asignacionEntity.setNumAsignacion(getSigNumAsignacion(asignacionEntity.getVehiculo().getUnidad().getNumCc().substring(0,2)));
        	asignacionEntity.setChofer(choferesRepository.getById(asignacion.getIdChofer()));
        	asignacionEntity.setRuta(rutasRespository.getById(asignacion.getIdRuta()));
        	asignacionEntity.setFechaAlta(new Date());
        	asignacionEntity.setIndActivo(true);
        	asignacionEntity.setIndSistema(true);
        	asignacionesRepository.save(asignacionEntity);
        	// Actualización de estatus tarjeta no aplica
        	
        	response.setCodigo(HttpStatus.OK.value());
	        response.setMensaje("Exito");
	        response.setError(false);
	        Asignacion asignacionResp = AsignacionesMapper.INSTANCE.asignaEntityToJson(asignacionEntity);
	        asignacionResp.setIdVehiculo(asignacion.getIdVehiculo());
	        asignacionResp.setIdChofer(asignacion.getIdChofer());
	        asignacionResp.setIdRuta(asignacion.getIdRuta());
	        response.setDatos(asignacionResp);
        	
		} catch (Exception exc) {
            log.error("Error en registro de asignacion ", exc.getMessage());
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje("Error en registro de asignacion " + exc.getMessage());
            response.setError(true);
		}
        
		return response;
	}

	@Override
	public Respuesta<Asignacion> getAsignacionById(Integer idAsignacion) {
		Respuesta<Asignacion> response = new Respuesta<Asignacion>();
		
		try {
			AsignacionesEntity asignacionEntity = asignacionesRepository.getById(idAsignacion);
			response.setCodigo(HttpStatus.OK.value());
	        response.setMensaje("Exito");
	        response.setError(false);
	        Asignacion asignacionResp = AsignacionesMapper.INSTANCE.asignaEntityToJson(asignacionEntity);
	        asignacionResp.setIdVehiculo(asignacionEntity.getVehiculo().getIdVehiculo());
	        asignacionResp.setIdChofer(asignacionEntity.getChofer().getIdChofer());
	        asignacionResp.setIdRuta(asignacionEntity.getRuta().getIdRuta());
	        response.setDatos(asignacionResp);
	        
		} catch (Exception exc) {
            log.error("Error en consulta de asignacion por id ", exc.getMessage());
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje("Error en consulta de asignacion por id" + exc.getMessage());
            response.setError(true);
		}
		return response;
	}

	@Override
	public Respuesta<Asignacion> actualizaAsignacion(Asignacion asignacion) {
        Respuesta<Asignacion> response = new Respuesta<Asignacion>();
		
		try {
			AsignacionesEntity asignacionEntity = asignacionesRepository.getById(asignacion.getIdAsignacion());
			
			asignacionEntity.setVehiculo(vehiculosRepository.getById(asignacion.getIdVehiculo()));
			asignacionEntity.setDesMarca(asignacion.getDesMarca());
			asignacionEntity.setNumPlacas(asignacion.getNumPlacas());
			asignacionEntity.setDesModelo(asignacion.getDesModelo());
			asignacionEntity.setChofer(choferesRepository.getById(asignacion.getIdChofer()));
			asignacionEntity.setNumFolioTarjeta(asignacion.getNumFolioTarjeta());
			asignacionEntity.setDesEstatus(asignacion.getDesEstatus());
			
			asignacionEntity.setFechaActualizacion(new Date());
			asignacionEntity.setIndActivo(true);
        	asignacionEntity.setIndSistema(true);
			asignacionesRepository.save(asignacionEntity);
			
			response.setCodigo(HttpStatus.OK.value());
	        response.setMensaje("Exito");
	        response.setError(false);
	        Asignacion asignacionResp = AsignacionesMapper.INSTANCE.asignaEntityToJson(asignacionEntity);
	        asignacionResp.setIdVehiculo(asignacion.getIdVehiculo());
	        asignacionResp.setIdChofer(asignacion.getIdChofer());
	        asignacionResp.setIdRuta(asignacion.getIdRuta());
	        response.setDatos(asignacionResp);
	        
		} catch (Exception exc) {
            log.error("Error en actualización de asignacion ", exc.getMessage());
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje("Error en actualización de asignacion " + exc.getMessage());
            response.setError(true);
		}
		return response;
	}

	@Override
	public Respuesta<Integer> eliminaAsignacion(Integer idAsignacion) {
		Respuesta<Integer> response = new Respuesta<Integer>();
		
		try {
			AsignacionesEntity asignacionEntity = asignacionesRepository.getById(idAsignacion);
			asignacionEntity.setFechaBaja(new Date());
			asignacionEntity.setIndActivo(false);
			asignacionesRepository.save(asignacionEntity);
			response.setCodigo(HttpStatus.OK.value());
	        response.setMensaje("Exito");
	        response.setError(false);
	        response.setDatos(idAsignacion);
			
		} catch (Exception exc) {
			 log.error("Error en eliminación de asignacion ", exc.getMessage());
	         response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
	         response.setMensaje("Error en eliminación de asignacion " + exc.getMessage());
	         response.setError(true);
		}
		return response;
	}

	
	private String getSigNumAsignacion(String clavePresup) {
		String ultimoFolio = asignacionesRepository.findUltimoFolioByOoad(clavePresup);
		if (ultimoFolio == null) {
			ultimoFolio = "0000";
		}
		return "ASIS-" + clavePresup + "-" + String.format("%04d",Integer.parseInt(ultimoFolio) + 1);
	}
}
