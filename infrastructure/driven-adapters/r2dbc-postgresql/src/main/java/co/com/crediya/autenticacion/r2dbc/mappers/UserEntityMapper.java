package co.com.crediya.autenticacion.r2dbc.mappers;

import co.com.crediya.autenticacion.model.usuario.Usuario;
import co.com.crediya.autenticacion.r2dbc.entities.UsuarioEntity;

/**
 * Mapper para convertir entre Usuario (modelo de dominio) y UsuarioEntity (entidad de base de datos).
 * Facilita la conversión bidireccional entre las capas de dominio e infraestructura.
 */
public class UserEntityMapper {

    /**
     * Convierte una UsuarioEntity (entidad de base de datos) a Usuario (modelo de dominio).
     *
     * @param usuarioEntity la entidad de base de datos a convertir
     * @return el objeto de dominio Usuario, o null si la entidad es null
     */
    public static Usuario toDomain(UsuarioEntity usuarioEntity) {
        if (usuarioEntity == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setUsuarioId(usuarioEntity.getUsuarioId());
        usuario.setNombre(usuarioEntity.getNombre());
        usuario.setApellido(usuarioEntity.getApellido());
        usuario.setEmail(usuarioEntity.getEmail());
        usuario.setDocumentoIdentidad(usuarioEntity.getDocumentoIdentidad());
        usuario.setTelefono(usuarioEntity.getTelefono());
        usuario.setRolId(usuarioEntity.getRolId());
        usuario.setSalarioBase(usuarioEntity.getSalarioBase());

        return usuario;
    }

    /**
     * Convierte un Usuario (modelo de dominio) a UsuarioEntity (entidad de base de datos).
     *
     * @param usuario el objeto de dominio a convertir
     * @return la entidad de base de datos UsuarioEntity, o null si el usuario es null
     */
    public static UsuarioEntity toEntity(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return UsuarioEntity.builder()
                .usuarioId(usuario.getUsuarioId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .documentoIdentidad(usuario.getDocumentoIdentidad())
                .telefono(usuario.getTelefono())
                .rolId(usuario.getRolId())
                .salarioBase(usuario.getSalarioBase())
                .build();
    }
}
