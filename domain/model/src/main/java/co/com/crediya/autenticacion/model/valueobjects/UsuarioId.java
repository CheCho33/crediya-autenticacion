package co.com.crediya.autenticacion.model.valueobjects;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * Value Object que representa el identificador único de un Usuario.
 * Encapsula un UUID y proporciona validaciones para garantizar su integridad.
 */
public record UsuarioId(UUID value) {
    
    public UsuarioId {
        if (value == null) {
            throw new IllegalArgumentException("El UUID del usuario no puede ser nulo");
        }
    }
    
    /**
     * Crea un nuevo UsuarioId usando el generador de UUID proporcionado.
     * 
     * @param generator Función que genera UUIDs
     * @return Nueva instancia de UsuarioId
     */
    public static UsuarioId newId(Supplier<UUID> generator) {
        return new UsuarioId(generator.get());
    }
    
    /**
     * Crea un UsuarioId a partir de un UUID existente.
     * 
     * @param uuid UUID existente
     * @return Nueva instancia de UsuarioId
     */
    public static UsuarioId of(UUID uuid) {
        return new UsuarioId(uuid);
    }
    
    /**
     * Crea un UsuarioId a partir de una cadena UUID.
     * 
     * @param uuidString Cadena que representa un UUID
     * @return Nueva instancia de UsuarioId
     * @throws IllegalArgumentException si la cadena no es un UUID válido
     */
    public static UsuarioId fromString(String uuidString) {
        if (uuidString == null || uuidString.isBlank()) {
            throw new IllegalArgumentException("La cadena UUID no puede ser nula o vacía");
        }
        try {
            return new UsuarioId(UUID.fromString(uuidString));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Formato de UUID inválido: " + uuidString);
        }
    }
}
