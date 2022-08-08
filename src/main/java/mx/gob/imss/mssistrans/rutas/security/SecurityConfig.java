package mx.gob.imss.mssistrans.rutas.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${jwt.secretkey}")
    private String secretkey ;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().anyRequest().authenticated() //cualquier otra peticion requiere autenticacion
            .and()
            // Las demás peticiones pasarán por este filtro para validar el token
            .addFilterBefore(new JWTAuthorizationFilter(secretkey),
                    UsernamePasswordAuthenticationFilter.class);
    }
 
}