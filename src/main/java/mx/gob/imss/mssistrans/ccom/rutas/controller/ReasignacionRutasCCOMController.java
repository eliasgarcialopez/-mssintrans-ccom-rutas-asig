package mx.gob.imss.mssistrans.ccom.rutas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosUsuarioDTO;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;
import mx.gob.imss.mssistrans.ccom.rutas.service.impl.ReasignacionRutasServiceImpl;
import mx.gob.imss.mssistrans.ccom.rutas.util.ValidaDatos;

import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @author opimentel
 *
 */
@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
@RestController
@RequestMapping("/ReasignacionRutasCCOM")
@CrossOrigin(methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class ReasignacionRutasCCOMController {

	@Autowired
	ReasignacionRutasServiceImpl reasignacionRutasServiceImpl;

	@GetMapping
	public <T> ResponseEntity<Response> consultarRutasAsignaciones(@RequestParam Integer pagina,
			@RequestParam(defaultValue = "10") Integer tamanio, @RequestParam(defaultValue = "asc") String orden,
			@RequestParam(defaultValue = "idEstatusSolicitud") String ordenCol) {
		Response<T> respuesta = new Response<>();
		if (ValidaDatos.getAccess()) {
			respuesta = ValidaDatos.noAutorizado(respuesta);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} else {
			respuesta = reasignacionRutasServiceImpl.consultaGeneral(pagina, tamanio, orden, ordenCol);

			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/consultaById")
	public <T> ResponseEntity<Response> consultaById(@RequestParam Integer pagina,
			@RequestParam(defaultValue = "10") Integer tamanio, @RequestParam String idRutaAsig,
			@RequestParam String idSolicitud, @RequestParam(defaultValue = "asc") String orden,
			@RequestParam(defaultValue = "idEstatusSolicitud") String ordenCol) {

		Response<T> respuesta = new Response<>();
		if (ValidaDatos.getAccess()) {
			respuesta = ValidaDatos.noAutorizado(respuesta);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} else {
			Pageable pageable = PageRequest.of(pagina, tamanio);
			Response response = reasignacionRutasServiceImpl.consultaById(pagina, tamanio, idRutaAsig, idSolicitud);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@DeleteMapping(path = "{idRutaAsignacion}")
	public <T> ResponseEntity<Response> delete(@PathVariable String idRutaAsignacion) {
		Response<T> respuesta = new Response<>();
		if (ValidaDatos.getAccess()) {
			respuesta = ValidaDatos.noAutorizado(respuesta);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} else {
			Response response = reasignacionRutasServiceImpl.delete(idRutaAsignacion);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	/****** HU006 - 28 **********/
	

	@GetMapping(path = "/getDatosReAsignacion")
	public <T> ResponseEntity<Response> getDatosAsignacion(@RequestParam Integer idVehiculo,
			@RequestParam Integer idRuta, @RequestParam Integer idSolicitud) {

		Response<T> respuesta = new Response<>();
		if (ValidaDatos.getAccess()) {
			respuesta = ValidaDatos.noAutorizado(respuesta);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} else {
			DatosUsuarioDTO datosUsuarios = ValidaDatos.datosUsuarios();
			Response response = reasignacionRutasServiceImpl.getDatosAsignacion(idVehiculo, idRuta,	idSolicitud);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/getTripulacionAsignada")
	public <T> ResponseEntity<Response> getTripulacionAsignada(@RequestParam Integer idVehiculo,
			@RequestParam Integer idRuta, @RequestParam Integer idSolicitud) {

		Response<T> respuesta = new Response<>();
		if (ValidaDatos.getAccess()) {
			respuesta = ValidaDatos.noAutorizado(respuesta);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} else {
			Response response = reasignacionRutasServiceImpl.getTripulacionAsignada(idRuta, idVehiculo, idSolicitud);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/getIncidentes")
	public <T> ResponseEntity<Response> getSiniestro() {

		Response<T> respuesta = new Response<>();
		if (ValidaDatos.getAccess()) {
			respuesta = ValidaDatos.noAutorizado(respuesta);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} else {
			Response response = reasignacionRutasServiceImpl.getSiniestro();
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	

	@PutMapping(path = "{idVehiculo}")
	public ResponseEntity<Response> update ( @PathVariable String idVehiculo, @RequestParam String idNuevoVehiculo
			, @RequestParam String idRuta, @RequestParam String idNuevaRuta, @RequestParam String idSolicitud
			, @RequestParam String idNuevaSolicitud, @RequestParam String desEstatus ){
		Response response = reasignacionRutasServiceImpl.update(idVehiculo, idNuevoVehiculo, idRuta, idNuevaRuta, idSolicitud, idNuevaSolicitud, desEstatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
