package co.com.crediya.autenticacion.r2dbc.adapters;

import co.com.crediya.autenticacion.model.usuario.Usuario;
import co.com.crediya.autenticacion.r2dbc.entities.UsuarioEntity;
import co.com.crediya.autenticacion.r2dbc.repositories.UsuarioEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Test unitarios para UsuarioAdapters.
 * Verifica el comportamiento del adaptador mockeando las interacciones con la base de datos.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UsuarioAdapters Tests")
class UsuarioAdaptersTest {

    @Mock
    private UsuarioEntityRepository usuarioEntityRepository;

    @InjectMocks
    private UsuarioAdapters usuarioAdapters;

    private Usuario usuarioMock;
    private UsuarioEntity usuarioEntityMock;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba
        usuarioMock = new Usuario();
        usuarioMock.setUsuarioId(1L);
        usuarioMock.setNombre("Juan");
        usuarioMock.setApellido("Pérez");
        usuarioMock.setEmail("juan.perez@example.com");
        usuarioMock.setDocumentoIdentidad("12345678");
        usuarioMock.setTelefono("3001234567");
        usuarioMock.setRolId(2L);
        usuarioMock.setSalarioBase(2500000.0);

        usuarioEntityMock = UsuarioEntity.builder()
                .usuarioId(1L)
                .nombre("Juan")
                .apellido("Pérez")
                .email("juan.perez@example.com")
                .documentoIdentidad("12345678")
                .telefono("3001234567")
                .rolId(2L)
                .salarioBase(2500000.0)
                .build();
    }

    @Test
    @DisplayName("Debe guardar un usuario exitosamente")
    void debeGuardarUsuarioExitosamente() {
        // Given
        when(usuarioEntityRepository.save(any(UsuarioEntity.class)))
                .thenReturn(Mono.just(usuarioEntityMock));

        // When
        Mono<Usuario> resultado = usuarioAdapters.guardar(usuarioMock);

        // Then
        StepVerifier.create(resultado)
                .expectNextMatches(usuario ->
                    usuario.getUsuarioId().equals(1L) &&
                    usuario.getNombre().equals("Juan") &&
                    usuario.getApellido().equals("Pérez") &&
                    usuario.getEmail().equals("juan.perez@example.com") &&
                    usuario.getDocumentoIdentidad().equals("12345678") &&
                    usuario.getTelefono().equals("3001234567") &&
                    usuario.getRolId().equals(2L) &&
                    usuario.getSalarioBase().equals(2500000.0)
                )
                .verifyComplete();
    }

    @Test
    @DisplayName("Debe manejar error al guardar usuario")
    void debeManejarErrorAlGuardarUsuario() {
        // Given
        when(usuarioEntityRepository.save(any(UsuarioEntity.class)))
                .thenReturn(Mono.error(new RuntimeException("Error de base de datos")));

        // When
        Mono<Usuario> resultado = usuarioAdapters.guardar(usuarioMock);

        // Then
        StepVerifier.create(resultado)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    @DisplayName("Debe buscar usuario por email exitosamente")
    void debeBuscarUsuarioPorEmailExitosamente() {
        // Given
        String email = "juan.perez@example.com";
        when(usuarioEntityRepository.findByEmail(email))
                .thenReturn(Mono.just(usuarioEntityMock));

        // When
        Mono<Usuario> resultado = usuarioAdapters.buscarPorEmail(email);

        // Then
        StepVerifier.create(resultado)
                .expectNextMatches(usuario ->
                    usuario.getEmail().equals(email) &&
                    usuario.getNombre().equals("Juan")
                )
                .verifyComplete();
    }


    @Test
    @DisplayName("Debe verificar existencia de usuario por email - existe")
    void debeVerificarExistenciaUsuarioPorEmail_Existe() {
        // Given
        String email = "juan.perez@example.com";
        when(usuarioEntityRepository.existsByEmail(email))
                .thenReturn(Mono.just(true));

        // When
        Mono<Boolean> resultado = usuarioAdapters.existePorEmail(email);

        // Then
        StepVerifier.create(resultado)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    @DisplayName("Debe verificar existencia de usuario por email - no existe")
    void debeVerificarExistenciaUsuarioPorEmail_NoExiste() {
        // Given
        String email = "noexiste@example.com";
        when(usuarioEntityRepository.existsByEmail(email))
                .thenReturn(Mono.just(false));

        // When
        Mono<Boolean> resultado = usuarioAdapters.existePorEmail(email);

        // Then
        StepVerifier.create(resultado)
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    @DisplayName("Debe buscar usuario por ID exitosamente")
    void debeBuscarUsuarioPorIdExitosamente() {
        // Given
        Long idUsuario = 1L;
        when(usuarioEntityRepository.findById(idUsuario))
                .thenReturn(Mono.just(usuarioEntityMock));

        // When
        Mono<Usuario> resultado = usuarioAdapters.buscarPorId(idUsuario);

        // Then
        StepVerifier.create(resultado)
                .expectNextMatches(usuario ->
                    usuario.getUsuarioId().equals(idUsuario) &&
                    usuario.getNombre().equals("Juan")
                )
                .verifyComplete();
    }

    @Test
    @DisplayName("Debe retornar empty cuando no encuentra usuario por ID")
    void debeRetornarEmptyNoCuandoEncuentraUsuarioPorId() {
        // Given
        Long idUsuario = 999L;
        when(usuarioEntityRepository.findById(idUsuario))
                .thenReturn(Mono.empty());

        // When
        Mono<Usuario> resultado = usuarioAdapters.buscarPorId(idUsuario);

        // Then
        StepVerifier.create(resultado)
                .verifyComplete();
    }

    @Test
    @DisplayName("Debe manejar error al buscar usuario por email")
    void debeManejarErrorAlBuscarUsuarioPorEmail() {
        // Given
        String email = "juan.perez@example.com";
        when(usuarioEntityRepository.findByEmail(email))
                .thenReturn(Mono.error(new RuntimeException("Error de conexión")));

        // When
        Mono<Usuario> resultado = usuarioAdapters.buscarPorEmail(email);

        // Then
        StepVerifier.create(resultado)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    @DisplayName("Debe manejar error al verificar existencia por email")
    void debeManejarErrorAlVerificarExistenciaPorEmail() {
        // Given
        String email = "juan.perez@example.com";
        when(usuarioEntityRepository.existsByEmail(email))
                .thenReturn(Mono.error(new RuntimeException("Error de base de datos")));

        // When
        Mono<Boolean> resultado = usuarioAdapters.existePorEmail(email);

        // Then
        StepVerifier.create(resultado)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    @DisplayName("Debe manejar error al buscar usuario por ID")
    void debeManejarErrorAlBuscarUsuarioPorId() {
        // Given
        Long idUsuario = 1L;
        when(usuarioEntityRepository.findById(idUsuario))
                .thenReturn(Mono.error(new RuntimeException("Error de base de datos")));

        // When
        Mono<Usuario> resultado = usuarioAdapters.buscarPorId(idUsuario);

        // Then
        StepVerifier.create(resultado)
                .expectError(RuntimeException.class)
                .verify();
    }
}
