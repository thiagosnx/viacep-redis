package cepredis.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myOpenAPI() {

        Contact contact = new Contact();

        contact.setEmail("thiagosantanacq@gmail.com");
        contact.setName("Thiago Santana");
        contact.setUrl("");

        Info info = new Info()
                .title("ViaCep API Consume")
                .version("1.0")
                .description("Consumindo API ViaCEP")
                .contact(contact);

        return new OpenAPI().info(info);
    }
}
