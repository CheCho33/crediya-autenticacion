package co.com.crediya.autenticacion.model.usuario.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDto {

    private Long usuarioId ;
    private String nombre;
    private String apellido;
    private String email;
    private String documentoIdentidad;
    private String telefono;
    private Long  rolId;
    private Double salarioBase;

}
