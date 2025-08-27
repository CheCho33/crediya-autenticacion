package co.com.crediya.autenticacion.model.rol;

import lombok.*;

/**
 * Entidad que representa un Rol en el sistema de autenticación CrediYa.
 * Un rol define los permisos y responsabilidades que puede tener un usuario.
 * 
 * Esta entidad es inmutable y protege sus invariantes a través de Value Objects.
 * Según las especificaciones del microservicio, los roles principales son:
 * - ADMIN: Administrador del sistema
 * - ASESOR: Asesor financiero
 * - CLIENTE: Cliente del sistema
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class Rol {
    
    private Long rolId;
    private String nombreRol;
    private String descripcionRol;
}
