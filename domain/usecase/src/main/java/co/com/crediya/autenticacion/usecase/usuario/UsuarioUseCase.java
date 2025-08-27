package co.com.crediya.autenticacion.usecase.usuario;

import co.com.crediya.autenticacion.model.usuario.Usuario;
import reactor.core.publisher.Mono;

/**
 * Clase principal que orquesta los casos de uso relacionados con usuarios.
 * 
 * Esta clase actúa como punto de entrada para todas las operaciones de usuarios
 * y delega la ejecución a los casos de uso específicos.
 */
public class UsuarioUseCase {
    
    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;

    public UsuarioUseCase(RegistrarUsuarioUseCase registrarUsuarioUseCase) {
        this.registrarUsuarioUseCase = registrarUsuarioUseCase;
    }
    
    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * @param usuario Datos del usuario a registrar
     * @return Mono con el usuario registrado exitosamente
     */
    public Mono<Usuario> registrarUsuario(Usuario usuario) {
        return registrarUsuarioUseCase.registrar(usuario);
    }
}
