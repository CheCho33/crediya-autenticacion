package co.com.crediya.autenticacion.model.sesion.gateways;

import reactor.core.publisher.Mono;
import co.com.crediya.autenticacion.model.sesion.Sesion;

public interface SesionRepository {
    Mono<Sesion> buscarSesion(Long id);
    Mono<Sesion> guardar(Sesion sesion);
    Mono<Sesion> buscarSesionPorToken(String token);
    Mono<Sesion> buscarSesionPorUsuarioId(Long usuarioId);
}
