package co.com.crediya.autenticacion.usecase.usuario;

import co.com.crediya.autenticacion.model.exceptions.CrediYautentiateException;
import co.com.crediya.autenticacion.model.usuario.Usuario;
import reactor.core.publisher.Mono;

public class UsuarioValidator {

    public static Mono<Usuario> validarUsuario(Usuario usuario) {

        if (usuario.getNombre() == null || usuario.getNombre().isBlank()) {
            String msg = "Los nombres del usuario no pueden ser nulos o vacíos";
            return Mono.error(new CrediYautentiateException(msg));
        }
        if (usuario.getApellido() == null || usuario.getApellido().isBlank()) {
            String msg = "Los apellidos del usuario no pueden ser nulos o vacíos";
            return Mono.error(new CrediYautentiateException(msg));
        }
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            String msg = "El email del usuario no puede ser nulo o vacío";
            return Mono.error(new CrediYautentiateException(msg));
        }

        if (usuario.getDocumentoIdentidad() == null || usuario.getDocumentoIdentidad().isBlank()) {
            String msg = "El documento de identidad del usuario no puede ser nulo o vacío";
            return Mono.error(new CrediYautentiateException(msg));
        }
        if (usuario.getTelefono() == null || usuario.getTelefono().isBlank()) {
            String msg = "El teléfono del usuario no puede ser nulo o vacío";
            return Mono.error(new CrediYautentiateException(msg));
        }

        if (usuario.getSalarioBase() == null) {
            String msg = "El salario base del usuario no puede ser nulo";
            return Mono.error(new CrediYautentiateException(msg));
        }
        if (usuario.getSalarioBase() < 0) {
            String msg = "El salario base no puede ser negativo";
            return Mono.error(new CrediYautentiateException(msg));
        }
        if (usuario.getSalarioBase() > 15000000.00) {
            String msg = "El salario base no puede exceder 15,000,000";
            return Mono.error(new CrediYautentiateException(msg));
        }
        if (usuario.getRolId() == null) {
            String msg = "El rol del usuario no puede ser nulo o vacío";
            return Mono.error(new CrediYautentiateException(msg));
        }
        return Mono.just(usuario);

    }
}