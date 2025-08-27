package co.com.crediya.autenticacion.usecase.usuario;


import co.com.crediya.autenticacion.model.exceptions.CrediYautentiateException;
import co.com.crediya.autenticacion.model.usuario.Usuario;
import co.com.crediya.autenticacion.model.usuario.gateways.UsuarioRepository;
import reactor.core.publisher.Mono;

/**
 * Implementación del caso de uso para registrar un nuevo usuario.
 * 
 * Este servicio implementa las reglas de negocio para el registro de usuarios
 * siguiendo los principios de arquitectura hexagonal y programación reactiva.
 */
public class RegistrarUsuarioUseCase {
    
    private final UsuarioRepository usuarioRepository;
    
    public RegistrarUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    public Mono<Usuario> registrar(Usuario usuario) {
        return Mono.just(usuario)
            .flatMap(this::validarDatos)
            .flatMap(usuarioRepository::guardar)
            .onErrorMap(this::mapearExcepciones);
    }
    
    /**
     * Valida los datos de entrada del usuario.
     * 
     * @param usuario Datos a validar
     * @return Mono con los datos validados
     */
    private Mono<Usuario> validarDatos(Usuario usuario) {

        return UsuarioValidator.validarUsuario(usuario)
                .flatMap(this::validarUnicidadEmail);
    }
    
    /**
     * Valida que el email no esté previamente registrado.
     * 
     * @param usuario email del usuario a validar
     * @return Mono con los datos si el email es único
     */
    private Mono<Usuario> validarUnicidadEmail(Usuario usuario) {
        return usuarioRepository.existePorEmail(usuario.getEmail().toLowerCase())
            .flatMap(existe -> {
                if (existe) {
                    return Mono.error(new CrediYautentiateException(
                        "El email ya está registrado en el sistema"
                    ));
                }
                return Mono.just(usuario);
            });
    }

    
    /**
     * Mapea las excepciones del dominio a excepciones específicas.
     * 
     * @param error Error original
     * @return Excepción mapeada
     */
    private Throwable mapearExcepciones(Throwable error) {
        if (error instanceof CrediYautentiateException) {
            return error;
        }
        else{
            return new CrediYautentiateException("Ha ocurrido un error inesperado al registrar el usuario. Por favor, intente nuevamente.");
        }
    }
}
