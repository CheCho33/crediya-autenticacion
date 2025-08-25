package co.com.crediya.autenticacion.model.valueobjects;

/**
 * Value Object que representa el teléfono de un Usuario.
 * Encapsula validaciones de negocio para garantizar la integridad del teléfono.
 */
public record Telefono(String value) {
    
    private static final int MIN_LENGTH = 7;
    private static final int MAX_LENGTH = 15;
    
    public Telefono {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El teléfono del usuario no puede ser nulo o vacío");
        }
        
        String trimmedValue = value.trim();
        if (trimmedValue.length() < MIN_LENGTH) {
            throw new IllegalArgumentException("El teléfono del usuario debe tener al menos " + MIN_LENGTH + " caracteres");
        }
        
        if (trimmedValue.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("El teléfono del usuario no puede exceder " + MAX_LENGTH + " caracteres");
        }
        
        // Validar que solo contenga números, espacios, guiones y paréntesis
        if (!trimmedValue.matches("^[0-9\\s\\-\\(\\)\\+]+$")) {
            throw new IllegalArgumentException("El teléfono del usuario contiene caracteres no permitidos");
        }
    }
    
    /**
     * Crea un Telefono a partir de una cadena.
     * 
     * @param telefono Cadena que representa el teléfono del usuario
     * @return Nueva instancia de Telefono
     */
    public static Telefono of(String telefono) {
        return new Telefono(telefono);
    }
    
    /**
     * Obtiene el teléfono en formato normalizado (trimmed).
     * 
     * @return Teléfono normalizado
     */
    public String normalized() {
        return value.trim();
    }
}
