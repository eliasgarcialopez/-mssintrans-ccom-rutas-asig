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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosUsuarioDTO;
import mx.gob.imss.mssistrans.ccom.rutas.dto.RegistroRecorridoDTO;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;
import mx.gob.imss.mssistrans.ccom.rutas.service.impl.AsigRutasServiceImpl;
import mx.gob.imss.mssistrans.ccom.rutas.util.ValidaDatos;

import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author opimentel
 *
 */
@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
@RestController
@RequestMapping("/RutasAsignacionesCCOM")
@CrossOrigin(methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class RutasAsignacionesCCOMController {

	@Autowired
	AsigRutasServiceImpl asigRutasServiceImpl;

	@GetMapping
	public <T> ResponseEntity<Response> consultaGeneral(@RequestParam(defaultValue = "0") Integer pagina,
			@RequestParam(defaultValue = "10") Integer tamanio, @RequestParam(defaultValue = "") String ordenCol,
			@RequestParam(defaultValue = "ASC") String orden, @RequestParam(required = false) String idRutaAsig,
			@RequestParam(required = false) String idSolicitud) {

		Response<T> respuesta = new Response<>();
		if (ValidaDatos.getAccess()) {
			respuesta = ValidaDatos.noAutorizado(respuesta);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} else {
			Response response = asigRutasServiceImpl.consultaVistaRapida(pagina, tamanio, orden, ordenCol, idRutaAsig,
					idSolicitud);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@DeleteMapping(path = "{idControlRuta}")
	public <T> ResponseEntity<Response> delete(@PathVariable String idControlRuta) {
		Response<T> respuesta = new Response<>();
		if (ValidaDatos.getAccess()) {
			respuesta = ValidaDatos.noAutorizado(respuesta);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} else {
			Response response = asigRutasServiceImpl.delete(idControlRuta);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	/****** HU006 - 26 **********/
	@GetMapping(path = "/getRutas")
	public <T> ResponseEntity<Response> obtenerRuta() {

		Response<T> respuesta = new Response<>();
		if (ValidaDatos.getAccess()) {
			respuesta = ValidaDatos.noAutorizado(respuesta);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} else {
			DatosUsuarioDTO datosUsuario = ValidaDatos.datosUsuarios();
			Response response = asigRutasServiceImpl.getRutas(datosUsuario.getIDOOAD(), datosUsuario.getRol());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/getSolTraslado/{idRuta}")
	public <T> ResponseEntity<Response> getSolicitudTraslado(@PathVariable Integer idRuta) {

		Response<T> respuesta = new Response<>();
		if (ValidaDatos.getAccess()) {
			respuesta = ValidaDatos.noAutorizado(respuesta);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} else {
			DatosUsuarioDTO datosUsuario = ValidaDatos.datosUsuarios();
			Response response = asigRutasServiceImpl.getSolicitudTraslado(datosUsuario, idRuta);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/ecco/{idRuta}")
	public <T> ResponseEntity<Response> getEcco(@PathVariable Integer idRuta) {
		Response<T> respuesta = new Response<>();
		if (ValidaDatos.getAccess()) {
			respuesta = ValidaDatos.noAutorizado(respuesta);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} else {
			DatosUsuarioDTO datosUsuario = ValidaDatos.datosUsuarios();
			Response response = asigRutasServiceImpl.getEcco(datosUsuario, idRuta);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/getDatosAsignacion")
	public <T> ResponseEntity<Response> getDatosAsignacion(@RequestParam(required = false) Integer idControlRuta
			, @RequestParam(required = false) Integer idRuta, @RequestParam(required = false) Integer idSolicitud
			, @RequestParam(required = false) Integer idVehiculo) {

		Response<T> respuesta = new Response<>();
		if (ValidaDatos.getAccess()) {
			respuesta = ValidaDatos.noAutorizado(respuesta);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} else {
			DatosUsuarioDTO datosUsuarios = ValidaDatos.datosUsuarios();
			Response response = asigRutasServiceImpl.getDatosAsignacion(idControlRuta, idRuta, idSolicitud, idVehiculo);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/getTripulacionAsignada")
	public <T> ResponseEntity<Response> getTripulacionAsignada(@RequestParam(required = false) Integer idControlRuta
			, @RequestParam(required = false) Integer idRuta, @RequestParam(required = false) Integer idSolicitud
			, @RequestParam(required = false) Integer idVehiculo)  {

		Response<T> respuesta = new Response<>();
		if (ValidaDatos.getAccess()) {
			respuesta = ValidaDatos.noAutorizado(respuesta);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} else {
			Response response = asigRutasServiceImpl.getTripulacionAsignada(idControlRuta, idRuta, idSolicitud, idVehiculo);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/getRegRecorrido")
	public <T> ResponseEntity<Response> getRegRecorrido(@RequestParam(required = false) Integer idControlRuta
			, @RequestParam(required = false) Integer idRuta, @RequestParam(required = false) Integer idSolicitud
			, @RequestParam(required = false) Integer idVehiculo) {

		Response<T> respuesta = new Response<>();
		if (ValidaDatos.getAccess()) {
			respuesta = ValidaDatos.noAutorizado(respuesta);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} else {
			Response response = asigRutasServiceImpl.getRegistroRecorrido(idControlRuta, idRuta, idSolicitud, idVehiculo);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@PutMapping(path = "/")
	public <T> ResponseEntity<Response> update(@RequestBody RegistroRecorridoDTO datosRecorrido) {

		Response<T> respuesta = new Response<>();
		if (ValidaDatos.getAccess()) {
			respuesta = ValidaDatos.noAutorizado(respuesta);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} else {
			Response response = asigRutasServiceImpl.update(datosRecorrido);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/getDetalleRutasyAsig")
	public <T> ResponseEntity<Response> getDetalleRutasyAsig(@RequestParam Integer idControlRuta) {

		Response<T> respuesta = new Response<>();
		if (ValidaDatos.getAccess()) {
			respuesta = ValidaDatos.noAutorizado(respuesta);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} else {
			DatosUsuarioDTO datosUsuarios = ValidaDatos.datosUsuarios();
			Response response = asigRutasServiceImpl.getDetalleRutasyAsig(idControlRuta);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

}
