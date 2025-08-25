package co.com.crediya.autenticacion.model.valueobjects;

/**
 * Value Object que representa el nombre de un Rol.
 * Encapsula validaciones de negocio para garantizar la integridad del nombre.
 */
public record NombreRol(String value) {
    
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 50;
    
    public NombreRol {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El nombre del rol no puede ser nulo o vacío");
        }
        
        String trimmedValue = value.trim();
        if (trimmedValue.length() < MIN_LENGTH) {
            throw new IllegalArgumentException("El nombre del rol debe tener al menos " + MIN_LENGTH + " caracteres");
        }
        
        if (trimmedValue.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("El nombre del rol no puede exceder " + MAX_LENGTH + " caracteres");
        }
        
        // Validar que solo contenga letras, números, espacios y caracteres especiales comunes
        if (!trimmedValue.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9\\s\\-_]+$")) {
            throw new IllegalArgumentException("El nombre del rol contiene caracteres no permitidos");
        }
    }
    
    /**
     * Crea un NombreRol a partir de una cadena.
     * 
     * @param nombre Cadena que representa el nombre del rol
     * @return Nueva instancia de NombreRol
     */
    public static NombreRol of(String nombre) {
        return new NombreRol(nombre);
    }
    
    /**
     * Verifica si el nombre es igual a otro ignorando mayúsculas/minúsculas.
     * 
     * @param otro NombreRol a comparar
     * @return true si son iguales ignorando case
     */
    public boolean equalsIgnoreCase(NombreRol otro) {
        return this.value.equalsIgnoreCase(otro.value);
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
