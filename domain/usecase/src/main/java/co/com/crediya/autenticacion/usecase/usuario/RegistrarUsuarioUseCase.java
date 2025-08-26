package co.com.crediya.autenticacion.usecase.usuario;

import java.util.UUID;

import co.com.crediya.autenticacion.model.exceptions.ConflictDomainException;
import co.com.crediya.autenticacion.model.exceptions.DomainException;
import co.com.crediya.autenticacion.model.exceptions.ValidationDomainException;
import co.com.crediya.autenticacion.model.rol.RolId;
import co.com.crediya.autenticacion.model.usuario.Usuario;
import co.com.crediya.autenticacion.model.usuario.gateways.UsuarioRepository;
import co.com.crediya.autenticacion.model.valueobjects.ApellidoUsuario;
import co.com.crediya.autenticacion.model.valueobjects.DocumentoIdentidad;
import co.com.crediya.autenticacion.model.valueobjects.Email;
import co.com.crediya.autenticacion.model.valueobjects.NombreUsuario;
import co.com.crediya.autenticacion.model.valueobjects.SalarioBase;
import co.com.crediya.autenticacion.model.valueobjects.Telefono;
import co.com.crediya.autenticacion.model.valueobjects.UsuarioId;
import reactor.core.publisher.Mono;

/**
 * Implementación del caso de uso para registrar un nuevo usuario.
 * 
 * Este servicio implementa las reglas de negocio para el registro de usuarios
 * siguiendo los principios de arquitectura hexagonal y programación reactiva.
 */
public class RegistrarUsuarioUseCase {
    
    private final UsuarioRepository usuarioRepository;
    
    public RegistrarUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    public Mono<Usuario> registrar(DatosRegistroUsuario datos) {
        return Mono.just(datos)
            .flatMap(this::validarDatos)
            .flatMap(this::validarUnicidadEmail)
            .flatMap(this::crearUsuario)
            .flatMap(usuarioRepository::guardar)
            .onErrorMap(this::mapearExcepciones);
    }
    
    /**
     * Valida los datos de entrada del usuario.
     * 
     * @param datos Datos a validar
     * @return Mono con los datos validados
     */
    private Mono<DatosRegistroUsuario> validarDatos(DatosRegistroUsuario datos) {
        return Mono.fromCallable(() -> {
            try {
                // Las validaciones básicas ya están en el constructor del record
                // Aquí se pueden agregar validaciones adicionales si es necesario
                return datos;
            } catch (IllegalArgumentException e) {
                throw new ValidationDomainException(e.getMessage(), "usuario.datos.invalidos");
            }
        });
    }
    
    /**
     * Valida que el email no esté previamente registrado.
     * 
     * @param datos Datos del usuario
     * @return Mono con los datos si el email es único
     */
    private Mono<DatosRegistroUsuario> validarUnicidadEmail(DatosRegistroUsuario datos) {
        return usuarioRepository.existePorEmail(Email.of(datos.email()))
            .flatMap(existe -> {
                if (existe) {
                    return Mono.error(new ConflictDomainException(
                        "El correo electrónico ya está registrado en el sistema",
                        "usuario.email.duplicado"
                    ));
                }
                return Mono.just(datos);
            });
    }
    
    /**
     * Crea la entidad Usuario a partir de los datos validados.
     * 
     * @param datos Datos validados del usuario
     * @return Mono con el usuario creado
     */
    private Mono<Usuario> crearUsuario(DatosRegistroUsuario datos) {
        return Mono.fromCallable(() -> {
            try {
                // Crear value objects
                UsuarioId id = UsuarioId.of(UUID.randomUUID());
                NombreUsuario nombre = NombreUsuario.of(datos.nombres());
                ApellidoUsuario apellido = ApellidoUsuario.of(datos.apellidos());
                Email email = Email.of(datos.email());
                DocumentoIdentidad documento = DocumentoIdentidad.of(datos.documento_identidad());
                Telefono telefono = Telefono.of(datos.telefono());
                SalarioBase salario = SalarioBase.of(datos.salario_base());
                RolId rolId = RolId.of(UUID.randomUUID()); // TODO: Mapear según rol
                

                // Crear usuario
                return Usuario.create(id, nombre, apellido, email, documento, telefono, rolId, salario);
                
            } catch (IllegalArgumentException e) {
                throw new ValidationDomainException(e.getMessage(), "usuario.creacion.invalida");
            }
        });
    }
    
    /**
     * Mapea las excepciones del dominio a excepciones específicas.
     * 
     * @param error Error original
     * @return Excepción mapeada
     */
    private Throwable mapearExcepciones(Throwable error) {
        if (error instanceof DomainException) {
            return error;
        }
        
        // Mapear excepciones técnicas a excepciones de dominio
        if (error instanceof IllegalArgumentException) {
            return new ValidationDomainException(error.getMessage(), "usuario.validacion.error");
        }
        
        // Para otras excepciones, mantener el error original
        return error;
    }
}
