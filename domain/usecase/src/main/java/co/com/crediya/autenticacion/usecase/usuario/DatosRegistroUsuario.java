package co.com.crediya.autenticacion.usecase.usuario;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * DTO que contiene los datos necesarios para registrar un nuevo usuario.
 * Representa la información requerida para crear un usuario en el sistema.
 * 
 * Campos según especificaciones del microservicio:
 * - nombres: string
 * - apellidos: string
 * - email: string
 * - documento_identidad: string
 * - telefono: string
 * - salario_base: number
 * - rol: string
 */
public record DatosRegistroUsuario(
    String nombres,
    String apellidos,
    String email,
    String documento_identidad,
    String telefono,
    BigDecimal salario_base,
    String rol
) {
    
    // Patrón para validación de email
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );
    
    // Patrón para validación de teléfono (formato colombiano)
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^\\+?57?[1-9][0-9]{9}$"
    );
    
    /**
     * Constructor con validaciones básicas.
     * 
     * @param nombres Nombres del usuario
     * @param apellidos Apellidos del usuario
     * @param email Email del usuario
     * @param documento_identidad Documento de identidad del usuario
     * @param telefono Teléfono del usuario
     * @param salario_base Salario base del usuario
     * @param rol Rol del usuario (ADMIN, ASESOR, CLIENTE)
     */
    public DatosRegistroUsuario {
        // Validaciones básicas de nulidad
        if (nombres == null || nombres.isBlank()) {
            throw new IllegalArgumentException("Los nombres del usuario no pueden ser nulos o vacíos");
        }
        if (apellidos == null || apellidos.isBlank()) {
            throw new IllegalArgumentException("Los apellidos del usuario no pueden ser nulos o vacíos");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El email del usuario no puede ser nulo o vacío");
        }
        if (documento_identidad == null || documento_identidad.isBlank()) {
            throw new IllegalArgumentException("El documento de identidad del usuario no puede ser nulo o vacío");
        }
        if (telefono == null || telefono.isBlank()) {
            throw new IllegalArgumentException("El teléfono del usuario no puede ser nulo o vacío");
        }
        if (salario_base == null) {
            throw new IllegalArgumentException("El salario base del usuario no puede ser nulo");
        }
        if (rol == null || rol.isBlank()) {
            throw new IllegalArgumentException("El rol del usuario no puede ser nulo o vacío");
        }
        
        // Validación de formato de email
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("El formato del email no es válido");
        }
        
        // Validación de formato de teléfono
        if (!PHONE_PATTERN.matcher(telefono).matches()) {
            throw new IllegalArgumentException("El formato del teléfono no es válido. Use formato colombiano: +573001234567");
        }

        
        // Validación de salario base
        if (salario_base.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El salario base no puede ser negativo");
        }
        if (salario_base.compareTo(new BigDecimal("15000000")) > 0) {
            throw new IllegalArgumentException("El salario base no puede exceder 15,000,000");
        }
        
        // Validación de rol
        if (!rol.equals("ADMIN") && !rol.equals("ASESOR") && !rol.equals("CLIENTE")) {
            throw new IllegalArgumentException("El rol debe ser ADMIN, ASESOR o CLIENTE");
        }
    }
}
