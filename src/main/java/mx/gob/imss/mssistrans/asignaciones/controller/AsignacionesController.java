package mx.gob.imss.mssistrans.asignaciones.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import mx.gob.imss.mssistrans.asignaciones.dto.*;
import mx.gob.imss.mssistrans.asignaciones.service.AsignacionesService;
import mx.gob.imss.mssistrans.shared.service.BitacoraService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/asignaciones")
@CrossOrigin(methods = { RequestMethod.GET, RequestMethod.POST })
public class AsignacionesController {

	private AsignacionesService asignacionesService;
	
	private BitacoraService bitacoraService;
	
	/**
	 * Endpoint para consulta general de asignaciones
	 * @param idOoad
	 * @return
	 */
	@GetMapping("/get-general/{idOoad}")
	public ResponseEntity consultaGeneralAsignaciones(@RequestParam Integer pagina, @RequestParam(defaultValue = "10") Integer tamanio,
			@RequestParam(defaultValue = "1") String orden, @PathVariable Integer idOoad) {
		RespuestaTO response = asignacionesService.busquedaAsignacionesByOoad(pagina, tamanio, orden, idOoad);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
  	/**
	 * Endpoint para consultar asignaciones por folio
	 * @param numAsignacion, idOoad
	 * @return
	 */
	@GetMapping("/get-folio/{numAsignacion}/{idOoad}")
	public ResponseEntity<?> consultaAsignacionesPorNum(@PathVariable String numAsignacion, @PathVariable Integer idOoad) {
		Respuesta<List<DatosAsignacion>> response = asignacionesService.busquedaAsignacionesByNum(numAsignacion, idOoad);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Endpoint para consultar vehiculos asignables
	 * @param idOoad
	 * @return
	 */
	@GetMapping("/get-vehiculos/{idOoad}")
	public ResponseEntity<?> consultaVehiculos(@PathVariable Integer idOoad) {
		Respuesta<List<DatosVehiculo>> response = asignacionesService.busquedaVehiculosAsignables(idOoad);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Endpoint para consultar choferes
	 * @param idOoad
	 * @return
	 */
	@GetMapping("/get-choferes/{idOoad}")
	public ResponseEntity<?> consultaChoferes(@PathVariable Integer idOoad) {
		Respuesta<List<CatalogoGenerico>> response = asignacionesService.busquedaChoferes(idOoad);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Endpoint para consultar rutas
	 * @param idOoad
	 * @return
	 */
	@GetMapping("/get-rutas/{idOoad}")
	public ResponseEntity<?> consultaRutas(@PathVariable Integer idOoad) {
		Respuesta<List<CatalogoGenerico>> response = asignacionesService.busquedaRutas(idOoad);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Endpoint para consultar tarjetas digitales
	 * @param idOoad
	 * @return
	 */
	@GetMapping("/get-tarjetas/{idOoad}")
	public ResponseEntity<?> consultaTarjetas(@PathVariable Integer idOoad) {
		Respuesta<List<CatalogoGenerico>> response = asignacionesService.busquedaTarjetasDigitales(idOoad);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Endpoint para crear una asignacion
	 * @param asignacion
	 * @return
	 */
	@PostMapping(path = "/save/", consumes = "application/json" )
	public ResponseEntity<Respuesta<?>> crearAsignacion(@RequestBody Asignacion asignacion) {
		Respuesta<?> response = asignacionesService.registraAsignacion(asignacion);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Endpoint para obtener asignacion por id
	 * @param idAsignacion
	 * @return
	 */
	@GetMapping("/get-id/{idAsignacion}")
	public ResponseEntity<?> consultaAsignacion(@PathVariable Integer idAsignacion) {
		Respuesta<Asignacion> response = asignacionesService.getAsignacionById(idAsignacion);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Endpoint para actualizar una asignacion
	 * @param asignacion
	 * @return
	 */
	@PutMapping(path = "/update/", consumes = "application/json" )
	public ResponseEntity<Respuesta<?>> actualizaAsignacion(@RequestBody Asignacion asignacion) {
		Respuesta<?> response = asignacionesService.actualizaAsignacion(asignacion);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Endpoint para borrar asignacion por id
	 * @param idAsignacion
	 * @return
	 */
	@DeleteMapping("/delete/{idAsignacion}")
	public ResponseEntity<?> eliminaAsignacion(@PathVariable Integer idAsignacion) {
		Respuesta<Integer> response = asignacionesService.eliminaAsignacion(idAsignacion);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Endpoint para generar la bitacora de servicios
     * @param idOoad, tipo, id
     * @return
     */
    @PostMapping(path = "/generar-bitacora/", consumes = "application/json" )
    public ResponseEntity<?> bitacoraServicio(@RequestBody BitacoraServicio bitacoraServicio) {
        Respuesta<byte[]> response = bitacoraService.generaBitacora(bitacoraServicio);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=formato-bitacora-servicio.pdf")
                .body(response.getDatos());
    }
	
}
