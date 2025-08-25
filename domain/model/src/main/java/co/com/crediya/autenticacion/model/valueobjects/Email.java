package co.com.crediya.autenticacion.model.valueobjects;

import java.util.regex.Pattern;

/**
 * Value Object que representa el email de un Usuario.
 * Encapsula validaciones de negocio para garantizar la integridad del email.
 */
public record Email(String value) {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );
    private static final int MAX_LENGTH = 255;
    
    public Email {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El email del usuario no puede ser nulo o vacío");
        }
        
        String trimmedValue = value.trim().toLowerCase();
        if (trimmedValue.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("El email del usuario no puede exceder " + MAX_LENGTH + " caracteres");
        }
        
        if (!EMAIL_PATTERN.matcher(trimmedValue).matches()) {
            throw new IllegalArgumentException("El formato del email no es válido");
        }
    }
    
    /**
     * Crea un Email a partir de una cadena.
     * 
     * @param email Cadena que representa el email del usuario
     * @return Nueva instancia de Email
     */
    public static Email of(String email) {
        return new Email(email);
    }
    
    /**
     * Obtiene el dominio del email.
     * 
     * @return Dominio del email
     */
    public String domain() {
        return value.substring(value.indexOf('@') + 1);
    }
    
    /**
     * Obtiene el email en formato normalizado (trimmed y lowercase).
     * 
     * @return Email normalizado
     */
    public String normalized() {
        return value.trim().toLowerCase();
    }
}
