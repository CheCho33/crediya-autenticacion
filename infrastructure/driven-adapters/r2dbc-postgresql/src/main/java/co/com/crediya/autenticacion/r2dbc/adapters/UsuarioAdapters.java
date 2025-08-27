package co.com.crediya.autenticacion.r2dbc.adapters;

import co.com.crediya.autenticacion.model.usuario.Usuario;
import co.com.crediya.autenticacion.model.usuario.gateways.UsuarioRepository;
import co.com.crediya.autenticacion.r2dbc.mappers.UserEntityMapper;
import co.com.crediya.autenticacion.r2dbc.repositories.UsuarioEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Adaptador de infraestructura que implementa el puerto UsuarioRepository.
 * Maneja la persistencia de usuarios usando Spring Data R2DBC con PostgreSQL.
 */
@Repository
@RequiredArgsConstructor
public class UsuarioAdapters implements UsuarioRepository {

    private final UsuarioEntityRepository usuarioEntityRepository;

    /**
     * Guarda un usuario en la base de datos.
     *
     * @param usuario Usuario a guardar
     * @return Mono con el usuario guardado (con ID y versión actualizada)
     */
    @Override
    public Mono<Usuario> guardar(Usuario usuario) {
        return Mono.just(usuario)
                .map(UserEntityMapper::toEntity)
                .flatMap(usuarioEntityRepository::save)
                .map(UserEntityMapper::toDomain);
    }

    /**
     * Busca un usuario por su email.
     *
     * @param email Email del usuario a buscar
     * @return Mono con el usuario encontrado o Mono.empty() si no existe
     */
    @Override
    public Mono<Usuario> buscarPorEmail(String email) {
        return usuarioEntityRepository.findByEmail(email)
                .map(UserEntityMapper::toDomain);
    }

    /**
     * Verifica si existe un usuario con el email proporcionado.
     *
     * @param email Email a verificar
     * @return Mono<Boolean> true si existe, false en caso contrario
     */
    @Override
    public Mono<Boolean> existePorEmail(String email) {
        return usuarioEntityRepository.existsByEmail(email);
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param idUsuario ID del usuario a buscar
     * @return Mono con el usuario encontrado o Mono.empty() si no existe
     */
    @Override
    public Mono<Usuario> buscarPorId(Long idUsuario) {
        return usuarioEntityRepository.findById(idUsuario)
                .map(UserEntityMapper::toDomain);
    }
}

