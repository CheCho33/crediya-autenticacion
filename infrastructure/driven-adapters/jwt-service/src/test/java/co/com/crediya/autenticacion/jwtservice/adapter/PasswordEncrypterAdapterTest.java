package co.com.crediya.autenticacion.jwtservice.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.crediya.autenticacion.jwtservice.config.JwtConfig;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class PasswordEncrypterAdapterTest {

    @Mock
    private JwtConfig jwtConfig;

    private PasswordEncrypterAdapter passwordEncrypterAdapter;

    @BeforeEach
    void setUp() {
        passwordEncrypterAdapter = new PasswordEncrypterAdapter(jwtConfig);
    }

    @Test
    void deberiaGenerarTokenJWT() {
        // Arrange
        String email = "test@example.com";
        String expectedToken = "eyJhbGciOiJIUzI1NiJ9.test.token";
        when(jwtConfig.generateToken(anyString())).thenReturn(expectedToken);

        // Act & Assert
        StepVerifier.create(passwordEncrypterAdapter.generarToken(email))
                .expectNext(expectedToken)
                .verifyComplete();
    }
}
