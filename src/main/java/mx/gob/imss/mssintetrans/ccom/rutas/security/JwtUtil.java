package mx.gob.imss.mssintetrans.ccom.rutas.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Base64;

import static java.util.Collections.emptyList;

@Slf4j
@Component
public class JwtUtil implements Serializable {
	
	private static final long serialVersionUID = -2550185165626007488L;
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

	public static Authentication getAuthentication(HttpServletRequest request,String secretKey2) {
		if (secretKey2 == null) {
			return null;
		}
		String secretKey = secretKey2;
		// Obtenemos el token que viene en el encabezado de la peticion
		String token = request.getHeader("Authorization");
		byte[] decodedSecret = Base64.getDecoder().decode(secretKey);
		// si hay un token presente, entonces lo validamos
		if (token != null) {
			String user = null;
				// este metodo es el que valida
			System.out.println(token);
				user = Jwts.parser().setSigningKey(decodedSecret).parseClaimsJws(token.replace("Bearer", "")).getBody().getSubject();
			// Recordamos que para las demás peticiones
			// no requerimos una autenticacion por username/password
			// por este motivo podemos devolver un UsernamePasswordAuthenticationToken sin
			// password
				System.out.println(user);
			return user != null ? new UsernamePasswordAuthenticationToken(user, null, emptyList()) : null;
		}
		return new UsernamePasswordAuthenticationToken("denegado", null, emptyList());
	}

}