package co.com.crediya.autenticacion.r2dbc.repositories;

import co.com.crediya.autenticacion.r2dbc.entities.UsuarioEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Repositorio R2DBC para la entidad UsuarioEntity.
 * Proporciona operaciones CRUD reactivas para la tabla usuario.
 */
@Repository
public interface UsuarioEntityRepository extends R2dbcRepository<UsuarioEntity, Long> {

    /**
     * Busca un usuario por su email.
     *
     * @param email Email del usuario a buscar
     * @return Mono con la entidad encontrada o Mono.empty() si no existe
     */
    Mono<UsuarioEntity> findByEmail(String email);

    /**
     * Verifica si existe un usuario con el email proporcionado.
     *
     * @param email Email a verificar
     * @return Mono<Boolean> true si existe, false en caso contrario
     */
    Mono<Boolean> existsByEmail(String email);
}
