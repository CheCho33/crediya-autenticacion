package co.com.crediya.autenticacion.api.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record CrearUsuarioDto(
        @NotBlank
        String nombre,

        @NotBlank
        String apellido,

        @Email
        @NotBlank
        String email,

        @NotBlank
        String documentoIdentidad,

        @NotBlank
        String telefono,

        @NotNull
        Long rolId,

        @NotNull
        Double salarioBase
) {
}
