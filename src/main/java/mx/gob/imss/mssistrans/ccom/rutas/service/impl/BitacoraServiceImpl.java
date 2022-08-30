package mx.gob.imss.mssistrans.ccom.rutas.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosBitacora;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Respuesta;
import mx.gob.imss.mssistrans.ccom.rutas.model.BitacoraServiciosEntity;
import mx.gob.imss.mssistrans.ccom.rutas.model.ControlRutas;
import mx.gob.imss.mssistrans.ccom.rutas.service.BitacoraService;
import mx.gob.imss.mssistrans.ccom.rutas.util.ReporteUtil;
import mx.gob.imss.mssistrans.ccom.rutas.util.TipoServicioEnum;
import mx.gob.imss.mssistrans.ccom.rutas.util.TipoVehEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.ccom.rutas.repository.ControlRutasRepository;
import net.sf.jasperreports.engine.JasperReport;
import mx.gob.imss.mssistrans.ccom.rutas.repository.BitacoraServiciosRepository;

/**
 * Servicio para la Bitacora de servicios
 *
 * @author gsz
 */
@Service
@Slf4j
public class BitacoraServiceImpl implements BitacoraService {
	
	@Autowired
	private ControlRutasRepository controlRutasRepository;
	
	@Autowired
	private BitacoraServiciosRepository bitacoraRepository;
	
	@Override
	public Respuesta<DatosBitacora> buscaVehiculo(String ecco, Integer idOoad) {
		Respuesta<DatosBitacora> response = new Respuesta<DatosBitacora>();

        try {
        	List<ControlRutas> lstControlRutasEntity;
        	if (idOoad > 0) {
                lstControlRutasEntity = controlRutasRepository.findVehiculoByEcco(ecco, idOoad);
        	}
            else {
            	lstControlRutasEntity = controlRutasRepository.findVehiculoByEccoAdm(ecco);
            }
            if (lstControlRutasEntity.size() > 0) {
            	ControlRutas controlRutaEntity = lstControlRutasEntity.get(0);
                DatosBitacora datosBitacora = new DatosBitacora();
                datosBitacora.setIdVehiculo(controlRutaEntity.getIdVehiculo().getIdVehiculo());
                datosBitacora.setCveEcco(controlRutaEntity.getIdVehiculo().getCveEcco());
                datosBitacora.setDesMarca(controlRutaEntity.getIdVehiculo().getDesMarca());
                datosBitacora.setDesTipoVeh(controlRutaEntity.getIdVehiculo().getDesTipoVehiculo());
                datosBitacora.setNumPlacas(controlRutaEntity.getIdVehiculo().getNumPlacas());
                datosBitacora.setDesModelo(controlRutaEntity.getIdVehiculo().getDesModelo());
                datosBitacora.setNomModuloAmb(controlRutaEntity.getModulo().getDesNombre());
                datosBitacora.setIdTripulacion(controlRutaEntity.getTripulacion().getIdTripulacion());
                datosBitacora.setCveMatriculaChofer(controlRutaEntity.getTripulacion().getPersonalChofer().getChofer().getMatriculaChofer());
                datosBitacora.setCveMatriculaCamillero1(controlRutaEntity.getTripulacion().getPersonalCamillero1().getCamillero().getCveMatricula());
                datosBitacora.setCveMatriculaCamillero2(controlRutaEntity.getTripulacion().getPersonalCamillero2().getCamillero().getCveMatricula());
                datosBitacora.setDesTipoServicio(controlRutaEntity.getRuta().getDesServicio());
                datosBitacora.setNumRuta(controlRutaEntity.getRuta().getNumFolioRuta());
                datosBitacora.setIdControlRuta(controlRutaEntity.getIdControlRuta());
                datosBitacora.setIdOoad(idOoad);
                List<BitacoraServiciosEntity> lstBitacoraServiciosEntity = bitacoraRepository.findBitacoraByCr(controlRutaEntity.getIdControlRuta());
                if (lstBitacoraServiciosEntity.size() > 0) {
                	datosBitacora.setNumBitacora(lstBitacoraServiciosEntity.get(0).getNumBitacora());
                	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            		String fechaBit = sdf.format(lstBitacoraServiciosEntity.get(0).getFecBitacora());
                	datosBitacora.setFecBitacora(fechaBit);
                }
                response.setDatos(datosBitacora);
            } else {
                response.setDatos(null);
            }
            response.setCodigo(HttpStatus.OK.value());
            response.setMensaje("Exito");
            response.setError(false);
        } catch (Exception exc) {
        	exc.printStackTrace();
            log.error("Error al consultar vehiculo por id, {}", exc.getMessage());
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje("Error al consultar vehiculo por id " + exc.getMessage());
            response.setError(true);
        }

        return response;
	}

	@Override
	public Respuesta<byte[]> generaBitacora(Integer idOoad, Integer idControlRuta, String fechaResg, String matricula) throws IOException {
		Map<String, Object> parameters = new HashMap<>();
        Respuesta<byte[]> response = new Respuesta<>();
		String numBitacora = getSigBitacora(idOoad);
		try {
			log.info("Creando bitácora");
			List<BitacoraServiciosEntity> lstBitacoraServiciosEntity = bitacoraRepository.findBitacoraByCr(idControlRuta);
			BitacoraServiciosEntity bitacoraServiciosEntity = null;
			if (lstBitacoraServiciosEntity.size() == 0) {
				bitacoraServiciosEntity = new BitacoraServiciosEntity();
			    bitacoraServiciosEntity.setNumBitacora(numBitacora);
			    bitacoraServiciosEntity.setFecBitacora(new Date());
			    bitacoraServiciosEntity.setIdOoad(idOoad);
			    bitacoraServiciosEntity.setControlRuta(controlRutasRepository.getById(idControlRuta));
			    bitacoraServiciosEntity.setMatricula(matricula);
			    bitacoraServiciosEntity.setFechaAlta(new Date());
			    bitacoraServiciosEntity.setIndActivo(true);
			    bitacoraServiciosEntity.setIndSistema(true);
			    bitacoraRepository.save(bitacoraServiciosEntity);
			} else {
				bitacoraServiciosEntity = lstBitacoraServiciosEntity.get(0);
			}
			
			log.info("Armando reporte");
			parameters.put("numBitacora", numBitacora);
			parameters.put("fecha", fechaResg);
			parameters.put("inmueble", bitacoraServiciosEntity.getControlRuta().getIdVehiculo().getUnidad().getNomUnidadAdscripcion());
			parameters.put("marca", bitacoraServiciosEntity.getControlRuta().getIdVehiculo().getDesMarca());
			parameters.put("placas", bitacoraServiciosEntity.getControlRuta().getIdVehiculo().getNumPlacas());
			parameters.put("tipo", recuperaTipoVeh(bitacoraServiciosEntity.getControlRuta().getIdVehiculo().getDesTipoVehiculo()));
			parameters.put("modelo", bitacoraServiciosEntity.getControlRuta().getIdVehiculo().getDesModelo().toString());
			parameters.put("unidadAdscripcion", bitacoraServiciosEntity.getControlRuta().getIdVehiculo().getUnidad().getNomUnidadAdscripcion());
			parameters.put("tripulacion", bitacoraServiciosEntity.getControlRuta().getTripulacion().getIdTripulacion().toString());
			parameters.put("chofer", bitacoraServiciosEntity.getControlRuta().getTripulacion().getPersonalChofer().getChofer().getNombreChofer());
			parameters.put("camillero1", bitacoraServiciosEntity.getControlRuta().getTripulacion().getPersonalCamillero1().getCamillero().getNomCamillero());
			parameters.put("camillero2", bitacoraServiciosEntity.getControlRuta().getTripulacion().getPersonalCamillero2().getCamillero().getNomCamillero());
			parameters.put("tipoServicio", recuperaTipoServicio(bitacoraServiciosEntity.getControlRuta().getRuta().getDesServicio()));
			parameters.put("idRuta", bitacoraServiciosEntity.getControlRuta().getRuta().getNumFolioRuta());
			InputStream reportStream = ReporteUtil.recuperarInputStream.apply("reportes/formato-bitacora-servicios-ccom.jrxml");
            JasperReport report = ReporteUtil.recuperarJasperReport.apply(reportStream);
            reportStream.close();
			byte[] filePdf = ReporteUtil.generarPdf.apply(report, parameters);
			
			response.setDatos(filePdf);
			response.setCodigo(HttpStatus.OK.value());
            response.setMensaje("Exito");
            response.setError(false);
			
		} catch (Exception exc) {
			exc.printStackTrace();
            log.error("Error al generar formato de bitacora, {}", exc.getMessage());
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje("Error al generar formato de bitacora " + exc.getMessage());
            response.setError(true);
	    }
		
		return response;
	}
	
	private String getSigBitacora(Integer idOoad) {
        String mes = String.format("%02d", Calendar.getInstance().get(Calendar.MONTH) + 1);
        String ooad = String.format("%02d", idOoad);
        String ultimoFolio = bitacoraRepository.findUltimoFolioByMesOoad(idOoad, mes);
        if (ultimoFolio == null) {
            ultimoFolio = "0000";
        }

        return mes + '-' + ooad + '-' + String.format("%04d", Integer.parseInt(ultimoFolio) + 1);
    }
	
	private static String recuperaTipoVeh(String tipoVehId) throws Exception {
		return Arrays.stream(TipoVehEnum.values()).filter(tipoVehValue -> tipoVehValue.getTipoVeh() == Integer.parseInt(tipoVehId))
				 .map(Enum::name)
	                .findFirst()
	                .orElseThrow(() -> new Exception("No se ha encontrado el tipo vehiculo en la lista"));
	}
	
	private static String recuperaTipoServicio(String tipoServicioId) throws Exception {
		return Arrays.stream(TipoServicioEnum.values()).filter(tipoServicioValue -> tipoServicioValue.getTipoServicio() == Integer.parseInt(tipoServicioId))
				 .map(Enum::name)
	                .findFirst()
	                .orElseThrow(() -> new Exception("No se ha encontrado el tipo vehiculo en la lista"));
	}

}
