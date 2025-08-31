package co.com.crediya.autenticacion.r2dbc.adapters;

import co.com.crediya.autenticacion.model.sesion.Sesion;
import co.com.crediya.autenticacion.model.sesion.gateways.SesionRepository;
import co.com.crediya.autenticacion.r2dbc.mappers.SessionEntityMapper;
import co.com.crediya.autenticacion.r2dbc.repositories.SesionEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;


@Repository
@RequiredArgsConstructor
@Transactional
public class SesionAdapters implements SesionRepository {

    private final SesionEntityRepository sesionEntityRepository;

    @Override
    public Mono<Sesion> buscarSesion(Long id) {
        return sesionEntityRepository.findById(id)
                .map(SessionEntityMapper::toDomain);
    }

    @Override
    public Mono<Sesion> guardar(Sesion sesion) {
        return Mono.just(sesion)
                .map(SessionEntityMapper::toEntity)
                .flatMap(sesionEntityRepository::save)
                .map(SessionEntityMapper::toDomain);
    }

    @Override
    public Mono<Sesion> buscarSesionPorToken(String token) {
        return sesionEntityRepository.findByToken(token)
                .map(SessionEntityMapper::toDomain);
    }

    @Override
    public Mono<Sesion> buscarSesionPorUsuarioId(Long usuarioId) {
        return sesionEntityRepository.findByUsuarioId(usuarioId)
                .map(SessionEntityMapper::toDomain);
    }
}

