package co.com.crediya.autenticacion.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.web.bind.annotation.RequestMethod;

import co.com.crediya.autenticacion.api.dto.CrearUsuarioDto;
import co.com.crediya.autenticacion.api.dto.RespuestaGenericaDto;

@Configuration
public class RouterRest {
    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/api/v1/usuarios",
                    method = RequestMethod.POST,
                    beanClass = Handler.class,
                    beanMethod = "guardarUsuario",
                    operation = @Operation(
                            operationId = "guardarUsuario",
                            summary = "Crea un nuevo usuario",
                            description = "Registra un usuario y retorna una respuesta genérica con el resultado",
                            tags = {"Usuarios"},
                            requestBody = @RequestBody(
                                    required = true,
                                    description = "Payload para crear usuario",
                                    content = @Content(
                                            schema = @Schema(implementation = CrearUsuarioDto.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Usuario creado",
                                            content = @Content(
                                                    schema = @Schema(implementation = RespuestaGenericaDto.class)
                                            )
                                    ),
                                    @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
                                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET("/api/usecase/path"), handler::listenGETUseCase)
                .andRoute(POST("/api/api/v1/usuarios"), handler::guardarUsuario);
    }
}
