package co.com.crediya.autenticacion.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import co.com.crediya.autenticacion.api.dto.CrearUsuarioDto;
import co.com.crediya.autenticacion.api.dto.RespuestaGenericaDto;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {RouterRest.class, Handler.class})
@WebFluxTest
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @Mock
    private Handler handler;

    private CrearUsuarioDto crearUsuarioDto;
    private RespuestaGenericaDto respuestaGenericaDto;

    @BeforeEach
    void setUp() {
        crearUsuarioDto = new CrearUsuarioDto("sergio", "tabares", "test@test34.com", "123", "123", 1L, 300.3 );

        respuestaGenericaDto = new RespuestaGenericaDto("Usuario Creado", null);
    }




}
