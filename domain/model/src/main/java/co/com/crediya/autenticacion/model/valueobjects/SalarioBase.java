package co.com.crediya.autenticacion.model.valueobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Value Object que representa el salario base de un Usuario.
 * Encapsula validaciones de negocio para garantizar la integridad del salario.
 */
public record SalarioBase(BigDecimal value) {
    
    private static final BigDecimal MIN_SALARIO = BigDecimal.ZERO;
    private static final BigDecimal MAX_SALARIO = new BigDecimal("15000000");
    private static final int SCALE = 2;
    
    public SalarioBase {
        if (value == null) {
            throw new IllegalArgumentException("El salario base no puede ser nulo");
        }
        
        if (value.compareTo(MIN_SALARIO) < 0) {
            throw new IllegalArgumentException("El salario base no puede ser negativo");
        }
        
        if (value.compareTo(MAX_SALARIO) > 0) {
            throw new IllegalArgumentException("El salario base no puede exceder " + MAX_SALARIO);
        }
        
        // Asegurar que tenga exactamente 2 decimales
        if (value.scale() != SCALE) {
            value = value.setScale(SCALE, RoundingMode.HALF_UP);
        }
    }
    
    /**
     * Crea un SalarioBase a partir de un BigDecimal.
     * 
     * @param salario BigDecimal que representa el salario base
     * @return Nueva instancia de SalarioBase
     */
    public static SalarioBase of(BigDecimal salario) {
        return new SalarioBase(salario);
    }
    
    /**
     * Crea un SalarioBase a partir de un String.
     * 
     * @param salario String que representa el salario base
     * @return Nueva instancia de SalarioBase
     */
    public static SalarioBase of(String salario) {
        try {
            return new SalarioBase(new BigDecimal(salario));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El formato del salario base no es válido");
        }
    }
    
    /**
     * Crea un SalarioBase a partir de un double.
     * 
     * @param salario double que representa el salario base
     * @return Nueva instancia de SalarioBase
     */
    public static SalarioBase of(double salario) {
        return new SalarioBase(BigDecimal.valueOf(salario));
    }
    
    /**
     * Verifica si el salario es mayor que cero.
     * 
     * @return true si el salario es mayor que cero
     */
    public boolean esMayorQueCero() {
        return value.compareTo(BigDecimal.ZERO) > 0;
    }
    
    /**
     * Verifica si el salario cumple con el mínimo requerido.
     * 
     * @param minimo Salario mínimo requerido
     * @return true si el salario cumple con el mínimo
     */
    public boolean cumpleMinimo(SalarioBase minimo) {
        return value.compareTo(minimo.value) >= 0;
    }
    
    /**
     * Obtiene el salario como BigDecimal con escala correcta.
     * 
     * @return Salario con escala de 2 decimales
     */
    public BigDecimal valor() {
        return value.setScale(SCALE, RoundingMode.HALF_UP);
    }
}
