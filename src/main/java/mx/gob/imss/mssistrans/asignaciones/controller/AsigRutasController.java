package mx.gob.imss.mssistrans.asignaciones.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import org.springframework.web.bind.annotation.RequestMethod;

import mx.gob.imss.mssistrans.asignaciones.rutas.dto.DatosUsuarioDTO;
import mx.gob.imss.mssistrans.asignaciones.rutas.dto.Response;
import mx.gob.imss.mssistrans.asignaciones.rutas.service.impl.AsigRutasServiceImpl;
import mx.gob.imss.mssistrans.asignaciones.rutas.util.ValidaDatos;

@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
@RestController
@RequestMapping("/asigRutas")
@CrossOrigin(methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class AsigRutasController {

	@Autowired
	AsigRutasServiceImpl asigRutasServiceImpl;

	@GetMapping
	public <T> ResponseEntity<Response> consultarAsignacionRutas(@RequestParam Integer pagina,
			@RequestParam(defaultValue = "10") Integer tamanio, @RequestParam(defaultValue = "asc") String orden,
			@RequestParam(defaultValue = "idEstatusSolicitud") String ordenCol) {
		Response<T> respuesta = new Response<>();
		if (ValidaDatos.getAccess()) {
			respuesta = ValidaDatos.noAutorizado(respuesta);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} else {
			respuesta = asigRutasServiceImpl.consultaGeneral(pagina, tamanio, orden, ordenCol);

			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/consultaById")
	public <T> ResponseEntity<Response> consultaById(@RequestParam Integer pagina,
			@RequestParam(defaultValue = "10") Integer tamanio, @RequestParam String idAsignacion,
			@RequestParam String idSolicitud, @RequestParam(defaultValue = "asc") String orden,
			@RequestParam(defaultValue = "idEstatusSolicitud") String ordenCol) {

		Response<T> respuesta = new Response<>();
		if (ValidaDatos.getAccess()) {
			respuesta = ValidaDatos.noAutorizado(respuesta);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} else {
			Pageable pageable = PageRequest.of(pagina, tamanio);

			Response response = asigRutasServiceImpl.consultaById(pagina, tamanio, idAsignacion, idSolicitud);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@DeleteMapping(path = "{idAsignacion}")
	public <T> ResponseEntity<Response> delete(@PathVariable String idAsignacion) {
		Response<T> respuesta = new Response<>();
		if (ValidaDatos.getAccess()) {
			respuesta = ValidaDatos.noAutorizado(respuesta);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} else {
			Response response = asigRutasServiceImpl.delete(idAsignacion);
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
	        DatosUsuarioDTO datosUsuarios = ValidaDatos.datosUsuarios();
			Response response = asigRutasServiceImpl.getRutas(datosUsuarios.getIDOOAD());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/getSolTraslado/{idRuta}")
	public <T> ResponseEntity<Response> getSolicitudTraslado(@PathVariable String idRuta) {

		Response<T> respuesta = new Response<>();
		if (ValidaDatos.getAccess()) {
			respuesta = ValidaDatos.noAutorizado(respuesta);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} else {
			Response response = asigRutasServiceImpl.getSolicitudTraslado(idRuta);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	
	@GetMapping(path = "/getDatosAsignacion/{idVehiculo}")
	public <T> ResponseEntity<Response> getDatosAsignacion(@PathVariable String idVehiculo) {

		Response<T> respuesta = new Response<>();
		if (ValidaDatos.getAccess()) {
			respuesta = ValidaDatos.noAutorizado(respuesta);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} else {
	        DatosUsuarioDTO datosUsuarios = ValidaDatos.datosUsuarios();
			Response response = asigRutasServiceImpl.getDatosAsignacion(idVehiculo, datosUsuarios.getIDOOAD());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/getTripulacionAsignada/{idVehiculo}")
	public <T> ResponseEntity<Response> getTripulacionAsignada(@PathVariable String idVehiculo) {

		Response<T> respuesta = new Response<>();
		if (ValidaDatos.getAccess()) {
			respuesta = ValidaDatos.noAutorizado(respuesta);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} else {
			Response response = asigRutasServiceImpl.getTripulacionAsignada(idVehiculo, 1);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
}
