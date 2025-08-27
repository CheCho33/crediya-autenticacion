package co.com.crediya.autenticacion.r2dbc.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entidad de base de datos que representa un Usuario en el sistema de autenticación CrediYa.
 * Mapea la tabla 'usuario' de la base de datos PostgreSQL usando Spring Data R2DBC.
 */
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioEntity {

    @Id
    @Column("usuario_id")
    private Long usuarioId;

    @Column("nombre")
    private String nombre;

    @Column("apellido")
    private String apellido;

    @Column("email")
    private String email;

    @Column("documento_identidad")
    private String documentoIdentidad;

    @Column("telefono")
    private String telefono;

    @Column("rol_id")
    private Long rolId;

    @Column("salario_base")
    private Double salarioBase;
}
