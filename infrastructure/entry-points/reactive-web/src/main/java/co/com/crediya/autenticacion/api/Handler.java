package co.com.crediya.autenticacion.api;

import co.com.crediya.autenticacion.api.dto.CrearUsuarioDto;
import co.com.crediya.autenticacion.api.dto.RespuestaGenericaDto;
import co.com.crediya.autenticacion.api.mapper.UsuarioRequest;
import co.com.crediya.autenticacion.usecase.usuario.UsuarioUseCase;
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

    private final UsuarioUseCase usuarioUseCase;

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        // useCase.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> guardarUsuario(ServerRequest serverRequest) {
        log.info("Inicio Creacion de Usuario");
        return serverRequest
                .bodyToMono(CrearUsuarioDto.class)
                .doOnNext(dto -> log.info("DTO recibido: {}", dto))
                .map(usuarioRequest::toUsuario)
                .flatMap(usuarioUseCase::registrarUsuario)
                .map(usuario -> new RespuestaGenericaDto("Usuario Creado", usuario))
                .flatMap(respuesta -> ServerResponse.ok().bodyValue(respuesta))
                .doOnSuccess(resp -> log.info("Usuario creado exitosamente"))
                .doOnError(error -> {
                    log.error("Error al crear usuario: {}", error.getMessage());
                    if (error instanceof org.springframework.web.bind.support.WebExchangeBindException) {
                        log.error("Error de validación detectado");
                    }
                });
    }
}
