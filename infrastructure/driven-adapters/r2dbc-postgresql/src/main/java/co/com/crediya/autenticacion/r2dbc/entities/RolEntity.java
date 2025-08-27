package co.com.crediya.autenticacion.r2dbc.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entidad de base de datos que representa un Rol en el sistema de autenticación CrediYa.
 * Mapea la tabla 'rol' de la base de datos PostgreSQL usando Spring Data R2DBC.
 */
@Table(name = "rol")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RolEntity {

    @Id
    @Column("rol_id")
    private Long rolId;

    @Column("nombre_rol")
    private String nombreRol;

    @Column("descripcion_rol")
    private String descripcionRol;
}
