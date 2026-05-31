package com.lucasmarques.clientapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API do meu Sistema de cadastro de clients")
                        .version("1.0.0")
                        .description("Documentação detalhada da API da aplicação.")
                        .contact(new Contact().name("Lucas Marques").email("lucaamarques2406@gmail.com")));
    }
}
