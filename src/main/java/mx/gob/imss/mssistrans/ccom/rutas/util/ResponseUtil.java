package mx.gob.imss.mssistrans.ccom.rutas.util;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mx.gob.imss.mssistrans.ccom.rutas.dto.DatosUsuarioDTO;
import mx.gob.imss.mssistrans.ccom.rutas.dto.Response;
import mx.gob.imss.mssistrans.ccom.rutas.dto.SiniestrosResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;


/**
 * @author opimentel
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseUtil {

    /**
     * Regresa un true en caso de que el usuario tenga el acceso denegado.
     *
     * @return
     */
    public static boolean getAccess() {
        String usuario = getUsuario();
        return usuario.equals("denegado");
    }

    /**
     * Recupera el nombre de Usuario.
     *
     * @return
     */
    private static String getUsuario() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getPrincipal();
    }

    public static <T> Response<T> noAutorizado(Response<T> respuesta) {
        String usuario = getUsuario();
        respuesta.setError(false);
        respuesta.setCodigo(HttpStatus.UNAUTHORIZED.value());
        respuesta.setMensaje(usuario);
        return respuesta;

    }

    public static <T> Response<T> errorException(Exception e) {
        Response<T> respuesta = new Response<>();
        respuesta.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        respuesta.setError(true);
        respuesta.setMensaje(e.getMessage());
        return respuesta;
    }

    public static DatosUsuarioDTO datosUsuarios() {
        String usuario = getUsuario();
        Gson gson = new Gson();
        return gson.fromJson(usuario, DatosUsuarioDTO.class);
    }


    /**
     * Crea una respuesta exitosa.
     *
     * @param datos
     * @param <T>
     * @return
     */
    public static <T> Response<T> crearRespuestaExito(T datos) {
        Response<T> response = new Response<>();
        response.setCodigo(HttpStatus.OK.value());
        response.setError(false);
        response.setMensaje("Exito");
        response.setDatos(datos);

        return response;
    }


    /**
     * Crea una respuesta para sieniestros, pero ya se esta atendiendo en otro servicio.
     *
     * @param respuesta
     * @param msg
     * @param listaDeUnidad
     * @param <T>
     * @return
     */
    public static <T> Response<T> respSiniestros(Response<T> respuesta, String msg,
                                                 List<SiniestrosResponse> listaDeUnidad) {
        respuesta.setCodigo(HttpStatus.OK.value());
        respuesta.setError(false);
        respuesta.setMensaje(msg);
        respuesta.setDatos((T) listaDeUnidad);
        return respuesta;
    }

    /**
     * Recupera el nombre de la columna por la que se va a filtrar.
     *
     * @param nameCol
     * @return
     * @deprecated
     */
    @Deprecated
    public static String getNameColAsignacion(String nameCol) {
        switch (nameCol) {
            case "idRuta":
                return "ID_RUTA";
            case "idSolicitud":
                return "ID_SOLICITUD";
            case "cveEcco":
                return "CVE_ECCO";
            case "idEstatusSolicitud":
                return "DES_ESTATUS_ASIGNA";
            default:
                return "ID_CONTROL_RUTA";
        }
    }

    public static String getNameColReasig(String nameCol) {
        switch (nameCol) {
            case "idRuta":
                return "NUM_FOLIO_RUTA";
            case "idSolicitud":
                return "ID_SOLICITUD";
            case "cveEcco":
                return "CVE_ECCO";
            case "idEstatusSolicitud":
                return "DES_ESTATUS_ASIGNA";
            default:
                return "ID_REASIGNACION";
        }
    }
}
