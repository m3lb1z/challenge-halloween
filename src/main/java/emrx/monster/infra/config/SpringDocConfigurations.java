package emrx.monster.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Halloween Monster API")
                .description("API Rest de la aplicación Challenge Halloween, " +
                        "que contiene las funcionalidades de CRUD de monstruos y sus características.")
                .contact(new Contact()
                        .name("Equipo de desarrollo")
                        .email("monster@halloween.com"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://challenge-halloween.com/api/licencia"))
            );
    }
}
