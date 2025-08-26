package co.com.crediya.autenticacion.model.exceptions;

/**
 * Excepción base para todas las excepciones del dominio.
 * Proporciona una estructura común para el manejo de errores de negocio.
 */
public abstract class DomainException extends RuntimeException {
    
    private final String errorCode;
    
    /**
     * Constructor con mensaje y código de error.
     * 
     * @param message Mensaje descriptivo del error
     * @param errorCode Código único del error para identificación
     */
    protected DomainException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    /**
     * Constructor con mensaje, causa y código de error.
     * 
     * @param message Mensaje descriptivo del error
     * @param cause Causa original del error
     * @param errorCode Código único del error para identificación
     */
    protected DomainException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    /**
     * Obtiene el código de error.
     * 
     * @return Código único del error
     */
    public String getErrorCode() {
        return errorCode;
    }
}
