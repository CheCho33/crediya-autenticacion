package co.com.crediya.autenticacion.api.dto;


import jakarta.validation.constraints.*;


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

        @DecimalMin(value = "1.0", inclusive = true, message = "el salario base debe ser mayor 0")
        @DecimalMax(value = "15000000.0", inclusive = true, message = "el salario base debe ser menor a 15.000.000")
        @NotNull
        Double salarioBase
) {
}
