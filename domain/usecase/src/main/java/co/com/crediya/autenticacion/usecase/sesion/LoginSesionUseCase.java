package co.com.crediya.autenticacion.usecase.sesion;

import co.com.crediya.autenticacion.model.exceptions.CrediYautentiateException;
import co.com.crediya.autenticacion.model.sesion.Sesion;
import co.com.crediya.autenticacion.model.sesion.gateways.SesionRepository;
import co.com.crediya.autenticacion.model.usuario.Usuario;
import co.com.crediya.autenticacion.model.usuario.dto.UsuarioLoginDto;
import co.com.crediya.autenticacion.model.usuario.gateways.PasswordEncrypterGateway;
import co.com.crediya.autenticacion.model.usuario.gateways.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Date;

@RequiredArgsConstructor
public class LoginSesionUseCase {

    private final UsuarioRepository usuarioRepository;
    private final SesionRepository sesionRepository;
    private final PasswordEncrypterGateway passwordEncrypterGateway;


    public Mono<Sesion> loginUsuairo(UsuarioLoginDto usuarioLoginDto) {

        return validarCredenciales(usuarioLoginDto)
                .flatMap(usuario -> getSesion(usuario));
    }

    private Mono<Usuario> validarCredenciales(UsuarioLoginDto usuarioLoginDto) {
        return usuarioRepository.buscarPorEmail(usuarioLoginDto.getEmail().toLowerCase())
                .switchIfEmpty(Mono.error(new CrediYautentiateException("Usuario o contraseña inválidos")))
                .filter( user ->
                {
                    boolean matches = usuarioLoginDto.getPassword().equals(user.getContrasena());
                    return matches;
                })

                .switchIfEmpty(Mono.error(new CrediYautentiateException("Usuario o contraseña inválidos")))
                .doOnError( error -> Mono.error(new CrediYautentiateException("no se puedo hacer la validacion"))
                );
    }

    private Mono<Sesion> getSesion(Usuario usuario) {
        return sesionRepository.buscarSesionPorUsuarioId(usuario.getUsuarioId())
                .switchIfEmpty(crearSesion(usuario));
    }

    private Mono<Sesion> crearSesion(Usuario usuario) {

        return Mono.just(usuario)
//                .flatMap(passwordEncrypterGateway::generarToken)
                .flatMap(u -> passwordEncrypterGateway.generarToken(u.getEmail()))
                .flatMap(token -> {
                    Sesion sesion = new Sesion();
                    sesion.setUsuarioId(usuario.getUsuarioId());
                    sesion.setToken(token);
                    sesion.setFechaExpiracion(new Date( System.currentTimeMillis() + 86400000));
                    return Mono.just(sesion);
                })
                .flatMap(sesionRepository::guardar);
    }
}
