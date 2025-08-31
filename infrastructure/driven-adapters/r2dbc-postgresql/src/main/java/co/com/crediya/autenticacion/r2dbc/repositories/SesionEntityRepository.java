package co.com.crediya.autenticacion.r2dbc.repositories;

import co.com.crediya.autenticacion.r2dbc.entities.SesionEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface SesionEntityRepository extends R2dbcRepository<SesionEntity, Long> {

    Mono<SesionEntity> findByToken(String token);
    Mono<SesionEntity> findByUsuarioId(Long usuarioId);
}
