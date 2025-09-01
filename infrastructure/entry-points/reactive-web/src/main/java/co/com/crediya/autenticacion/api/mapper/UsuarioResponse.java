package co.com.crediya.autenticacion.api.mapper;

import co.com.crediya.autenticacion.model.usuario.Usuario;
import co.com.crediya.autenticacion.model.usuario.dto.UsuarioResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UsuarioResponse {

    public UsuarioResponseDto toUsuarioResponseDto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return new UsuarioResponseDto(
                usuario.getUsuarioId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getDocumentoIdentidad(),
                usuario.getTelefono(),
                usuario.getRolId(),
                usuario.getSalarioBase()
        );
    }
}
