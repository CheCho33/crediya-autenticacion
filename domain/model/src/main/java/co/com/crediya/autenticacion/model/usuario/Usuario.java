package co.com.crediya.autenticacion.model.usuario;

import lombok.*;

/**
 * Entidad que representa un Usuario en el sistema de autenticación CrediYa.
 * Un usuario es una persona que puede registrarse, autenticarse y acceder
 * a las funcionalidades del sistema según su rol.
 * 
 * Esta entidad es inmutable y protege sus invariantes a través de Value Objects.
 * Según las especificaciones del microservicio, los usuarios pueden tener
 * diferentes roles: ADMIN, ASESOR, CLIENTE.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class Usuario {
    
    private Long usuarioId ;
    private String nombre;
    private String apellido;
    private String email;
    private String documentoIdentidad;
    private String telefono;
    private Long  rolId;
    private Double salarioBase;
    private String contrasena;

}
