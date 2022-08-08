package mx.gob.imss.mssistrans.reasignacion.rutas.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import mx.gob.imss.mssistrans.reasignacion.rutas.dto.CatalogoGenerico;
import mx.gob.imss.mssistrans.reasignacion.rutas.dto.DatosVehiculo;
import mx.gob.imss.mssistrans.reasignacion.rutas.dto.ReasignacionRutaRequestDTO;
import mx.gob.imss.mssistrans.reasignacion.rutas.dto.Respuesta;
import mx.gob.imss.mssistrans.reasignacion.rutas.service.impl.ReasignacionRutaServiceImpl;

/**
 * @author Brayan Ramiro Quezada Hernandez
 *
 */
@RestController
@RequestMapping("/reasignaciones")
@CrossOrigin(methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class ReasignacionRutasController {

	@Autowired
	private ReasignacionRutaServiceImpl serviceImpl;
	
	@GetMapping
	public ResponseEntity<?> consultarReasignacion(
			@RequestParam(defaultValue = "0") Integer pagina,
			@RequestParam(defaultValue = "10") Integer tamanio,
			@RequestParam(value = "sort", defaultValue = "ecco,desc") String sort,
			@RequestParam String columna) throws Exception {
		Pageable pageable = 
				PageRequest.of(pagina, tamanio, Sort.by(
						new Sort.Order(Sort.Direction.fromString(sort), columna)));
		Respuesta<?> response = serviceImpl.get(pageable);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Respuesta<?>> save(@RequestBody ReasignacionRutaRequestDTO request) throws Exception {
		Respuesta<?> response = serviceImpl.save(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> consultaId(@PathVariable int id) throws Exception {
		Respuesta<?> response = serviceImpl.consultaId(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "{id}")
	public ResponseEntity<Respuesta> delete(@PathVariable int id) throws Exception {
		Respuesta response = serviceImpl.delete(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Integer id,@RequestBody ReasignacionRutaRequestDTO request) throws Exception {
		Respuesta<?> response = serviceImpl.actualizar(id, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Endpoint para consultar choferes
	 * @param idOoad
	 * @return
	 */
	@GetMapping("/choferes")
	public ResponseEntity<?> consultaChoferes() {
		Respuesta<List<CatalogoGenerico>> response = serviceImpl.busquedaChoferes();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/vehiculos")
	public ResponseEntity<?> consultaVehiculos() {
		Respuesta<List<DatosVehiculo>> response = serviceImpl.busquedaVehiculosAsignables();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
    private static List<Sort.Order> convertSort(String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sort[0].contains(",")) {
            for (String string : sort) {
                String[] strings = string.split(",");
                orders.add(new Sort.Order(Sort.Direction.fromString(strings[1]), strings[0]));
            }
        } else {
            orders.add(new Sort.Order(Sort.Direction.fromString(sort[1]), sort[0]));
        }
        return orders;
    }
}
