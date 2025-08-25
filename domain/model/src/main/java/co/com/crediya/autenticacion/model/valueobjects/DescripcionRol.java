package co.com.crediya.autenticacion.model.valueobjects;

/**
 * Value Object que representa la descripción de un Rol.
 * Encapsula validaciones de negocio para garantizar la integridad de la descripción.
 */
public record DescripcionRol(String value) {
    
    private static final int MAX_LENGTH = 200;
    
    public DescripcionRol {
        if (value == null) {
            throw new IllegalArgumentException("La descripción del rol no puede ser nula");
        }
        
        String trimmedValue = value.trim();
        if (trimmedValue.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("La descripción del rol no puede exceder " + MAX_LENGTH + " caracteres");
        }
        
        // Validar que solo contenga caracteres válidos
        if (!trimmedValue.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9\\s\\-_.,!?()]+$")) {
            throw new IllegalArgumentException("La descripción del rol contiene caracteres no permitidos");
        }
    }
    
    /**
     * Crea un DescripcionRol a partir de una cadena.
     * 
     * @param descripcion Cadena que representa la descripción del rol
     * @return Nueva instancia de DescripcionRol
     */
    public static DescripcionRol of(String descripcion) {
        return new DescripcionRol(descripcion);
    }
    
    /**
     * Crea un DescripcionRol vacío.
     * 
     * @return Nueva instancia de DescripcionRol vacía
     */
    public static DescripcionRol empty() {
        return new DescripcionRol("");
    }
    
    /**
     * Verifica si la descripción está vacía.
     * 
     * @return true si la descripción está vacía
     */
    public boolean isEmpty() {
        return value.trim().isEmpty();
    }
    
    /**
     * Obtiene la descripción en formato normalizado (trimmed).
     * 
     * @return Descripción normalizada
     */
    public String normalized() {
        return value.trim();
    }
}
