package mx.gob.imss.mssistrans.ccom.rutas.controller;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosBitacora;
import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosUsuario;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;
import mx.gob.imss.mssistrans.ccom.rutas.exceptions.UserUnauthorizedException;
import mx.gob.imss.mssistrans.ccom.rutas.service.BitacoraService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static mx.gob.imss.mssistrans.ccom.rutas.util.ResponseEntityUtil.sendResponse;

@AllArgsConstructor
@RestController
@RequestMapping("/bitacora")
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
public class BitacoraServiciosController {

    private final BitacoraService bitacoraService;

    /**
     * Endpoint para consultar vehiculos por ecco
     *
     * @return
     */
    @GetMapping("/ecco/{ecco}")
    public ResponseEntity<Response<DatosBitacora>> consultaVehiculoEcco(@PathVariable String ecco) {

        String usuario = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Response<DatosBitacora> response = new Response<>();
        if (usuario.equals("denegado")) {
            response.setError(false);
            response.setCodigo(HttpStatus.UNAUTHORIZED.value());
            response.setMensaje(usuario);
            return sendResponse(response);
        }

        Gson gson = new Gson();
        DatosUsuario datosUsuario = gson.fromJson(usuario, DatosUsuario.class);
        datosUsuario.getIdOoad();
        datosUsuario.getRol();

        response = bitacoraService.buscaVehiculo(ecco, datosUsuario.getRol().equals("Administrador") ? 0 : datosUsuario.getIdOoad());
        return sendResponse(response);
    }

    /**
     * Endpoint para generar la bitacora de servicios
     *
     * @param idOoad
     * @param idControlRuta
     * @param fechaResguardo
     * @return
     */
    @PostMapping(path = "/genera/{idOoad}/{idControlRuta}/{fechaResguardo}")
    public ResponseEntity<byte[]> bitacoraServicio(
            @PathVariable Integer idOoad,
            @PathVariable Integer idControlRuta,
            @PathVariable String fechaResguardo) throws IOException, UserUnauthorizedException {

        String usuario = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Response<byte[]> response = new Response<>();
        validarUsuario(usuario, response);

        Gson gson = new Gson();
        DatosUsuario datosUsuarios = gson.fromJson(usuario, DatosUsuario.class);

        response = bitacoraService.generaBitacora(
                idOoad, idControlRuta,
                fechaResguardo, datosUsuarios.getMatricula());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=formato-bitacora-servicio.pdf")
                .body(response.getDatos());
    }

    private static void validarUsuario(String usuario, Response<byte[]> response) throws UserUnauthorizedException {
        if (usuario.equals("denegado")) {
            response.setError(false);
            response.setCodigo(HttpStatus.UNAUTHORIZED.value());
            response.setMensaje(usuario);
            throw new UserUnauthorizedException();
        }
    }

}
