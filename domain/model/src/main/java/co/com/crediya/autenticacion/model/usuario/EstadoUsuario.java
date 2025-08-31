package co.com.crediya.autenticacion.model.usuario;

/**
 * Enum que representa los posibles estados de un Usuario en el sistema.
 * Según las especificaciones del microservicio de autenticación,
 * los usuarios pueden estar en diferentes estados que determinan
 * si pueden autenticarse y acceder al sistema.
 */
public enum EstadoUsuario {
    
    ACTIVO("ACTIVO"),
    INACTIVO("INACTIVO"),
    SUSPENDIDO("SUSPENDIDO"),
    ELIMINADO("ELIMINADO");
    
    private final String valor;
    
    EstadoUsuario(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
    
    /**
     * Obtiene el EstadoUsuario a partir de un String.
     * 
     * @param valor String que representa el estado
     * @return EstadoUsuario correspondiente
     * @throws IllegalArgumentException si el valor no corresponde a ningún estado
     */
    public static EstadoUsuario fromString(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El valor del estado no puede ser nulo o vacío");
        }
        
        for (EstadoUsuario estado : values()) {
            if (estado.valor.equalsIgnoreCase(valor.trim())) {
                return estado;
            }
        }
        
        throw new IllegalArgumentException("Estado de usuario no válido: " + valor);
    }
    
    @Override
    public String toString() {
        return valor;
    }
}
