package co.com.crediya.autenticacion.usecase.sesion;

import co.com.crediya.autenticacion.model.exceptions.CrediYautentiateException;
import co.com.crediya.autenticacion.model.sesion.UrlsAdministrador;
import co.com.crediya.autenticacion.model.sesion.UrlsAsesor;
import co.com.crediya.autenticacion.model.sesion.UrlsCliente;
import co.com.crediya.autenticacion.model.sesion.Sesion;
import co.com.crediya.autenticacion.model.usuario.Usuario;
import co.com.crediya.autenticacion.model.usuario.gateways.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SesionPermisoUrlUseCase {

    private final UsuarioRepository usuarioRepository;

    public Mono<Boolean> getPermiso(Sesion sesion, String url) {
        return usuarioRepository.buscarPorId(sesion.getUsuarioId())
                .flatMap(usuario -> validarPermisoPorRol(usuario, url));
    }

    private Mono<Boolean> validarPermisoPorRol(Usuario usuario, String url) {
        Long rolId = usuario.getRolId();
        
        // Mapeo de IDs de rol a nombres (esto debería venir de la base de datos)
        if (rolId == 1L) {
            return permisosAdministrador(url);
        } else if (rolId == 2L) {
            return permisosAsesor(url);
        } else if (rolId == 3L) {
            return permisosCliente(url);
        } else {
            return Mono.error(new CrediYautentiateException("El Rol asignado no tiene permisos para acceder a este servicio"));
        }
    }

    private Mono<Boolean> permisosAdministrador(String url) {
        return Mono.just(UrlsAdministrador.isUrlPermitida(url));
    }
    
    private Mono<Boolean> permisosAsesor(String url) {
        return Mono.just(UrlsAsesor.isUrlPermitida(url));
    }

    private Mono<Boolean> permisosCliente(String url) {
        return Mono.just(UrlsCliente.isUrlPermitida(url));
    }
}
