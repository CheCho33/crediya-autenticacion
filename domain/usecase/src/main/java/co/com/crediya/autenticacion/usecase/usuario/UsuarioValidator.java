package co.com.crediya.autenticacion.usecase.usuario;

import co.com.crediya.autenticacion.model.exceptions.CrediYautentiateException;
import co.com.crediya.autenticacion.model.usuario.Usuario;
import reactor.core.publisher.Mono;

public class UsuarioValidator {

    public static Mono<Usuario> validarUsuario(Usuario usuario) {

        if (usuario.getNombre() == null || usuario.getNombre().isBlank()) {
                return Mono.error(new CrediYautentiateException("Los nombres del usuario no pueden ser nulos o vacíos"));
            }
            if (usuario.getApellido() == null || usuario.getApellido().isBlank()) {
                return Mono.error(new CrediYautentiateException("Los apellidos del usuario no pueden ser nulos o vacíos"));
            }
            if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
                return Mono.error(new CrediYautentiateException("El email del usuario no puede ser nulo o vacío"));
            }

            if (usuario.getDocumentoIdentidad() == null || usuario.getDocumentoIdentidad().isBlank()) {
                return Mono.error(new CrediYautentiateException("El documento de identidad del usuario no puede ser nulo o vacío"));
            }
            if (usuario.getTelefono() == null || usuario.getTelefono().isBlank()) {
                return Mono.error(new CrediYautentiateException("El teléfono del usuario no puede ser nulo o vacío"));
            }

            if (usuario.getSalarioBase() == null) {
                return Mono.error(new CrediYautentiateException("El salario base del usuario no puede ser nulo"));
            }
            if (usuario.getSalarioBase() < 0) {
                return Mono.error(new CrediYautentiateException("El salario base no puede ser negativo"));
            }
            if (usuario.getSalarioBase() > 150000.00) {
                return Mono.error(new CrediYautentiateException("El salario base no puede exceder 15,000,000"));
            }
            if (usuario.getRolId() == null) {
                return Mono.error(new CrediYautentiateException("El rol del usuario no puede ser nulo o vacío"));
            }
        return Mono.just(usuario);

    }
}