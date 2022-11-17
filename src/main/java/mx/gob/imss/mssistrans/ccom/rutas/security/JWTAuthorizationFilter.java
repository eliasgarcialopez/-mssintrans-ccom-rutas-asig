package mx.gob.imss.mssistrans.ccom.rutas.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private String secretkey;
    public JWTAuthorizationFilter(String secretkey) {
        this.secretkey = secretkey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            log.info("filter: {}", secretkey);
        Authentication authentication = JwtUtil.getAuthentication((HttpServletRequest)request, secretkey);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request,response);
        } catch (MalformedJwtException | UnsupportedJwtException | ExpiredJwtException | IllegalArgumentException | SignatureException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.error("Ha ocurrido un error, {}", e.getMessage());
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }
    }
}
