package mx.gob.imss.mssistrans.ccom.rutas.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Asignacion;
import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosUsuario;
import mx.gob.imss.mssistrans.ccom.rutas.dto.RespuestaAsig;
import mx.gob.imss.mssistrans.ccom.rutas.model.AsignacionesEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.VehiculosEntity;
import mx.gob.imss.mssistrans.ccom.rutas.repository.AsignacionesRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.VehiculosRepository;
import mx.gob.imss.mssistrans.ccom.rutas.repository.VehiculosRepositorySG;
import mx.gob.imss.mssistrans.ccom.rutas.service.AsignacionesService;
import mx.gob.imss.mssistrans.ccom.rutas.util.AsignacionesMapper;

/**
 * Servicio para las Asignaciones
 *
 * @author gsz
 */
@Service
@Slf4j
public class AsignacionesServiceImpl implements AsignacionesService {
	
	
	@Autowired
    private VehiculosRepositorySG vehiculosRepository;
    
    @Autowired
    private  AsignacionesRepository asignacionesRepository;
    
    private String mensaje;
    
    private static final String ASC = "ASC";

    

    @Transactional(rollbackOn = SQLException.class)
    @Override
    public RespuestaAsig<AsignacionesEntity> registraAsignacion(Asignacion asignacion, DatosUsuario datosUsuarios) {
    	RespuestaAsig<AsignacionesEntity> response = new RespuestaAsig<AsignacionesEntity>();

//        RutasEntity ruta = rutasRepository.getById(asignacion.getIdRuta());
        VehiculosEntity vehiculo = vehiculosRepository.getById(asignacion.getIdVehiculo());
        
                
        try {
            log.info("Creando asignacion");
            AsignacionesEntity asignacionEntity = AsignacionesMapper.INSTANCE.jsonToAsignaEntity(asignacion);
            asignacionEntity.setVehiculo( vehiculo );
            asignacionEntity.setNumAsignacion("CCOM");
            asignacionEntity.setMatricula(datosUsuarios.getMatricula());
            asignacionEntity.setFechaAlta(new Date());
            asignacionEntity.setIndActivo(true);
            asignacionEntity.setIndSistema(true);
            asignacionEntity.setChofer(asignacion.getIdChofer().intValue());
            asignacionEntity.setRuta(asignacion.getIdRuta());
            asignacionesRepository.save(asignacionEntity);
            // Actualización de estatus tarjeta no aplica
            	
//            List<RutasDestinos> rutasDetinos = rutasDestinosRepository.consultaPorRuta( asignacion.getIdRuta() );
//            
//            if( rutasDetinos!=null && rutasDetinos.size()>0 ) {
//            	
//            	for( RutasDestinos rutas : rutasDetinos ) {
//            		
//            		AsignacionesDestinosEntity asignacionDestino = new AsignacionesDestinosEntity();
//            		asignacionDestino.setIdAsignacion( asignacionEntity.getIdAsignacion() );
//            		asignacionDestino.setIdUnidadDestino( rutas.getIdDestino() );
//            		asignacionDestino.setActivo(true);
//                    asignacionDestino.setSistema(false);
//                    asignacionesDestinoRepository.save(asignacionDestino);
//                    
//            	}
//            }else {
//            	
//            	AsignacionesDestinosEntity asignacionDestino = new AsignacionesDestinosEntity();
//        		asignacionDestino.setIdAsignacion( asignacionEntity.getIdAsignacion() );
//        		asignacionDestino.setActivo(true);
//                asignacionDestino.setSistema(false);
//                asignacionesDestinoRepository.save(asignacionDestino);
//                
//            }
            
//            String numBitacora = generaFolioBitacora( datosUsuarios.getIDOOAD() );
//            BitacoraServiciosEntity bitacoraServiciosEntity = new BitacoraServiciosEntity();
//		    bitacoraServiciosEntity.setNumBitacora(numBitacora);
//		    bitacoraServiciosEntity.setFecBitacora(new Date());
//		    bitacoraServiciosEntity.setIdOoad( datosUsuarios.getIDOOAD() );
//			bitacoraServiciosEntity.setAsignacion( asignacionEntity );
//		    bitacoraServiciosEntity.setMatricula(datosUsuarios.getMatricula());
//		    bitacoraServiciosEntity.setFechaAlta(new Date());
//		    bitacoraServiciosEntity.setIndActivo(true);
//		    bitacoraServiciosEntity.setIndSistema(false);
//		    bitacoraRepository.save(bitacoraServiciosEntity);

            response.setCodigo(HttpStatus.OK.value());
            response.setMensaje("Exito");
            response.setError(false);
            Asignacion asignacionResp = AsignacionesMapper.INSTANCE.asignaEntityToJson(asignacionEntity);
            asignacionResp.setIdVehiculo(asignacion.getIdVehiculo());
            response.setDatos(asignacionEntity);

        } catch (Exception exc) {
            log.error("Error en registro de asignacion ", exc.getMessage());
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje("Error en registro de asignacion " + exc.getMessage());
            response.setError(true);
        }

        return response;
    }

   

//    @Transactional(rollbackOn = SQLException.class)
//    @Override
//    public Respuesta<Asignacion> actualizaAsignacion(Asignacion asignacion, String matricula) {
//        Respuesta<Asignacion> response = new Respuesta<Asignacion>();
//
//        try {
//            AsignacionesEntity asignacionEntity = asignacionesRepository.getById(asignacion.getIdAsignacion());
//            RutasEntity ruta = rutasRepository.getById(asignacion.getIdRuta());
//            
//            if( !asignacion.getNumFolioTarjeta().equalsIgnoreCase( asignacionEntity.getNumFolioTarjeta() ) ) {
//            	
//            	 ArrayList<String> lstTarjetasUsadas = asignacionesRepository.findTarjetasByOoad(ruta.getIdOoad()); // Para descartar tarjetas utilizadas
//                 
//                 if( lstTarjetasUsadas!=null && lstTarjetasUsadas.size() > 0 ) {
//                 	 
//                 	 for( String tarjeta : lstTarjetasUsadas ) {
//                 		 if( tarjeta.equalsIgnoreCase( asignacion.getNumFolioTarjeta() ) ) {
//                 			 response.setCodigo(HttpStatus.BAD_REQUEST.value());
//                              response.setMensaje("La tarjeta " +asignacion.getNumFolioTarjeta() + " ya fue asignada.");
//                              response.setError(true);
//                              return response;
//                 		 }
//                 	 }
//                 }
//            	
//            }
//            
//            VehiculosEntity vehiculo = vehiculosRepository.getById(asignacion.getIdVehiculo());
//            
//            if( !asignacion.getIdVehiculo().equals( asignacionEntity.getVehiculo().getIdVehiculo() )  ) {
//            	
//                List<AsignacionesEntity> vehiculos = asignacionesRepository.listaAsignacionesVehiculos( asignacion.getIdVehiculo() );
//                
//                if( vehiculos!=null && vehiculos.size() > 0 ) {
//               	 	response.setCodigo(HttpStatus.BAD_REQUEST.value());
//                    response.setMensaje("El vehiculo " + vehiculo.getCveEcco() + " ya fue asignado.");
//                    response.setError(true);
//                    return response;
//               }
//            }
//            
//            ChoferesEntity chofer = choferesRepository.getById(asignacion.getIdChofer());
//            
//            if( !asignacion.getIdChofer().equals( asignacionEntity.getChofer().getIdChofer() ) ) {
//            	
//                List<AsignacionesEntity> choferes = asignacionesRepository.listaAsignacionesChofer( asignacion.getIdChofer() );
//                
//                if( choferes!=null && choferes.size() > 0 ) {
//               	 	response.setCodigo(HttpStatus.BAD_REQUEST.value());
//                    response.setMensaje("El Chofer " + chofer.getNomChofer() + " ya fue asignado.");
//                    response.setError(true);
//                    return response;
//               }
//            	
//            }
//            
//            asignacionEntity.setVehiculo(vehiculo);
//            asignacionEntity.setDesMarca(asignacion.getDesMarca());
//            asignacionEntity.setNumPlacas(asignacion.getNumPlacas());
//            asignacionEntity.setDesModelo(asignacion.getDesModelo());
//            asignacionEntity.setChofer(chofer);
//            asignacionEntity.setRuta(ruta);
//            asignacionEntity.setNumFolioTarjeta(asignacion.getNumFolioTarjeta());
//            asignacionEntity.setDesEstatus(asignacion.getDesEstatus());
//
//            asignacionEntity.setMatricula(matricula);
//            asignacionEntity.setFechaActualizacion(new Date());
//            asignacionEntity.setIndActivo(true);
//            asignacionEntity.setIndSistema(false);
//            asignacionesRepository.save(asignacionEntity);
//
//            response.setCodigo(HttpStatus.OK.value());
//            response.setMensaje("Exito");
//            response.setError(false);
//            Asignacion asignacionResp = AsignacionesMapper.INSTANCE.asignaEntityToJson(asignacionEntity);
//            asignacionResp.setIdVehiculo(asignacion.getIdVehiculo());
//            asignacionResp.setIdChofer(asignacion.getIdChofer());
//            asignacionResp.setIdRuta(asignacion.getIdRuta());
//            response.setDatos(asignacionResp);
//
//        } catch (Exception exc) {
//            log.error("Error en actualización de asignacion ", exc.getMessage());
//            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            response.setMensaje("Error en actualización de asignacion " + exc.getMessage());
//            response.setError(true);
//        }
//        return response;
//    }

//    @Override
//    public Respuesta<Integer> eliminaAsignacion(Integer idAsignacion, String matricula) {
//        Respuesta<Integer> response = new Respuesta<Integer>();
//
//        try {
//            AsignacionesEntity asignacionEntity = asignacionesRepository.getById(idAsignacion);
//            asignacionEntity.setMatricula(matricula);
//            asignacionEntity.setFechaBaja(new Date());
//            asignacionEntity.setIndActivo(false);
//            asignacionesRepository.save(asignacionEntity);
//
//            response.setCodigo(HttpStatus.OK.value());
//            response.setMensaje("Exito");
//            response.setError(false);
//            response.setDatos(idAsignacion);
//
//        } catch (Exception exc) {
//            log.error("Error en eliminación de asignacion ", exc.getMessage());
//            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            response.setMensaje("Error en eliminación de asignacion " + exc.getMessage());
//            response.setError(true);
//        }
//        return response;
//    }





	@Override
	public RespuestaAsig<Asignacion> actualizaAsignacion(Asignacion asignacion, String matricula) {
		// TODO Auto-generated method stub
		return null;
	}
}
