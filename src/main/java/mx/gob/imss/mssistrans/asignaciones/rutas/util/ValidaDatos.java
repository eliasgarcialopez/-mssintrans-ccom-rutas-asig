package mx.gob.imss.mssistrans.asignaciones.rutas.util;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.gson.Gson;

import mx.gob.imss.mssistrans.asignaciones.rutas.dto.AsigRutasResponse;
import mx.gob.imss.mssistrans.asignaciones.rutas.dto.DatosAsigResponse;
import mx.gob.imss.mssistrans.asignaciones.rutas.dto.DatosUsuarioDTO;
import mx.gob.imss.mssistrans.asignaciones.rutas.dto.RegistroRecorridoResponse;
import mx.gob.imss.mssistrans.asignaciones.rutas.dto.Response;
import mx.gob.imss.mssistrans.asignaciones.rutas.dto.RutasResponse;
import mx.gob.imss.mssistrans.asignaciones.rutas.dto.SiniestrosResponse;
import mx.gob.imss.mssistrans.asignaciones.rutas.dto.SolicitudTrasladoResponse;
import mx.gob.imss.mssistrans.asignaciones.rutas.dto.TripulacionAsigResponse;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ValidaDatos {

	public static boolean getAccess() {
		String usuario = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usuario.equals("denegado");
	}

	public static <T> Response noAutorizado(Response<?> respuesta) {
		String usuario = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		respuesta.setError(false);
		respuesta.setCodigo(HttpStatus.UNAUTHORIZED.value());
		respuesta.setMensaje(usuario);
		return respuesta;

	}

	public static <T> Response<?> errorException(Response<?> respuesta, Exception e) {
		respuesta.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		respuesta.setError(true);
		respuesta.setMensaje(e.getMessage());
		return respuesta;
	}

	public static <T> Response<?> respAsignacionRuta(Response<T> respuesta, String msg,
			Page<AsigRutasResponse> content) {
		respuesta.setCodigo(HttpStatus.OK.value());
		respuesta.setError(false);
		respuesta.setMensaje(msg);
		respuesta.setDatos((T) content);
		return respuesta;
	}

	public static <T> Response<?> respRutas(Response<T> respuesta, String msg, List<RutasResponse> listadeRutas) {
		respuesta.setCodigo(HttpStatus.OK.value());
		respuesta.setError(false);
		respuesta.setMensaje(msg);
		respuesta.setDatos((T) listadeRutas);
		return respuesta;
	}

	public static <T> Response<?> respSolTras(Response<T> respuesta, String msg,
			List<SolicitudTrasladoResponse> listadeRutas) {
		respuesta.setCodigo(HttpStatus.OK.value());
		respuesta.setError(false);
		respuesta.setMensaje(msg);
		respuesta.setDatos((T) listadeRutas);
		return respuesta;
	}

	public static <T> Response<?> respDatosAsig(Response<T> respuesta, String msg,
			List<DatosAsigResponse> objetoMapeado) {
		respuesta.setCodigo(HttpStatus.OK.value());
		respuesta.setError(false);
		respuesta.setMensaje(msg);
		respuesta.setDatos((T) objetoMapeado);
		return respuesta;
	}

	public static DatosUsuarioDTO datosUsuarios() {

		String usuario = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Gson gson = new Gson();
		return gson.fromJson(usuario, DatosUsuarioDTO.class);
		// return null;
	}

	public static <T> Response<?> respTripulacionAsig(Response<T> respuesta, String msg,
			List<TripulacionAsigResponse> objetoMapeado) {
		respuesta.setCodigo(HttpStatus.OK.value());
		respuesta.setError(false);
		respuesta.setMensaje(msg);
		respuesta.setDatos((T) objetoMapeado);
		return respuesta;
	}

	public static <T> Response<?> respRegistroRecorrido(Response<T> respuesta, String msg,
			RegistroRecorridoResponse listaDeUnidad) {
		respuesta.setCodigo(HttpStatus.OK.value());
		respuesta.setError(false);
		respuesta.setMensaje(msg);
		respuesta.setDatos((T) listaDeUnidad);
		return respuesta;
	}
	
	


	public static <T> Response<?> respSiniestros(Response<T> respuesta, String msg,
			List<SiniestrosResponse> listaDeUnidad) {
		respuesta.setCodigo(HttpStatus.OK.value());
		respuesta.setError(false);
		respuesta.setMensaje(msg);
		respuesta.setDatos((T) listaDeUnidad);
		return respuesta;
	}
	public static String getNameCol(String nameCol) {
		if (nameCol.equals("idRutaAsignacion"))
			return "ID_RUTA";
		else if (nameCol.equals("idSolicitud"))
			return "ID_SOLICITUD";
		else if (nameCol.equals("CVE_ECCO"))
			return "cveEcco";
		else if (nameCol.equals("idEstatusSolicitud"))
			return "DES_ESTATUS_ASIGNA";
		else
			return "ID_RUTA";
	}
}
