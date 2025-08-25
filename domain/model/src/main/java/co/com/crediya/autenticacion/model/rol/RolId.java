package co.com.crediya.autenticacion.model.rol;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * Value Object que representa el identificador único de un Rol.
 * Encapsula un UUID y proporciona validaciones para garantizar su integridad.
 */
public record RolId(UUID value) {
    
    public RolId {
        if (value == null) {
            throw new IllegalArgumentException("El UUID del rol no puede ser nulo");
        }
    }
    
    /**
     * Crea un nuevo RolId usando el generador de UUID proporcionado.
     * 
     * @param generator Función que genera UUIDs
     * @return Nueva instancia de RolId
     */
    public static RolId newId(Supplier<UUID> generator) {
        return new RolId(generator.get());
    }
    
    /**
     * Crea un RolId a partir de un UUID existente.
     * 
     * @param uuid UUID existente
     * @return Nueva instancia de RolId
     */
    public static RolId of(UUID uuid) {
        return new RolId(uuid);
    }
    
    /**
     * Crea un RolId a partir de una cadena UUID.
     * 
     * @param uuidString Cadena que representa un UUID
     * @return Nueva instancia de RolId
     * @throws IllegalArgumentException si la cadena no es un UUID válido
     */
    public static RolId fromString(String uuidString) {
        if (uuidString == null || uuidString.isBlank()) {
            throw new IllegalArgumentException("La cadena UUID no puede ser nula o vacía");
        }
        try {
            return new RolId(UUID.fromString(uuidString));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Formato de UUID inválido: " + uuidString);
        }
    }
}
