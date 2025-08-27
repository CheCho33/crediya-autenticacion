package co.com.crediya.autenticacion.api.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Order(-2)
@Slf4j
public class GlobalErrorHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        // Determinar el código de estado HTTP apropiado
        HttpStatus status = determineHttpStatus(ex);

        // Crear mensaje de error estructurado
        String errorMessage = createErrorMessage(ex, status);

        // Configurar headers de respuesta
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // Log del error
        log.error("Error en endpoint: {} - Status: {} - Error: {}",
                exchange.getRequest().getPath(), status, ex.getMessage());

        // Escribir respuesta de error
        DataBuffer buffer = response.bufferFactory()
                .wrap(errorMessage.getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Mono.just(buffer));
    }

    /**
     * Determina el código de estado HTTP apropiado basado en el tipo de excepción.
     */
    private HttpStatus determineHttpStatus(Throwable ex) {
        if (ex instanceof WebExchangeBindException) {
            return HttpStatus.BAD_REQUEST;
        } else if (ex instanceof IllegalArgumentException) {
            return HttpStatus.BAD_REQUEST;
        } else if (ex instanceof IllegalStateException) {
            return HttpStatus.CONFLICT;
        } else if (ex instanceof RuntimeException) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    /**
     * Crea un mensaje de error estructurado en formato JSON.
     */
    private String createErrorMessage(Throwable ex, HttpStatus status) {
        try {
            ErrorResponseDto errorResponse = new ErrorResponseDto(
                    Instant.now(),
                    status.value(),
                    status.getReasonPhrase(),
                    getErrorMessage(ex),
                    getValidationErrors(ex)
            );

            return objectMapper.writeValueAsString(errorResponse);
        } catch (JsonProcessingException e) {
            // Fallback en caso de error de serialización
            return String.format("""
                    {
                        "timestamp": "%s",
                        "error": "%s",
                        "message": "%s"
                    }
                    """,
                    Instant.now(),
                    status.value(),
                    ex.getMessage() != null ? ex.getMessage() : "Error interno del servidor"
            );
        }
    }

    /**
     * Obtiene el mensaje de error apropiado según el tipo de excepción.
     */
    private String getErrorMessage(Throwable ex) {
        if (ex instanceof WebExchangeBindException) {
            return "Error de validación en los datos de entrada";
        } else if (ex instanceof IllegalArgumentException) {
            return ex.getMessage();
        } else if (ex instanceof RuntimeException) {
            return ex.getMessage() != null ? ex.getMessage() : "Error interno del servidor";
        } else {
            return "Error interno del servidor";
        }
    }

    /**
     * Extrae los errores de validación específicos de WebExchangeBindException.
     */
    private Map<String, String> getValidationErrors(Throwable ex) {
        Map<String, String> validationErrors = new HashMap<>();

        if (ex instanceof WebExchangeBindException bindException) {
            validationErrors = bindException.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            fieldError -> {
                                // Manejo específico para salarioBase
                                if ("salarioBase".equals(fieldError.getField())) {
                                    return getSalarioBaseErrorMessage(fieldError);
                                }
                                return fieldError.getDefaultMessage() != null ? 
                                       fieldError.getDefaultMessage() : 
                                       "Campo inválido";
                            }
                    ));
        }

        return validationErrors;
    }

    /**
     * Obtiene mensajes de error específicos para el campo salarioBase.
     */
    private String getSalarioBaseErrorMessage(FieldError fieldError) {
        String code = fieldError.getCode();
        if (code != null) {
            switch (code) {
                case "DecimalMin":
                    return "El salario base debe ser mayor a 0";
                case "DecimalMax":
                    return "El salario base debe ser menor a 15,000,000";
                case "NotNull":
                    return "El salario base es obligatorio";
                default:
                    return fieldError.getDefaultMessage() != null ? 
                           fieldError.getDefaultMessage() : 
                           "El salario base tiene un valor inválido";
            }
        }
        return fieldError.getDefaultMessage() != null ? 
               fieldError.getDefaultMessage() : 
               "El salario base tiene un valor inválido";
    }

    /**
     * DTO para respuestas de error estructuradas.
     */
    public static class ErrorResponseDto {
        private final Instant timestamp;
        private final int error;
        private final String status;
        private final String message;
        private final Map<String, String> validationErrors;

        public ErrorResponseDto(Instant timestamp, int error, String status, 
                              String message, Map<String, String> validationErrors) {
            this.timestamp = timestamp;
            this.error = error;
            this.status = status;
            this.message = message;
            this.validationErrors = validationErrors;
        }

        // Getters
        public Instant getTimestamp() { return timestamp; }
        public int getError() { return error; }
        public String getStatus() { return status; }
        public String getMessage() { return message; }
        public Map<String, String> getValidationErrors() { return validationErrors; }
    }
}

