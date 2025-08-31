package co.com.crediya.autenticacion.usecase.usuario;

import co.com.crediya.autenticacion.model.exceptions.CrediYautentiateException;
import co.com.crediya.autenticacion.model.usuario.Usuario;
import co.com.crediya.autenticacion.model.usuario.dto.UsuarioLoginDto;
import reactor.core.publisher.Mono;

public class UsuarioLoginValidator {

    public static Mono<Usuario> validarUsuario(UsuarioLoginDto usuarioLoginDto, Usuario usuario) {
        if (usuario.getContrasena().equals(usuarioLoginDto.getPassword())) {
            return Mono.just(usuario);
        } else {
            return Mono.error(new CrediYautentiateException("Usuario o contraseña inválidos"));
        }
    }




}
