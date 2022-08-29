package mx.gob.imss.mssistrans.ccom.rutas.controller;

import com.google.gson.Gson;
import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosBitacora;
import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosUsuario;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Respuesta;
import mx.gob.imss.mssistrans.ccom.rutas.service.BitacoraService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/bitacora")
@CrossOrigin(methods = { RequestMethod.GET, RequestMethod.POST })
public class BitacoraServiciosController {
	
	@Autowired
	private BitacoraService bitacoraService;
	
	/**
	 * Endpoint para consultar vehiculos por ecco
	 * @return
	 */
	@GetMapping("/ecco/{ecco}")
	public ResponseEntity<?> consultaVehiculoEcco(@PathVariable String ecco) {

	    String usuario = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (usuario.equals("denegado")) {
           Respuesta<?> response = response = new Respuesta<>();
           response.setError(false);
           response.setCodigo(HttpStatus.UNAUTHORIZED.value());
           response.setMensaje(usuario);
           return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
       
        Gson gson = new Gson();
        DatosUsuario datosUsuarios = gson.fromJson(usuario, DatosUsuario.class);
        datosUsuarios.getIDOOAD();
        datosUsuarios.getRol();

        Respuesta<DatosBitacora> response = bitacoraService.buscaVehiculo(ecco, datosUsuarios.getIDOOAD());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Endpoint para generar la bitacora de servicios
     * @param bitacoraServicio
     * @return
     */
    @PostMapping(path = "/genera/{idOoad}/{idControlRuta}/{fechaResg}")
    public ResponseEntity<?> bitacoraServicio(@PathVariable Integer idOoad, @PathVariable Integer idControlRuta, @PathVariable String fechaResg) throws IOException {
    	
    	String usuario = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (usuario.equals("denegado")) {
            Respuesta<?> response = response = new Respuesta<>();
            response.setError(false);
            response.setCodigo(HttpStatus.UNAUTHORIZED.value());
            response.setMensaje(usuario);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        
        Gson gson = new Gson();
        DatosUsuario datosUsuarios = gson.fromJson(usuario, DatosUsuario.class);
        datosUsuarios.getIDOOAD();
        datosUsuarios.getRol();

        Respuesta<byte[]> response = bitacoraService.generaBitacora(idOoad, idControlRuta, fechaResg, datosUsuarios.getMatricula());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=formato-bitacora-servicio.pdf")
                .body(response.getDatos());
    }
     
}
