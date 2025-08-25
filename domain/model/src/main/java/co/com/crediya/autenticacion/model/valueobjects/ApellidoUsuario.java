package co.com.crediya.autenticacion.model.valueobjects;

/**
 * Value Object que representa el apellido de un Usuario.
 * Encapsula validaciones de negocio para garantizar la integridad del apellido.
 */
public record ApellidoUsuario(String value) {
    
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 100;
    
    public ApellidoUsuario {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El apellido del usuario no puede ser nulo o vacío");
        }
        
        String trimmedValue = value.trim();
        if (trimmedValue.length() < MIN_LENGTH) {
            throw new IllegalArgumentException("El apellido del usuario debe tener al menos " + MIN_LENGTH + " caracteres");
        }
        
        if (trimmedValue.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("El apellido del usuario no puede exceder " + MAX_LENGTH + " caracteres");
        }
        
        // Validar que solo contenga letras, espacios y caracteres especiales comunes
        if (!trimmedValue.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            throw new IllegalArgumentException("El apellido del usuario contiene caracteres no permitidos");
        }
    }
    
    /**
     * Crea un ApellidoUsuario a partir de una cadena.
     * 
     * @param apellido Cadena que representa el apellido del usuario
     * @return Nueva instancia de ApellidoUsuario
     */
    public static ApellidoUsuario of(String apellido) {
        return new ApellidoUsuario(apellido);
    }
    
    /**
     * Obtiene el apellido en formato normalizado (trimmed).
     * 
     * @return Apellido normalizado
     */
    public String normalized() {
        return value.trim();
    }
}
