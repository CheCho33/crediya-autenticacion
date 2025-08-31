package co.com.crediya.autenticacion.jwtservice.adapter;

import org.springframework.stereotype.Service;

import co.com.crediya.autenticacion.jwtservice.config.JwtConfig;
import co.com.crediya.autenticacion.model.usuario.gateways.PasswordEncrypterGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PasswordEncrypterAdapter implements PasswordEncrypterGateway {

    private final JwtConfig jwtConfig;

    @Override
    public Mono<String> generarToken(String contrasena) {
        return Mono.fromCallable(() -> jwtConfig.generateToken(contrasena));
    }
}
