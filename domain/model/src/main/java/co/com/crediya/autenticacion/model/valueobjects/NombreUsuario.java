package co.com.crediya.autenticacion.model.valueobjects;

/**
 * Value Object que representa el nombre de un Usuario.
 * Encapsula validaciones de negocio para garantizar la integridad del nombre.
 */
public record NombreUsuario(String value) {
    
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 100;
    
    public NombreUsuario {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El nombre del usuario no puede ser nulo o vacío");
        }
        
        String trimmedValue = value.trim();
        if (trimmedValue.length() < MIN_LENGTH) {
            throw new IllegalArgumentException("El nombre del usuario debe tener al menos " + MIN_LENGTH + " caracteres");
        }
        
        if (trimmedValue.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("El nombre del usuario no puede exceder " + MAX_LENGTH + " caracteres");
        }
        
        // Validar que solo contenga letras, espacios y caracteres especiales comunes
        if (!trimmedValue.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            throw new IllegalArgumentException("El nombre del usuario contiene caracteres no permitidos");
        }
    }
    
    /**
     * Crea un NombreUsuario a partir de una cadena.
     * 
     * @param nombre Cadena que representa el nombre del usuario
     * @return Nueva instancia de NombreUsuario
     */
    public static NombreUsuario of(String nombre) {
        return new NombreUsuario(nombre);
    }
    
    /**
     * Obtiene el nombre en formato normalizado (trimmed).
     * 
     * @return Nombre normalizado
     */
    public String normalized() {
        return value.trim();
    }
}
