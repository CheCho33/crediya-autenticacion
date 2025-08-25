package co.com.crediya.autenticacion.model.valueobjects;

/**
 * Value Object que representa el documento de identidad de un Usuario.
 * Encapsula validaciones de negocio para garantizar la integridad del documento.
 */
public record DocumentoIdentidad(String value) {
    
    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 20;
    
    public DocumentoIdentidad {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El documento de identidad no puede ser nulo o vacío");
        }
        
        String trimmedValue = value.trim();
        if (trimmedValue.length() < MIN_LENGTH) {
            throw new IllegalArgumentException("El documento de identidad debe tener al menos " + MIN_LENGTH + " caracteres");
        }
        
        if (trimmedValue.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("El documento de identidad no puede exceder " + MAX_LENGTH + " caracteres");
        }
        
        // Validar que solo contenga números y letras
        if (!trimmedValue.matches("^[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException("El documento de identidad contiene caracteres no permitidos");
        }
    }
    
    /**
     * Crea un DocumentoIdentidad a partir de una cadena.
     * 
     * @param documento Cadena que representa el documento de identidad
     * @return Nueva instancia de DocumentoIdentidad
     */
    public static DocumentoIdentidad of(String documento) {
        return new DocumentoIdentidad(documento);
    }
    
    /**
     * Obtiene el documento en formato normalizado (trimmed y uppercase).
     * 
     * @return Documento normalizado
     */
    public String normalized() {
        return value.trim().toUpperCase();
    }
}
