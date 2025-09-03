package co.com.crediya.autenticacion.api;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import org.springframework.web.reactive.function.server.RouterFunction;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.crediya.autenticacion.api.dto.CrearUsuarioDto;
import co.com.crediya.autenticacion.api.dto.ErrorResponseDto;
import co.com.crediya.autenticacion.api.dto.RespuestaGenericaDto;
import co.com.crediya.autenticacion.model.sesion.Sesion;
import co.com.crediya.autenticacion.model.usuario.dto.UsuarioLoginDto;
import co.com.crediya.autenticacion.model.usuario.dto.UsuarioResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

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
                            description = "Registra un usuario en el sistema y retorna mensaje de éxito con los datos almacenados. " +
                                    "El usuario debe proporcionar nombre, apellido, email, documento de identidad, teléfono, rol y salario base.",
                            tags = {"Usuarios"},
                            requestBody = @RequestBody(
                                    required = true, 
                                    description = "Datos del usuario a registrar",
                                    content = @Content(schema = @Schema(implementation = CrearUsuarioDto.class))
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200", 
                                            description = "Usuario creado exitosamente",
                                            content = @Content(schema = @Schema(implementation = RespuestaGenericaDto.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400", 
                                            description = "Solicitud inválida - Datos faltantes o incorrectos",
                                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "409", 
                                            description = "Conflicto - El email ya está registrado en el sistema",
                                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "500", 
                                            description = "Error interno del servidor",
                                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                                    ),
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/usuarios",
                    method = RequestMethod.GET,
                    beanClass = Handler.class,
                    beanMethod = "getUsuario",
                    operation = @Operation(
                            operationId = "getUsuario",
                            summary = "Obtiene datos del usuario autenticado",
                            description = "Retorna los datos del usuario correspondiente a la sesión activa. " +
                                    "Requiere autenticación mediante token en el header 'x-Token'.",
                            tags = {"Usuarios"},
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200", 
                                            description = "Datos del usuario obtenidos exitosamente",
                                            content = @Content(schema = @Schema(implementation = UsuarioResponseDto.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "401", 
                                            description = "No autorizado - Token inválido o expirado",
                                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404", 
                                            description = "Usuario no encontrado",
                                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "500", 
                                            description = "Error interno del servidor",
                                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                                    ),
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/login",
                    method = RequestMethod.POST,
                    beanClass = Handler.class,
                    beanMethod = "loginUsuario",
                    operation = @Operation(
                            operationId = "loginUsuario",
                            summary = "Autentica un usuario y genera sesión",
                            description = "Valida las credenciales del usuario (email y contraseña) y genera una sesión activa " +
                                    "con un token JWT válido por 24 horas. Si el usuario ya tiene una sesión activa, se reutiliza.",
                            tags = {"Autenticación"},
                            requestBody = @RequestBody(
                                    required = true, 
                                    description = "Credenciales de autenticación",
                                    content = @Content(schema = @Schema(implementation = UsuarioLoginDto.class))
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200", 
                                            description = "Autenticación exitosa",
                                            content = @Content(schema = @Schema(implementation = Sesion.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400", 
                                            description = "Solicitud inválida - Credenciales faltantes",
                                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "401", 
                                            description = "Credenciales inválidas - Usuario o contraseña incorrectos",
                                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "500", 
                                            description = "Error interno del servidor",
                                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                                    ),
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET("/api/usecase/path"), handler::listenGETUseCase)
                .andRoute(POST("/api/api/v1/usuarios"), handler::guardarUsuario)
                .andRoute(GET("/api/v1/usuarios"), handler::getUsuario)
                .andRoute(POST("/api/v1/login"), handler::loginUsuario);
    }
}
