package co.com.crediya.autenticacion.model.usuario.gateways;

import co.com.crediya.autenticacion.model.usuario.Usuario;
import reactor.core.publisher.Mono;

/**
 * Puerto de salida para la persistencia de usuarios.
 * Define los contratos para las operaciones de base de datos de usuarios.
 */
public interface UsuarioRepository {
    
    /**
     * Guarda un usuario en la base de datos.
     * 
     * @param usuario Usuario a guardar
     * @return Mono con el usuario guardado (con ID y versión actualizada)
     */
    Mono<Usuario> guardar(Usuario usuario);
    
    /**
     * Busca un usuario por su email.
     *
     * @param email Email del usuario a buscar
     * @return Mono con el usuario encontrado o Mono.empty() si no existe
     */
    Mono<Usuario> buscarPorEmail(String email);

    /**
     * Verifica si existe un usuario con el email proporcionado.
     *
     * @param email Email a verificar
     * @return Mono<Boolean> true si existe, false en caso contrario
     */
    Mono<Boolean> existePorEmail(String email);

    /**
     * Busca un usuario por su ID.
     * 
     * @param idUsuario ID del usuario a buscar
     * @return Mono con el usuario encontrado o Mono.empty() si no existe
     */
    Mono<Usuario> buscarPorId(Long idUsuario);
}
