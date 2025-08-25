package co.com.crediya.autenticacion.model.usuario;

import java.time.Instant;

import co.com.crediya.autenticacion.model.rol.RolId;
import co.com.crediya.autenticacion.model.valueobjects.ApellidoUsuario;
import co.com.crediya.autenticacion.model.valueobjects.DocumentoIdentidad;
import co.com.crediya.autenticacion.model.valueobjects.Email;
import co.com.crediya.autenticacion.model.valueobjects.NombreUsuario;
import co.com.crediya.autenticacion.model.valueobjects.SalarioBase;
import co.com.crediya.autenticacion.model.valueobjects.Telefono;
import co.com.crediya.autenticacion.model.valueobjects.UsuarioId;

/**
 * Evento de dominio que se publica cuando se crea un nuevo usuario.
 * Este evento es inmutable y contiene solo los datos necesarios para
 * que otros servicios puedan reaccionar a la creación del usuario.
 */
public record UsuarioCreatedEvent(
    UsuarioId usuarioId,
    NombreUsuario nombre,
    ApellidoUsuario apellido,
    Email email,
    DocumentoIdentidad documentoIdentidad,
    Telefono telefono,
    RolId rolId,
    SalarioBase salarioBase,
    EstadoUsuario estado,
    Instant occurredAt
) {
    
    public UsuarioCreatedEvent {
        if (usuarioId == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo");
        }
        if (nombre == null) {
            throw new IllegalArgumentException("El nombre del usuario no puede ser nulo");
        }
        if (apellido == null) {
            throw new IllegalArgumentException("El apellido del usuario no puede ser nulo");
        }
        if (email == null) {
            throw new IllegalArgumentException("El email del usuario no puede ser nulo");
        }
        if (documentoIdentidad == null) {
            throw new IllegalArgumentException("El documento de identidad del usuario no puede ser nulo");
        }
        if (telefono == null) {
            throw new IllegalArgumentException("El teléfono del usuario no puede ser nulo");
        }
        if (rolId == null) {
            throw new IllegalArgumentException("El rol del usuario no puede ser nulo");
        }
        if (salarioBase == null) {
            throw new IllegalArgumentException("El salario base del usuario no puede ser nulo");
        }
        if (estado == null) {
            throw new IllegalArgumentException("El estado del usuario no puede ser nulo");
        }
        if (occurredAt == null) {
            throw new IllegalArgumentException("La fecha de ocurrencia no puede ser nula");
        }
    }
    
    /**
     * Crea un evento de usuario creado con la fecha actual.
     * 
     * @param usuarioId Identificador del usuario creado
     * @param nombre Nombre del usuario creado
     * @param apellido Apellido del usuario creado
     * @param email Email del usuario creado
     * @param documentoIdentidad Documento de identidad del usuario creado
     * @param telefono Teléfono del usuario creado
     * @param rolId Rol del usuario creado
     * @param salarioBase Salario base del usuario creado
     * @param estado Estado del usuario creado
     * @return Nueva instancia del evento
     */
    public static UsuarioCreatedEvent of(UsuarioId usuarioId, NombreUsuario nombre, ApellidoUsuario apellido,
                                       Email email, DocumentoIdentidad documentoIdentidad, Telefono telefono,
                                       RolId rolId, SalarioBase salarioBase, EstadoUsuario estado) {
        return new UsuarioCreatedEvent(usuarioId, nombre, apellido, email, documentoIdentidad,
                                      telefono, rolId, salarioBase, estado, Instant.now());
    }
    
    /**
     * Crea un evento de usuario creado con una fecha específica.
     * 
     * @param usuarioId Identificador del usuario creado
     * @param nombre Nombre del usuario creado
     * @param apellido Apellido del usuario creado
     * @param email Email del usuario creado
     * @param documentoIdentidad Documento de identidad del usuario creado
     * @param telefono Teléfono del usuario creado
     * @param rolId Rol del usuario creado
     * @param salarioBase Salario base del usuario creado
     * @param estado Estado del usuario creado
     * @param occurredAt Fecha de ocurrencia del evento
     * @return Nueva instancia del evento
     */
    public static UsuarioCreatedEvent of(UsuarioId usuarioId, NombreUsuario nombre, ApellidoUsuario apellido,
                                       Email email, DocumentoIdentidad documentoIdentidad, Telefono telefono,
                                       RolId rolId, SalarioBase salarioBase, EstadoUsuario estado, Instant occurredAt) {
        return new UsuarioCreatedEvent(usuarioId, nombre, apellido, email, documentoIdentidad,
                                      telefono, rolId, salarioBase, estado, occurredAt);
    }
}
