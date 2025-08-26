package co.com.crediya.autenticacion.model.exceptions;

/**
 * Excepción lanzada cuando se produce un conflicto en el dominio.
 * Se utiliza para errores como duplicados, estados concurrentes, etc.
 */
public class ConflictDomainException extends DomainException {
    
    /**
     * Constructor con mensaje y código de error.
     * 
     * @param message Mensaje descriptivo del conflicto
     * @param errorCode Código único del error de conflicto
     */
    public ConflictDomainException(String message, String errorCode) {
        super(message, errorCode);
    }
    
    /**
     * Constructor con mensaje, causa y código de error.
     * 
     * @param message Mensaje descriptivo del conflicto
     * @param cause Causa original del error
     * @param errorCode Código único del error de conflicto
     */
    public ConflictDomainException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }
}
