package co.com.crediya.autenticacion.r2dbc.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "sesion")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SesionEntity {

    @Id
    @Column("sesion_id")
    private Long sesionId;

    @Column("usuario_id")
    private Long usuarioId;

    @Column("token")
    private String token;

    // @Column("fecha_expiracion")
    // private Date fechaExpiracion;

}
