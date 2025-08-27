package co.com.crediya.autenticacion.api.mapper;

import org.springframework.stereotype.Component;

import co.com.crediya.autenticacion.api.dto.CrearUsuarioDto;
import co.com.crediya.autenticacion.model.usuario.Usuario;

/**
 * Mapper para convertir entre DTOs de la capa de presentación y entidades del dominio.
 * Facilita la conversión de CrearUsuarioDto a Usuario del modelo de dominio.
 */
@Component
public class UsuarioRequest {

    /**
     * Convierte un CrearUsuarioDto a Usuario del modelo de dominio.
     *
     * @param crearUsuarioDto DTO con los datos del usuario a crear
     * @return Usuario del modelo de dominio
     */
    public Usuario toUsuario(CrearUsuarioDto crearUsuarioDto) {
        if (crearUsuarioDto == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(crearUsuarioDto.nombre());
        usuario.setApellido(crearUsuarioDto.apellido());
        usuario.setEmail(crearUsuarioDto.email());
        usuario.setDocumentoIdentidad(crearUsuarioDto.documentoIdentidad());
        usuario.setTelefono(crearUsuarioDto.telefono());
        usuario.setRolId(crearUsuarioDto.rolId());
        usuario.setSalarioBase(crearUsuarioDto.salarioBase());

        return usuario;
    }


}
