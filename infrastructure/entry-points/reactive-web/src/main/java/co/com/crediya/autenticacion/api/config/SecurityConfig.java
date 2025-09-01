package co.com.crediya.autenticacion.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchanges -> exchanges
                    // Permitir preflight CORS
                    .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    // Endpoints públicos - login debe estar primero
                    .pathMatchers("/api/v1/login").permitAll()
                    .pathMatchers("/api/api/v1/usuarios").permitAll()
                    .pathMatchers("/api/v1/usuarios").permitAll()
                    .pathMatchers("/api/usecase/**").permitAll()
                    .pathMatchers("/h2/**").permitAll()
                    .pathMatchers("/actuator/**").permitAll()
                    .pathMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                    .anyExchange().permitAll()
            )
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable);
        
        return http.build();
    }
}
