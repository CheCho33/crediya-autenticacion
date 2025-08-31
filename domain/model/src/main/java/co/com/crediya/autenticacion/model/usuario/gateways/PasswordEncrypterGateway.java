package co.com.crediya.autenticacion.model.usuario.gateways;


import reactor.core.publisher.Mono;

public interface PasswordEncrypterGateway {

    Mono<String> generarToken(String contrasena);

}
