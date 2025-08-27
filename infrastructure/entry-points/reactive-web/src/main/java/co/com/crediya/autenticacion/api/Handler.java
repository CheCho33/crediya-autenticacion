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

@Component
@RequiredArgsConstructor
public class Handler {

    private final UsuarioRequest usuarioRequest;

    private final UsuarioUseCase usuarioUseCase;

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        // useCase.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> guardarUsuario(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(CrearUsuarioDto.class)
                .map( usuarioRequest::toUsuario)
                .flatMap(usuarioUseCase::registrarUsuario)
                .map(usuario -> new RespuestaGenericaDto("hola mundo", usuario))
                .flatMap(respuesta -> ServerResponse.ok().bodyValue(respuesta));
    }
}
