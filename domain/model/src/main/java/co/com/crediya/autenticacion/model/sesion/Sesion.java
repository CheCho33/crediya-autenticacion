package co.com.crediya.autenticacion.model.sesion;
import lombok.*;

import java.util.Date;
//import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Sesion {
    private Long sesionId ;
    private Long usuarioId;
    private String token;
    private Date fechaExpiracion;
}
