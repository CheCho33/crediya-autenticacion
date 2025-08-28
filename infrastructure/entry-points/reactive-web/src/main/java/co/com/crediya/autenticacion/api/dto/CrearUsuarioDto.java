package co.com.crediya.autenticacion.api.dto;


import jakarta.validation.constraints.*;


public record CrearUsuarioDto(
        @NotBlank
        String nombre,

        @NotBlank
        String apellido,

        @Email(message = "El correo electrónico no tiene un formato válido")
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
