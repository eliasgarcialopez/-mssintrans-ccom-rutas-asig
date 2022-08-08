package mx.gob.imss.mssistrans.rutas.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static java.util.Collections.emptyList;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

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
			try {
				user = Jwts.parser().setSigningKey(decodedSecret).parseClaimsJws(token.replace("Bearer", "")) // este
																												// metodo
																												// es el
																												// que
																												// valida
						.getBody().getSubject();

			} catch (MalformedJwtException e) {
				log.info("token mal formado");
				return new UsernamePasswordAuthenticationToken("denegado", null, emptyList());
			} catch (UnsupportedJwtException e) {
				log.info("token no soportado");
				return new UsernamePasswordAuthenticationToken("denegado", null, emptyList());
			} catch (ExpiredJwtException e) {
				log.info("token expirado");
				return new UsernamePasswordAuthenticationToken("denegado", null, emptyList());
			} catch (IllegalArgumentException e) {
				log.info("token mal formado");
				return new UsernamePasswordAuthenticationToken("denegado", null, emptyList());
			} catch (SignatureException e) {
				log.info("token no firmado");
				return new UsernamePasswordAuthenticationToken("denegado", null, emptyList());
			}
			// Recordamos que para las demás peticiones
			// no requerimos una autenticacion por username/password
			// por este motivo podemos devolver un UsernamePasswordAuthenticationToken sin
			// password
			return user != null ? new UsernamePasswordAuthenticationToken(user, null, emptyList()) : null;
		}
		return new UsernamePasswordAuthenticationToken("denegado", null, emptyList());

	}

}