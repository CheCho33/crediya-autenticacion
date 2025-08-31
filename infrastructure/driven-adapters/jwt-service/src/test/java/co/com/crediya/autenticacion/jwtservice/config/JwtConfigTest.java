package co.com.crediya.autenticacion.jwtservice.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JwtConfigTest {

    private JwtConfig jwtConfig;

    @BeforeEach
    void setUp() {
        jwtConfig = new JwtConfig();
        jwtConfig.setSecret("CrediYaSecretKey2024ForJWTTokenGenerationAndValidation");
        jwtConfig.setExpiration(86400000L); // 24 hours
    }

    @Test
    void deberiaGenerarTokenValido() {
        // Arrange
        String subject = "test@example.com";

        // Act
        String token = jwtConfig.generateToken(subject);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains("."));
    }

    @Test
    void deberiaValidarTokenCorrecto() {
        // Arrange
        String subject = "test@example.com";
        String token = jwtConfig.generateToken(subject);

        // Act
        boolean isValid = jwtConfig.validateToken(token, subject);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void deberiaExtraerSubjectDelToken() {
        // Arrange
        String subject = "test@example.com";
        String token = jwtConfig.generateToken(subject);

        // Act
        String extractedSubject = jwtConfig.extractSubject(token);

        // Assert
        assertEquals(subject, extractedSubject);
    }
}
