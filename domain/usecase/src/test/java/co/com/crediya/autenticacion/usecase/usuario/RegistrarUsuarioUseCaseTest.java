package co.com.crediya.autenticacion.usecase.usuario;

import co.com.crediya.autenticacion.model.exceptions.CrediYautentiateException;
import co.com.crediya.autenticacion.model.usuario.Usuario;
import co.com.crediya.autenticacion.model.usuario.gateways.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Test unitario para el caso de uso RegistrarUsuarioUseCase.
 * 
 * Este test se enfoca en verificar el flujo exitoso de registro de usuarios,
 * validando que el usuario se cree correctamente y se persista en el repositorio.
 */
@ExtendWith(MockitoExtension.class)
class RegistrarUsuarioUseCaseTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private RegistrarUsuarioUseCase registrarUsuarioUseCase;

    @BeforeEach
    void setUp() {
        registrarUsuarioUseCase = new RegistrarUsuarioUseCase(usuarioRepository);
    }

    @Test
    @DisplayName("Debería registrar un usuario exitosamente cuando todos los datos son válidos")
    void deberiaRegistrarUsuarioExitosamente() {
        // Given
        Usuario usuarioARegistrar = crearUsuarioValido();
        Usuario usuarioGuardado = crearUsuarioConId();

        when(usuarioRepository.existePorEmail(anyString())).thenReturn(Mono.just(false));
        when(usuarioRepository.guardar(any(Usuario.class))).thenReturn(Mono.just(usuarioGuardado));

        // When
        Mono<Usuario> resultado = registrarUsuarioUseCase.registrar(usuarioARegistrar);

        // Then
        StepVerifier.create(resultado)
                .expectNext(usuarioGuardado)
                .verifyComplete();
    }

    @Test
    @DisplayName("Debería fallar cuando el nombre es nulo")
    void deberiaFallarCuandoNombreEsNulo() {
        // Given
        Usuario usuarioInvalido = new Usuario();
        usuarioInvalido.setNombre(null);
        usuarioInvalido.setApellido("Apellido");
        usuarioInvalido.setEmail("test@test.com");
        usuarioInvalido.setDocumentoIdentidad("12345678");
        usuarioInvalido.setTelefono("3001234567");
        usuarioInvalido.setSalarioBase(100000.0);
        usuarioInvalido.setRolId(1L);

        // When
        Mono<Usuario> resultado = registrarUsuarioUseCase.registrar(usuarioInvalido);

        // Then
        StepVerifier.create(resultado)
                .expectErrorMatches(throwable ->
                    throwable instanceof CrediYautentiateException &&
                    throwable.getMessage().contains("Los nombres del usuario no pueden ser nulos o vacíos"))
                .verify();
    }

    @Test
    @DisplayName("Debería fallar cuando el apellido es nulo")
    void deberiaFallarCuandoApellidoEsNulo() {
        // Given
        Usuario usuarioInvalido = new Usuario();
        usuarioInvalido.setNombre("Nombre");
        usuarioInvalido.setApellido(null);
        usuarioInvalido.setEmail("test@test.com");
        usuarioInvalido.setDocumentoIdentidad("12345678");
        usuarioInvalido.setTelefono("3001234567");
        usuarioInvalido.setSalarioBase(100000.0);
        usuarioInvalido.setRolId(1L);

        // When
        Mono<Usuario> resultado = registrarUsuarioUseCase.registrar(usuarioInvalido);

        // Then
        StepVerifier.create(resultado)
                .expectErrorMatches(throwable ->
                    throwable instanceof CrediYautentiateException &&
                    throwable.getMessage().contains("Los apellidos del usuario no pueden ser nulos o vacíos"))
                .verify();
    }

    @Test
    @DisplayName("Debería fallar cuando el email es nulo")
    void deberiaFallarCuandoEmailEsNulo() {
        // Given
        Usuario usuarioInvalido = new Usuario();
        usuarioInvalido.setNombre("Nombre");
        usuarioInvalido.setApellido("Apellido");
        usuarioInvalido.setEmail(null);
        usuarioInvalido.setDocumentoIdentidad("12345678");
        usuarioInvalido.setTelefono("3001234567");
        usuarioInvalido.setSalarioBase(100000.0);
        usuarioInvalido.setRolId(1L);

        // When
        Mono<Usuario> resultado = registrarUsuarioUseCase.registrar(usuarioInvalido);

        // Then
        StepVerifier.create(resultado)
                .expectErrorMatches(throwable ->
                    throwable instanceof CrediYautentiateException &&
                    throwable.getMessage().contains("El email del usuario no puede ser nulo o vacío"))
                .verify();
    }

    @Test
    @DisplayName("Debería fallar cuando el email ya está registrado")
    void deberiaFallarCuandoEmailYaEstaRegistrado() {
        // Given
        Usuario usuarioARegistrar = crearUsuarioValido();

        when(usuarioRepository.existePorEmail(anyString())).thenReturn(Mono.just(true));

        // When
        Mono<Usuario> resultado = registrarUsuarioUseCase.registrar(usuarioARegistrar);

        // Then
        StepVerifier.create(resultado)
                .expectErrorMatches(throwable ->
                    throwable instanceof CrediYautentiateException &&
                    throwable.getMessage().contains("El email ya está registrado en el sistema"))
                .verify();
    }

    @Test
    @DisplayName("Debería fallar cuando el salario base es negativo")
    void deberiaFallarCuandoSalarioBaseEsNegativo() {
        // Given
        Usuario usuarioInvalido = new Usuario();
        usuarioInvalido.setNombre("Nombre");
        usuarioInvalido.setApellido("Apellido");
        usuarioInvalido.setEmail("test@test.com");
        usuarioInvalido.setDocumentoIdentidad("12345678");
        usuarioInvalido.setTelefono("3001234567");
        usuarioInvalido.setSalarioBase(-1000.0);
        usuarioInvalido.setRolId(1L);

        // When
        Mono<Usuario> resultado = registrarUsuarioUseCase.registrar(usuarioInvalido);

        // Then
        StepVerifier.create(resultado)
                .expectErrorMatches(throwable ->
                    throwable instanceof CrediYautentiateException &&
                    throwable.getMessage().contains("El salario base no puede ser negativo"))
                .verify();
    }

    @Test
    @DisplayName("Debería fallar cuando el salario base excede el límite")
    void deberiaFallarCuandoSalarioBaseExcedeLimite() {
        // Given
        Usuario usuarioInvalido = new Usuario();
        usuarioInvalido.setNombre("Nombre");
        usuarioInvalido.setApellido("Apellido");
        usuarioInvalido.setEmail("test@test.com");
        usuarioInvalido.setDocumentoIdentidad("12345678");
        usuarioInvalido.setTelefono("3001234567");
        usuarioInvalido.setSalarioBase(160000.0);
        usuarioInvalido.setRolId(1L);

        // When
        Mono<Usuario> resultado = registrarUsuarioUseCase.registrar(usuarioInvalido);

        // Then
        StepVerifier.create(resultado)
                .expectErrorMatches(throwable ->
                    throwable instanceof CrediYautentiateException &&
                    throwable.getMessage().contains("El salario base no puede exceder 15,000,000"))
                .verify();
    }

    @Test
    @DisplayName("Debería mapear errores inesperados a CrediYautentiateException")
    void deberiaMaperarErroresInesperados() {
        // Given
        Usuario usuarioARegistrar = crearUsuarioValido();

        when(usuarioRepository.existePorEmail(anyString())).thenReturn(Mono.just(false));
        when(usuarioRepository.guardar(any(Usuario.class))).thenReturn(Mono.error(new RuntimeException("Error de base de datos")));

        // When
        Mono<Usuario> resultado = registrarUsuarioUseCase.registrar(usuarioARegistrar);

        // Then
        StepVerifier.create(resultado)
                .expectErrorMatches(throwable ->
                    throwable instanceof CrediYautentiateException &&
                    throwable.getMessage().contains("Ha ocurrido un error inesperado al registrar el usuario"))
                .verify();
    }

    private Usuario crearUsuarioValido() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setEmail("juan.perez@test.com");
        usuario.setDocumentoIdentidad("12345678");
        usuario.setTelefono("3001234567");
        usuario.setSalarioBase(100000.0);
        usuario.setRolId(1L);
        return usuario;
    }

    private Usuario crearUsuarioConId() {
        Usuario usuario = crearUsuarioValido();
        usuario.setUsuarioId(1L);
        return usuario;
    }
}
