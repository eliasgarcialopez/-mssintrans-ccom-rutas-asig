package mx.gob.imss.mssistrans.shared.service.impl;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.gob.imss.mssistrans.asignaciones.dto.BitacoraServicio;
import mx.gob.imss.mssistrans.asignaciones.dto.Respuesta;
import mx.gob.imss.mssistrans.asignaciones.model.BitacoraServiciosEntity;
import mx.gob.imss.mssistrans.asignaciones.model.PrestamosEntity;
import mx.gob.imss.mssistrans.asignaciones.repository.AsignacionesRepository;
import mx.gob.imss.mssistrans.asignaciones.repository.BitacoraServiciosRepository;
import mx.gob.imss.mssistrans.asignaciones.repository.PrestamosRepository;
import mx.gob.imss.mssistrans.shared.service.BitacoraService;
import mx.gob.imss.mssistrans.shared.util.ReporteUtil;
import net.sf.jasperreports.engine.JasperReport;

/**
 * Servicio para la Bitacora de servicios
 * @author gsz
 */
@Service
@AllArgsConstructor
@Slf4j
public class BitacoraServiceImpl implements BitacoraService {
	
	private final BitacoraServiciosRepository bitacoraRepository;
	
	private final AsignacionesRepository asignacionesRepository;
	
	private final PrestamosRepository prestamosRepository;

	@Override
	public Respuesta<byte[]> generaBitacora(BitacoraServicio bitacoraServicio) {
		Map<String, Object> parameters = new HashMap<>();
        Respuesta<byte[]> response = new Respuesta<>();
        byte[] filePdf = new byte[0];
		String numBitacora = getSigBitacora(bitacoraServicio.getIdOoad());
		
		try {
			log.info("Creando bitácora");
			BitacoraServiciosEntity bitacoraServiciosEntity = new BitacoraServiciosEntity();
			bitacoraServiciosEntity.setNumBitacora(numBitacora);
			bitacoraServiciosEntity.setFecBitacora(new Date());
			bitacoraServiciosEntity.setIdOoad(bitacoraServicio.getIdOoad());
			if (bitacoraServicio.getTipoMov().equals("P")) {
				PrestamosEntity prestamo = prestamosRepository.getById(bitacoraServicio.getId());
				bitacoraServiciosEntity.setPrestamo(prestamo);
			} else {
				bitacoraServiciosEntity.setAsignacion(asignacionesRepository.getById(bitacoraServicio.getId()));
			}
			bitacoraServiciosEntity.setMatricula(bitacoraServicio.getMatricula());
			bitacoraServiciosEntity.setFechaAlta(new Date());
			bitacoraServiciosEntity.setIndActivo(true);
			bitacoraServiciosEntity.setIndSistema(true);
			bitacoraRepository.save(bitacoraServiciosEntity);
			
			log.info("Armando reporte");
			parameters.put("numBitacora", numBitacora);
			parameters.put("fecBitacora", bitacoraServicio.getFechaBitacora());
			if (bitacoraServicio.getTipoMov().equals("P")) {
			    parameters.put("desInmueble", bitacoraServiciosEntity.getPrestamo().getVehiculo().getUnidad().getNomUnidadAdscripcion());
			    parameters.put("desMarca", bitacoraServiciosEntity.getPrestamo().getVehiculo().getDesMarca());
			    parameters.put("desTipo", bitacoraServiciosEntity.getPrestamo().getVehiculo().getDesTipoVehiculo());
			    parameters.put("desModelo", bitacoraServiciosEntity.getPrestamo().getVehiculo().getDesModelo());
			    parameters.put("nomUnidadAdscripcion", bitacoraServiciosEntity.getPrestamo().getVehiculo().getUnidad().getNomUnidadAdscripcion());
			    parameters.put("desServicio", "");
			    parameters.put("numRuta", "");
			} else {
				parameters.put("desInmueble", bitacoraServiciosEntity.getAsignacion().getVehiculo().getUnidad().getNomUnidadAdscripcion());
				parameters.put("desMarca", bitacoraServiciosEntity.getAsignacion().getDesMarca());
				parameters.put("desTipo", bitacoraServiciosEntity.getAsignacion().getVehiculo().getDesTipoVehiculo());
				parameters.put("desModelo", bitacoraServiciosEntity.getAsignacion().getDesModelo());
				parameters.put("nomUnidadAdscripcion", bitacoraServiciosEntity.getAsignacion().getVehiculo().getUnidad().getNomUnidadAdscripcion());
				parameters.put("desServicio", bitacoraServiciosEntity.getAsignacion().getRuta().getDesServicio());
				parameters.put("numRuta", bitacoraServiciosEntity.getAsignacion().getRuta().getNumRuta());
			}
			//InputStream reportStream = new ClassPathResource("reportes/formato-bitacora-servicios.jrxml").getInputStream();
            InputStream reportStream = ReporteUtil.recuperarInputStream.apply("reportes/formato-bitacora-servicios.jrxml");
            //JasperReport report = JasperCompileManager.compileReport(reportStream);
            JasperReport report = ReporteUtil.recuperarJasperReport.apply(reportStream);
            reportStream.close();

            //filePdf = JasperRunManager.runReportToPdf(report, parameters, new JREmptyDataSource());
            filePdf = ReporteUtil.generarPdf.apply(report, parameters);
			
			response.setDatos(filePdf);
			response.setCodigo(HttpStatus.OK.value());
            response.setMensaje("Exito");
            response.setError(false);
		} catch (Exception exc) {
            log.error("Error al generar formato de bitacora ", exc.getMessage());
            response.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensaje("Error al generar formato de bitacora " + exc.getMessage());
            response.setError(true);
		}
		
		return response;
		
	}
	
	private String getSigBitacora(Integer idOoad) {
		String mes = String.format("%02d",Calendar.getInstance().get(Calendar.MONTH)+1);
		String ooad = String.format("%02d",idOoad);
		String ultimoFolio = bitacoraRepository.findUltimoFolioByMesOoad(idOoad, mes);
		if (ultimoFolio == null) {
			ultimoFolio = "0000";
		}
		
		return mes + '-' + ooad + '-' + String.format("%04d",Integer.parseInt(ultimoFolio) + 1);
	}

}
