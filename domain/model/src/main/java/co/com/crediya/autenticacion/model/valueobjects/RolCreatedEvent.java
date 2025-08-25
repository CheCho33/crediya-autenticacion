package co.com.crediya.autenticacion.model.valueobjects;

import java.time.Instant;

import co.com.crediya.autenticacion.model.rol.RolId;

/**
 * Evento de dominio que se publica cuando se crea un nuevo rol.
 * Este evento es inmutable y contiene solo los datos necesarios para
 * que otros servicios puedan reaccionar a la creación del rol.
 */
public record RolCreatedEvent(
    RolId rolId,
    NombreRol nombre,
    DescripcionRol descripcion,
    Instant occurredAt
) {
    
    public RolCreatedEvent {
        if (rolId == null) {
            throw new IllegalArgumentException("El ID del rol no puede ser nulo");
        }
        if (nombre == null) {
            throw new IllegalArgumentException("El nombre del rol no puede ser nulo");
        }
        if (descripcion == null) {
            throw new IllegalArgumentException("La descripción del rol no puede ser nula");
        }
        if (occurredAt == null) {
            throw new IllegalArgumentException("La fecha de ocurrencia no puede ser nula");
        }
    }
    
    /**
     * Crea un evento de rol creado con la fecha actual.
     * 
     * @param rolId Identificador del rol creado
     * @param nombre Nombre del rol creado
     * @param descripcion Descripción del rol creado
     * @return Nueva instancia del evento
     */
    public static RolCreatedEvent of(RolId rolId, NombreRol nombre, DescripcionRol descripcion) {
        return new RolCreatedEvent(rolId, nombre, descripcion, Instant.now());
    }
    
    /**
     * Crea un evento de rol creado con una fecha específica.
     * 
     * @param rolId Identificador del rol creado
     * @param nombre Nombre del rol creado
     * @param descripcion Descripción del rol creado
     * @param occurredAt Fecha de ocurrencia del evento
     * @return Nueva instancia del evento
     */
    public static RolCreatedEvent of(RolId rolId, NombreRol nombre, DescripcionRol descripcion, Instant occurredAt) {
        return new RolCreatedEvent(rolId, nombre, descripcion, occurredAt);
    }
}
