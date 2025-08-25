package co.com.crediya.autenticacion.model.usuario;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.com.crediya.autenticacion.model.rol.RolId;
import co.com.crediya.autenticacion.model.valueobjects.ApellidoUsuario;
import co.com.crediya.autenticacion.model.valueobjects.DocumentoIdentidad;
import co.com.crediya.autenticacion.model.valueobjects.Email;
import co.com.crediya.autenticacion.model.valueobjects.NombreUsuario;
import co.com.crediya.autenticacion.model.valueobjects.SalarioBase;
import co.com.crediya.autenticacion.model.valueobjects.Telefono;
import co.com.crediya.autenticacion.model.valueobjects.UsuarioId;

@DisplayName("Entidad Usuario")
class UsuarioTest {
    
    private static final UUID TEST_UUID = UUID.randomUUID();
    private static final UsuarioId TEST_USUARIO_ID = UsuarioId.of(TEST_UUID);
    private static final NombreUsuario TEST_NOMBRE = NombreUsuario.of("Juan");
    private static final ApellidoUsuario TEST_APELLIDO = ApellidoUsuario.of("Pérez");
    private static final Email TEST_EMAIL = Email.of("juan.perez@ejemplo.com");
    private static final DocumentoIdentidad TEST_DOCUMENTO = DocumentoIdentidad.of("12345678");
    private static final Telefono TEST_TELEFONO = Telefono.of("3001234567");
    private static final RolId TEST_ROL_ID = RolId.of(UUID.randomUUID());
    private static final SalarioBase TEST_SALARIO = SalarioBase.of(3000000.00);
    
    @Test
    @DisplayName("Debería crear un usuario válido")
    void deberiaCrearUsuarioValido() {
        // When
        Usuario usuario = Usuario.create(TEST_USUARIO_ID, TEST_NOMBRE, TEST_APELLIDO,
                                        TEST_EMAIL, TEST_DOCUMENTO, TEST_TELEFONO,
                                        TEST_ROL_ID, TEST_SALARIO);
        
        // Then
        assertThat(usuario.id()).isEqualTo(TEST_USUARIO_ID);
        assertThat(usuario.nombre()).isEqualTo(TEST_NOMBRE);
        assertThat(usuario.apellido()).isEqualTo(TEST_APELLIDO);
        assertThat(usuario.email()).isEqualTo(TEST_EMAIL);
        assertThat(usuario.documentoIdentidad()).isEqualTo(TEST_DOCUMENTO);
        assertThat(usuario.telefono()).isEqualTo(TEST_TELEFONO);
        assertThat(usuario.rolId()).isEqualTo(TEST_ROL_ID);
        assertThat(usuario.salarioBase()).isEqualTo(TEST_SALARIO);
        assertThat(usuario.estado()).isEqualTo(EstadoUsuario.ACTIVO);
        assertThat(usuario.fechaCreacion()).isNotNull();
        assertThat(usuario.version()).isEqualTo(0L);
    }
    
    @Test
    @DisplayName("Debería crear un usuario desde datos existentes")
    void deberiaCrearUsuarioDesdeDatosExistentes() {
        // Given
        LocalDateTime fechaCreacion = LocalDateTime.now();
        long version = 5L;
        
        // When
        Usuario usuario = Usuario.from(TEST_USUARIO_ID, TEST_NOMBRE, TEST_APELLIDO,
                                      TEST_EMAIL, TEST_DOCUMENTO, TEST_TELEFONO,
                                      TEST_ROL_ID, TEST_SALARIO, EstadoUsuario.ACTIVO,
                                      fechaCreacion, version);
        
        // Then
        assertThat(usuario.id()).isEqualTo(TEST_USUARIO_ID);
        assertThat(usuario.nombre()).isEqualTo(TEST_NOMBRE);
        assertThat(usuario.apellido()).isEqualTo(TEST_APELLIDO);
        assertThat(usuario.email()).isEqualTo(TEST_EMAIL);
        assertThat(usuario.documentoIdentidad()).isEqualTo(TEST_DOCUMENTO);
        assertThat(usuario.telefono()).isEqualTo(TEST_TELEFONO);
        assertThat(usuario.rolId()).isEqualTo(TEST_ROL_ID);
        assertThat(usuario.salarioBase()).isEqualTo(TEST_SALARIO);
        assertThat(usuario.estado()).isEqualTo(EstadoUsuario.ACTIVO);
        assertThat(usuario.fechaCreacion()).isEqualTo(fechaCreacion);
        assertThat(usuario.version()).isEqualTo(version);
    }
    
    @Test
    @DisplayName("Debería actualizar el teléfono del usuario")
    void deberiaActualizarTelefono() {
        // Given
        Usuario usuario = Usuario.create(TEST_USUARIO_ID, TEST_NOMBRE, TEST_APELLIDO,
                                        TEST_EMAIL, TEST_DOCUMENTO, TEST_TELEFONO,
                                        TEST_ROL_ID, TEST_SALARIO);
        Telefono nuevoTelefono = Telefono.of("3109876543");
        
        // When
        Usuario usuarioActualizado = usuario.updateTelefono(nuevoTelefono);
        
        // Then
        assertThat(usuarioActualizado.id()).isEqualTo(TEST_USUARIO_ID);
        assertThat(usuarioActualizado.telefono()).isEqualTo(nuevoTelefono);
        assertThat(usuarioActualizado.nombre()).isEqualTo(TEST_NOMBRE);
        assertThat(usuarioActualizado.apellido()).isEqualTo(TEST_APELLIDO);
        assertThat(usuarioActualizado.email()).isEqualTo(TEST_EMAIL);
        assertThat(usuarioActualizado.documentoIdentidad()).isEqualTo(TEST_DOCUMENTO);
        assertThat(usuarioActualizado.rolId()).isEqualTo(TEST_ROL_ID);
        assertThat(usuarioActualizado.salarioBase()).isEqualTo(TEST_SALARIO);
        assertThat(usuarioActualizado.estado()).isEqualTo(EstadoUsuario.ACTIVO);
        
        // Verificar que el usuario original no cambió (inmutabilidad)
        assertThat(usuario.telefono()).isEqualTo(TEST_TELEFONO);
    }
    
    @Test
    @DisplayName("Debería actualizar el salario base del usuario")
    void deberiaActualizarSalarioBase() {
        // Given
        Usuario usuario = Usuario.create(TEST_USUARIO_ID, TEST_NOMBRE, TEST_APELLIDO,
                                        TEST_EMAIL, TEST_DOCUMENTO, TEST_TELEFONO,
                                        TEST_ROL_ID, TEST_SALARIO);
        SalarioBase nuevoSalario = SalarioBase.of(4000000.00);
        
        // When
        Usuario usuarioActualizado = usuario.updateSalarioBase(nuevoSalario);
        
        // Then
        assertThat(usuarioActualizado.id()).isEqualTo(TEST_USUARIO_ID);
        assertThat(usuarioActualizado.salarioBase()).isEqualTo(nuevoSalario);
        assertThat(usuarioActualizado.nombre()).isEqualTo(TEST_NOMBRE);
        assertThat(usuarioActualizado.apellido()).isEqualTo(TEST_APELLIDO);
        assertThat(usuarioActualizado.email()).isEqualTo(TEST_EMAIL);
        assertThat(usuarioActualizado.documentoIdentidad()).isEqualTo(TEST_DOCUMENTO);
        assertThat(usuarioActualizado.telefono()).isEqualTo(TEST_TELEFONO);
        assertThat(usuarioActualizado.rolId()).isEqualTo(TEST_ROL_ID);
        assertThat(usuarioActualizado.estado()).isEqualTo(EstadoUsuario.ACTIVO);
        
        // Verificar que el usuario original no cambió (inmutabilidad)
        assertThat(usuario.salarioBase()).isEqualTo(TEST_SALARIO);
    }
    
    @Test
    @DisplayName("Debería cambiar el estado del usuario")
    void deberiaCambiarEstado() {
        // Given
        Usuario usuario = Usuario.create(TEST_USUARIO_ID, TEST_NOMBRE, TEST_APELLIDO,
                                        TEST_EMAIL, TEST_DOCUMENTO, TEST_TELEFONO,
                                        TEST_ROL_ID, TEST_SALARIO);
        
        // When
        Usuario usuarioInactivo = usuario.cambiarEstado(EstadoUsuario.INACTIVO);
        
        // Then
        assertThat(usuarioInactivo.id()).isEqualTo(TEST_USUARIO_ID);
        assertThat(usuarioInactivo.estado()).isEqualTo(EstadoUsuario.INACTIVO);
        assertThat(usuarioInactivo.nombre()).isEqualTo(TEST_NOMBRE);
        assertThat(usuarioInactivo.apellido()).isEqualTo(TEST_APELLIDO);
        assertThat(usuarioInactivo.email()).isEqualTo(TEST_EMAIL);
        assertThat(usuarioInactivo.documentoIdentidad()).isEqualTo(TEST_DOCUMENTO);
        assertThat(usuarioInactivo.telefono()).isEqualTo(TEST_TELEFONO);
        assertThat(usuarioInactivo.rolId()).isEqualTo(TEST_ROL_ID);
        assertThat(usuarioInactivo.salarioBase()).isEqualTo(TEST_SALARIO);
        
        // Verificar que el usuario original no cambió (inmutabilidad)
        assertThat(usuario.estado()).isEqualTo(EstadoUsuario.ACTIVO);
    }
    
    @Test
    @DisplayName("Debería marcar el usuario como persistido con nueva versión")
    void deberiaMarcarComoPersistido() {
        // Given
        Usuario usuario = Usuario.create(TEST_USUARIO_ID, TEST_NOMBRE, TEST_APELLIDO,
                                        TEST_EMAIL, TEST_DOCUMENTO, TEST_TELEFONO,
                                        TEST_ROL_ID, TEST_SALARIO);
        long nuevaVersion = 1L;
        
        // When
        Usuario usuarioPersistido = usuario.markPersisted(nuevaVersion);
        
        // Then
        assertThat(usuarioPersistido.id()).isEqualTo(TEST_USUARIO_ID);
        assertThat(usuarioPersistido.version()).isEqualTo(nuevaVersion);
        assertThat(usuarioPersistido.nombre()).isEqualTo(TEST_NOMBRE);
        assertThat(usuarioPersistido.apellido()).isEqualTo(TEST_APELLIDO);
        assertThat(usuarioPersistido.email()).isEqualTo(TEST_EMAIL);
        assertThat(usuarioPersistido.documentoIdentidad()).isEqualTo(TEST_DOCUMENTO);
        assertThat(usuarioPersistido.telefono()).isEqualTo(TEST_TELEFONO);
        assertThat(usuarioPersistido.rolId()).isEqualTo(TEST_ROL_ID);
        assertThat(usuarioPersistido.salarioBase()).isEqualTo(TEST_SALARIO);
        assertThat(usuarioPersistido.estado()).isEqualTo(EstadoUsuario.ACTIVO);
    }
    
    @Test
    @DisplayName("Debería lanzar excepción al marcar como persistido con versión inválida")
    void deberiaLanzarExcepcionConVersionInvalida() {
        // Given
        Usuario usuario = Usuario.create(TEST_USUARIO_ID, TEST_NOMBRE, TEST_APELLIDO,
                                        TEST_EMAIL, TEST_DOCUMENTO, TEST_TELEFONO,
                                        TEST_ROL_ID, TEST_SALARIO);
        
        // When & Then
        assertThatThrownBy(() -> usuario.markPersisted(0L))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("La nueva versión debe ser mayor que la actual");
    }
    
    @Test
    @DisplayName("Debería verificar si el usuario está activo")
    void deberiaVerificarSiEstaActivo() {
        // Given
        Usuario usuarioActivo = Usuario.create(TEST_USUARIO_ID, TEST_NOMBRE, TEST_APELLIDO,
                                              TEST_EMAIL, TEST_DOCUMENTO, TEST_TELEFONO,
                                              TEST_ROL_ID, TEST_SALARIO);
        Usuario usuarioInactivo = usuarioActivo.cambiarEstado(EstadoUsuario.INACTIVO);
        
        // When & Then
        assertThat(usuarioActivo.isActivo()).isTrue();
        assertThat(usuarioInactivo.isActivo()).isFalse();
    }
    
    @Test
    @DisplayName("Debería verificar si el usuario puede autenticarse")
    void deberiaVerificarSiPuedeAutenticarse() {
        // Given
        Usuario usuarioActivo = Usuario.create(TEST_USUARIO_ID, TEST_NOMBRE, TEST_APELLIDO,
                                              TEST_EMAIL, TEST_DOCUMENTO, TEST_TELEFONO,
                                              TEST_ROL_ID, TEST_SALARIO);
        Usuario usuarioInactivo = usuarioActivo.cambiarEstado(EstadoUsuario.INACTIVO);
        Usuario usuarioSuspendido = usuarioActivo.cambiarEstado(EstadoUsuario.SUSPENDIDO);
        
        // When & Then
        assertThat(usuarioActivo.puedeAutenticarse()).isTrue();
        assertThat(usuarioInactivo.puedeAutenticarse()).isFalse();
        assertThat(usuarioSuspendido.puedeAutenticarse()).isFalse();
    }
    
    @Test
    @DisplayName("Debería obtener el nombre completo del usuario")
    void deberiaObtenerNombreCompleto() {
        // Given
        Usuario usuario = Usuario.create(TEST_USUARIO_ID, TEST_NOMBRE, TEST_APELLIDO,
                                        TEST_EMAIL, TEST_DOCUMENTO, TEST_TELEFONO,
                                        TEST_ROL_ID, TEST_SALARIO);
        
        // When
        String nombreCompleto = usuario.nombreCompleto();
        
        // Then
        assertThat(nombreCompleto).isEqualTo("Juan Pérez");
    }
    
    @Test
    @DisplayName("Debería verificar si el usuario cumple con el salario mínimo")
    void deberiaVerificarSiCumpleSalarioMinimo() {
        // Given
        Usuario usuario = Usuario.create(TEST_USUARIO_ID, TEST_NOMBRE, TEST_APELLIDO,
                                        TEST_EMAIL, TEST_DOCUMENTO, TEST_TELEFONO,
                                        TEST_ROL_ID, TEST_SALARIO);
        SalarioBase salarioMinimo = SalarioBase.of(1000000.00);
        SalarioBase salarioMinimoAlto = SalarioBase.of(5000000.00);
        
        // When & Then
        assertThat(usuario.cumpleSalarioMinimo(salarioMinimo)).isTrue();
        assertThat(usuario.cumpleSalarioMinimo(salarioMinimoAlto)).isFalse();
    }
    
    @Test
    @DisplayName("Debería lanzar excepción al crear usuario con ID nulo")
    void deberiaLanzarExcepcionConIdNulo() {
        // When & Then
        assertThatThrownBy(() -> Usuario.create(null, TEST_NOMBRE, TEST_APELLIDO,
                                               TEST_EMAIL, TEST_DOCUMENTO, TEST_TELEFONO,
                                               TEST_ROL_ID, TEST_SALARIO))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("El identificador del usuario no puede ser nulo");
    }
    
    @Test
    @DisplayName("Debería lanzar excepción al crear usuario con nombre nulo")
    void deberiaLanzarExcepcionConNombreNulo() {
        // When & Then
        assertThatThrownBy(() -> Usuario.create(TEST_USUARIO_ID, null, TEST_APELLIDO,
                                               TEST_EMAIL, TEST_DOCUMENTO, TEST_TELEFONO,
                                               TEST_ROL_ID, TEST_SALARIO))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("El nombre del usuario no puede ser nulo");
    }
    
    @Test
    @DisplayName("Debería lanzar excepción al crear usuario con email nulo")
    void deberiaLanzarExcepcionConEmailNulo() {
        // When & Then
        assertThatThrownBy(() -> Usuario.create(TEST_USUARIO_ID, TEST_NOMBRE, TEST_APELLIDO,
                                               null, TEST_DOCUMENTO, TEST_TELEFONO,
                                               TEST_ROL_ID, TEST_SALARIO))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("El email del usuario no puede ser nulo");
    }
    
    @Test
    @DisplayName("Debería lanzar excepción al crear usuario con rol nulo")
    void deberiaLanzarExcepcionConRolNulo() {
        // When & Then
        assertThatThrownBy(() -> Usuario.create(TEST_USUARIO_ID, TEST_NOMBRE, TEST_APELLIDO,
                                               TEST_EMAIL, TEST_DOCUMENTO, TEST_TELEFONO,
                                               null, TEST_SALARIO))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("El rol del usuario no puede ser nulo");
    }
    
    @Test
    @DisplayName("Debería ser igual a otro usuario con el mismo ID")
    void deberiaSerIgualConMismoId() {
        // Given
        Usuario usuario1 = Usuario.create(TEST_USUARIO_ID, TEST_NOMBRE, TEST_APELLIDO,
                                         TEST_EMAIL, TEST_DOCUMENTO, TEST_TELEFONO,
                                         TEST_ROL_ID, TEST_SALARIO);
        Usuario usuario2 = Usuario.create(TEST_USUARIO_ID, NombreUsuario.of("María"), 
                                         ApellidoUsuario.of("García"), TEST_EMAIL, 
                                         TEST_DOCUMENTO, TEST_TELEFONO, TEST_ROL_ID, TEST_SALARIO);
        
        // When & Then
        assertThat(usuario1).isEqualTo(usuario2);
        assertThat(usuario1.hashCode()).isEqualTo(usuario2.hashCode());
    }
    
    @Test
    @DisplayName("Debería tener representación de string válida")
    void deberiaTenerToStringValido() {
        // Given
        Usuario usuario = Usuario.create(TEST_USUARIO_ID, TEST_NOMBRE, TEST_APELLIDO,
                                        TEST_EMAIL, TEST_DOCUMENTO, TEST_TELEFONO,
                                        TEST_ROL_ID, TEST_SALARIO);
        
        // When
        String toString = usuario.toString();
        
        // Then
        assertThat(toString)
            .contains("Usuario{")
            .contains("id=" + TEST_UUID)
            .contains("nombre=Juan")
            .contains("apellido=Pérez")
            .contains("email=juan.perez@ejemplo.com")
            .contains("documentoIdentidad=12345678")
            .contains("telefono=3001234567")
            .contains("estado=ACTIVO")
            .contains("version=0");
    }
}
