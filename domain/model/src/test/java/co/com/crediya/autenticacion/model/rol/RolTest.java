package co.com.crediya.autenticacion.model.rol;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.com.crediya.autenticacion.model.valueobjects.DescripcionRol;
import co.com.crediya.autenticacion.model.valueobjects.NombreRol;

@DisplayName("Entidad Rol")
class RolTest {
    
    private static final UUID TEST_UUID = UUID.randomUUID();
    private static final RolId TEST_ROL_ID = RolId.of(TEST_UUID);
    private static final NombreRol TEST_NOMBRE = NombreRol.of("ADMIN");
    private static final DescripcionRol TEST_DESCRIPCION = DescripcionRol.of("Rol de administrador del sistema");
    
    @Test
    @DisplayName("Debería crear un rol válido")
    void deberiaCrearRolValido() {
        // When
        Rol rol = Rol.create(TEST_ROL_ID, TEST_NOMBRE, TEST_DESCRIPCION);
        
        // Then
        assertThat(rol.id()).isEqualTo(TEST_ROL_ID);
        assertThat(rol.nombre()).isEqualTo(TEST_NOMBRE);
        assertThat(rol.descripcion()).isEqualTo(TEST_DESCRIPCION);
    }
    
    @Test
    @DisplayName("Debería crear un rol desde datos existentes")
    void deberiaCrearRolDesdeDatosExistentes() {
        // When
        Rol rol = Rol.from(TEST_ROL_ID, TEST_NOMBRE, TEST_DESCRIPCION);
        
        // Then
        assertThat(rol.id()).isEqualTo(TEST_ROL_ID);
        assertThat(rol.nombre()).isEqualTo(TEST_NOMBRE);
        assertThat(rol.descripcion()).isEqualTo(TEST_DESCRIPCION);
    }
    
    @Test
    @DisplayName("Debería actualizar la descripción del rol")
    void deberiaActualizarDescripcion() {
        // Given
        Rol rol = Rol.create(TEST_ROL_ID, TEST_NOMBRE, TEST_DESCRIPCION);
        DescripcionRol nuevaDescripcion = DescripcionRol.of("Nueva descripción del rol");
        
        // When
        Rol rolActualizado = rol.updateDescripcion(nuevaDescripcion);
        
        // Then
        assertThat(rolActualizado.id()).isEqualTo(TEST_ROL_ID);
        assertThat(rolActualizado.nombre()).isEqualTo(TEST_NOMBRE);
        assertThat(rolActualizado.descripcion()).isEqualTo(nuevaDescripcion);
        
        // Verificar que el rol original no cambió (inmutabilidad)
        assertThat(rol.descripcion()).isEqualTo(TEST_DESCRIPCION);
    }
    
    @Test
    @DisplayName("Debería actualizar el nombre del rol")
    void deberiaActualizarNombre() {
        // Given
        Rol rol = Rol.create(TEST_ROL_ID, TEST_NOMBRE, TEST_DESCRIPCION);
        NombreRol nuevoNombre = NombreRol.of("ASESOR");
        
        // When
        Rol rolActualizado = rol.updateNombre(nuevoNombre);
        
        // Then
        assertThat(rolActualizado.id()).isEqualTo(TEST_ROL_ID);
        assertThat(rolActualizado.nombre()).isEqualTo(nuevoNombre);
        assertThat(rolActualizado.descripcion()).isEqualTo(TEST_DESCRIPCION);
        
        // Verificar que el rol original no cambió (inmutabilidad)
        assertThat(rol.nombre()).isEqualTo(TEST_NOMBRE);
    }
    
    @Test
    @DisplayName("Debería verificar si el rol tiene un nombre específico")
    void deberiaVerificarNombre() {
        // Given
        Rol rol = Rol.create(TEST_ROL_ID, TEST_NOMBRE, TEST_DESCRIPCION);
        NombreRol nombreAdmin = NombreRol.of("ADMIN");
        NombreRol nombreAsesor = NombreRol.of("ASESOR");
        
        // When & Then
        assertThat(rol.hasNombre(nombreAdmin)).isTrue();
        assertThat(rol.hasNombre(nombreAsesor)).isFalse();
    }
    
    @Test
    @DisplayName("Debería identificar roles específicos correctamente")
    void deberiaIdentificarRolesEspecificos() {
        // Given
        Rol rolAdmin = Rol.create(TEST_ROL_ID, NombreRol.of("ADMIN"), TEST_DESCRIPCION);
        Rol rolAsesor = Rol.create(RolId.of(UUID.randomUUID()), NombreRol.of("ASESOR"), TEST_DESCRIPCION);
        Rol rolCliente = Rol.create(RolId.of(UUID.randomUUID()), NombreRol.of("CLIENTE"), TEST_DESCRIPCION);
        
        // When & Then
        assertThat(rolAdmin.isAdmin()).isTrue();
        assertThat(rolAdmin.isAsesor()).isFalse();
        assertThat(rolAdmin.isCliente()).isFalse();
        
        assertThat(rolAsesor.isAdmin()).isFalse();
        assertThat(rolAsesor.isAsesor()).isTrue();
        assertThat(rolAsesor.isCliente()).isFalse();
        
        assertThat(rolCliente.isAdmin()).isFalse();
        assertThat(rolCliente.isAsesor()).isFalse();
        assertThat(rolCliente.isCliente()).isTrue();
    }
    
    @Test
    @DisplayName("Debería identificar roles ignorando mayúsculas/minúsculas")
    void deberiaIdentificarRolesIgnorandoCase() {
        // Given
        Rol rolAdmin = Rol.create(TEST_ROL_ID, NombreRol.of("admin"), TEST_DESCRIPCION);
        Rol rolAsesor = Rol.create(RolId.of(UUID.randomUUID()), NombreRol.of("Asesor"), TEST_DESCRIPCION);
        
        // When & Then
        assertThat(rolAdmin.isAdmin()).isTrue();
        assertThat(rolAsesor.isAsesor()).isTrue();
    }
    
    @Test
    @DisplayName("Debería verificar permisos administrativos correctamente")
    void deberiaVerificarPermisosAdministrativos() {
        // Given
        Rol rolAdmin = Rol.create(TEST_ROL_ID, NombreRol.of("ADMIN"), TEST_DESCRIPCION);
        Rol rolAsesor = Rol.create(RolId.of(UUID.randomUUID()), NombreRol.of("ASESOR"), TEST_DESCRIPCION);
        Rol rolCliente = Rol.create(RolId.of(UUID.randomUUID()), NombreRol.of("CLIENTE"), TEST_DESCRIPCION);
        
        // When & Then
        assertThat(rolAdmin.tienePermisosAdministrativos()).isTrue();
        assertThat(rolAsesor.tienePermisosAdministrativos()).isTrue();
        assertThat(rolCliente.tienePermisosAdministrativos()).isFalse();
    }
    
    @Test
    @DisplayName("Debería verificar permisos para registrar usuarios correctamente")
    void deberiaVerificarPermisosParaRegistrarUsuarios() {
        // Given
        Rol rolAdmin = Rol.create(TEST_ROL_ID, NombreRol.of("ADMIN"), TEST_DESCRIPCION);
        Rol rolAsesor = Rol.create(RolId.of(UUID.randomUUID()), NombreRol.of("ASESOR"), TEST_DESCRIPCION);
        Rol rolCliente = Rol.create(RolId.of(UUID.randomUUID()), NombreRol.of("CLIENTE"), TEST_DESCRIPCION);
        
        // When & Then
        assertThat(rolAdmin.puedeRegistrarUsuarios()).isTrue();
        assertThat(rolAsesor.puedeRegistrarUsuarios()).isTrue();
        assertThat(rolCliente.puedeRegistrarUsuarios()).isFalse();
    }
    
    @Test
    @DisplayName("Debería lanzar excepción al crear rol con ID nulo")
    void deberiaLanzarExcepcionConIdNulo() {
        // When & Then
        assertThatThrownBy(() -> Rol.create(null, TEST_NOMBRE, TEST_DESCRIPCION))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("El identificador del rol no puede ser nulo");
    }
    
    @Test
    @DisplayName("Debería lanzar excepción al crear rol con nombre nulo")
    void deberiaLanzarExcepcionConNombreNulo() {
        // When & Then
        assertThatThrownBy(() -> Rol.create(TEST_ROL_ID, null, TEST_DESCRIPCION))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("El nombre del rol no puede ser nulo");
    }
    
    @Test
    @DisplayName("Debería lanzar excepción al crear rol con descripción nula")
    void deberiaLanzarExcepcionConDescripcionNula() {
        // When & Then
        assertThatThrownBy(() -> Rol.create(TEST_ROL_ID, TEST_NOMBRE, null))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("La descripción del rol no puede ser nula");
    }
    
    @Test
    @DisplayName("Debería ser igual a otro rol con el mismo ID")
    void deberiaSerIgualConMismoId() {
        // Given
        Rol rol1 = Rol.create(TEST_ROL_ID, TEST_NOMBRE, TEST_DESCRIPCION);
        Rol rol2 = Rol.create(TEST_ROL_ID, NombreRol.of("OTRO"), DescripcionRol.of("Otra descripción"));
        
        // When & Then
        assertThat(rol1).isEqualTo(rol2);
        assertThat(rol1.hashCode()).isEqualTo(rol2.hashCode());
    }
    
    @Test
    @DisplayName("Debería tener representación de string válida")
    void deberiaTenerToStringValido() {
        // Given
        Rol rol = Rol.create(TEST_ROL_ID, TEST_NOMBRE, TEST_DESCRIPCION);
        
        // When
        String toString = rol.toString();
        
        // Then
        assertThat(toString)
            .contains("Rol{")
            .contains("id=" + TEST_UUID)
            .contains("nombre=ADMIN")
            .contains("descripcion=Rol de administrador del sistema");
    }
}
