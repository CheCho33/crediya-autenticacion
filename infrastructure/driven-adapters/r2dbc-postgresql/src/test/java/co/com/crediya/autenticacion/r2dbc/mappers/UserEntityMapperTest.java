package co.com.crediya.autenticacion.r2dbc.mappers;

import co.com.crediya.autenticacion.model.usuario.Usuario;
import co.com.crediya.autenticacion.r2dbc.entities.UsuarioEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityMapperTest {

    @Test
    void toDomain_deberiaMapearCorrectamente() {
        UsuarioEntity entity = UsuarioEntity.builder()
                .usuarioId(1L)
                .nombre("Juan")
                .apellido("Pérez")
                .email("juan@correo.com")
                .documentoIdentidad("123456")
                .telefono("5551234")
                .rolId(2L)
                .salarioBase(1000.0)
                .build();

        Usuario usuario = UserEntityMapper.toDomain(entity);
        assertNotNull(usuario);
        assertEquals(1L, usuario.getUsuarioId());
        assertEquals("Juan", usuario.getNombre());
        assertEquals("Pérez", usuario.getApellido());
        assertEquals("juan@correo.com", usuario.getEmail());
        assertEquals("123456", usuario.getDocumentoIdentidad());
        assertEquals("5551234", usuario.getTelefono());
        assertEquals(2L, usuario.getRolId());
        assertEquals(1000.0, usuario.getSalarioBase());
    }

    @Test
    void toDomain_deberiaRetornarNullSiEntityEsNull() {
        assertNull(UserEntityMapper.toDomain(null));
    }

    @Test
    void toEntity_deberiaMapearCorrectamente() {
        Usuario usuario = new Usuario();
        usuario.setUsuarioId(1L);
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setEmail("juan@correo.com");
        usuario.setDocumentoIdentidad("123456");
        usuario.setTelefono("5551234");
        usuario.setRolId(2L);
        usuario.setSalarioBase(1000.0);

        UsuarioEntity entity = UserEntityMapper.toEntity(usuario);
        assertNotNull(entity);
        assertEquals(1L, entity.getUsuarioId());
        assertEquals("Juan", entity.getNombre());
        assertEquals("Pérez", entity.getApellido());
        assertEquals("juan@correo.com", entity.getEmail());
        assertEquals("123456", entity.getDocumentoIdentidad());
        assertEquals("5551234", entity.getTelefono());
        assertEquals(2L, entity.getRolId());
        assertEquals(1000.0, entity.getSalarioBase());
    }

    @Test
    void toEntity_deberiaRetornarNullSiUsuarioEsNull() {
        assertNull(UserEntityMapper.toEntity(null));
    }
}

