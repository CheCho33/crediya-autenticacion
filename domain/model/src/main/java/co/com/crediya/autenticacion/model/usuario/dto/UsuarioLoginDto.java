package co.com.crediya.autenticacion.model.usuario.dto;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioLoginDto {
    String email;
    String password;
}
