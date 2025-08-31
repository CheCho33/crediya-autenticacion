package co.com.crediya.autenticacion.config;

import co.com.crediya.autenticacion.model.sesion.gateways.SesionRepository;
import co.com.crediya.autenticacion.model.usuario.gateways.PasswordEncrypterGateway;
import co.com.crediya.autenticacion.model.usuario.gateways.UsuarioRepository;
import co.com.crediya.autenticacion.usecase.sesion.LoginSesionUseCase;
import co.com.crediya.autenticacion.usecase.usuario.RegistrarUsuarioUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.com.crediya.autenticacion.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {

    @Bean
    public RegistrarUsuarioUseCase registrarUsuarioUseCase(UsuarioRepository usuarioRepository) {
        return new RegistrarUsuarioUseCase(usuarioRepository);
    }

    @Bean
    public LoginSesionUseCase loginSesionUseCase(UsuarioRepository usuarioRepository, SesionRepository sesionRepository,  PasswordEncrypterGateway passwordEncrypterGateway) {
        return new LoginSesionUseCase(usuarioRepository, sesionRepository, passwordEncrypterGateway);
    }

}
