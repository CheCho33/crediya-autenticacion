package co.com.crediya.autenticacion.api;

import co.com.crediya.autenticacion.api.dto.CrearUsuarioDto;
import co.com.crediya.autenticacion.api.dto.RespuestaGenericaDto;
import co.com.crediya.autenticacion.api.mapper.UsuarioRequest;
import co.com.crediya.autenticacion.model.usuario.dto.UsuarioLoginDto;
import co.com.crediya.autenticacion.usecase.sesion.LoginSesionUseCase;
import co.com.crediya.autenticacion.usecase.usuario.RegistrarUsuarioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class Handler {

    private final UsuarioRequest usuarioRequest;

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;
    private final LoginSesionUseCase loginSesionUseCase;

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> guardarUsuario(ServerRequest serverRequest) {
        log.info("Inicio Creacion de Usuario");
        return serverRequest
                .bodyToMono(CrearUsuarioDto.class)
                .doOnNext(dto -> log.info("DTO recibido: {}", dto))
                .map(usuarioRequest::toUsuario)
                .flatMap(registrarUsuarioUseCase::registrar)
                .map(usuario -> new RespuestaGenericaDto("Usuario Creado", usuario))
                .flatMap(respuesta -> ServerResponse.ok().bodyValue(respuesta))
                .doOnSuccess(resp -> log.info("Usuario creado exitosamente"))
                .doOnError(error -> {
                    log.error("Error al crear usuario: {}", error.getMessage());
                });
    }

    public Mono<ServerResponse> loginUsuario(ServerRequest serverRequest) {
        log.info("Autenticacion de usuario");
        return serverRequest
                .bodyToMono(UsuarioLoginDto.class)
                .doOnNext(dto -> log.info("Login de usuario {}", dto.getEmail()))
                .flatMap(loginSesionUseCase::loginUsuairo)
                .flatMap(respuesta -> ServerResponse.ok().bodyValue(respuesta))
                .doOnError(error -> log.error("Error al crear usuario: {}", error.getMessage()));
    }
}
