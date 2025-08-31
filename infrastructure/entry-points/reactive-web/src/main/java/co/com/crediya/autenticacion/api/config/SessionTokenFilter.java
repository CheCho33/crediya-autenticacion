package co.com.crediya.autenticacion.api.config;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import co.com.crediya.autenticacion.model.exceptions.CrediYautentiateException;
import co.com.crediya.autenticacion.model.sesion.gateways.SesionRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SessionTokenFilter implements WebFilter {

    private final SesionRepository sesionRepository;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        
        // No aplicar el filtro para el endpoint de login
        if ("/api/v1/login".equals(path)) {
            return chain.filter(exchange);
        }
        
        String token = exchange.getRequest().getHeaders().getFirst("x-Token");
        
        // Si no hay token, generar error
        if (token == null || token.trim().isEmpty()) {
            return Mono.error(new CrediYautentiateException("No tienen permisos para este servicio"));
        }
        
        // Validar el token y buscar la sesión
        return sesionRepository.buscarSesionPorToken(token)
                .flatMap(sesion -> {
                    // Agregar la sesión a los atributos del exchange
                    exchange.getAttributes().put("sesion", sesion);
                    return chain.filter(exchange);
                })
                .switchIfEmpty(Mono.error(new CrediYautentiateException("No tienen permisos para este servicio")));
    }
}
