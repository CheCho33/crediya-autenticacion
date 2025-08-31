package co.com.crediya.autenticacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = {
        "co.com.crediya.autenticacion.config",
        "co.com.crediya.autenticacion.r2dbc",
        "co.com.crediya.autenticacion.api",
        "co.com.crediya.autenticacion.jwtservice"
})
@ConfigurationPropertiesScan
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
