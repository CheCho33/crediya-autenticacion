package co.com.crediya.autenticacion.api.dto;


import jakarta.validation.constraints.*;


public record CrearUsuarioDto(
        String nombre,
        String apellido,
        String email,
        String documentoIdentidad,
        String telefono,
        Long rolId,
        Double salarioBase
) {
}
