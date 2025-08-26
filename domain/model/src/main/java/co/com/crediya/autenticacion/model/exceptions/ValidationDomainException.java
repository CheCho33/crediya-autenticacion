package co.com.crediya.autenticacion.model.exceptions;

/**
 * Excepción lanzada cuando se violan las validaciones de entrada o formato.
 * Se utiliza para errores de validación estructural en el dominio.
 */
public class ValidationDomainException extends DomainException {
    
    /**
     * Constructor con mensaje y código de error.
     * 
     * @param message Mensaje descriptivo del error de validación
     * @param errorCode Código único del error de validación
     */
    public ValidationDomainException(String message, String errorCode) {
        super(message, errorCode);
    }
    
    /**
     * Constructor con mensaje, causa y código de error.
     * 
     * @param message Mensaje descriptivo del error de validación
     * @param cause Causa original del error
     * @param errorCode Código único del error de validación
     */
    public ValidationDomainException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }
}
