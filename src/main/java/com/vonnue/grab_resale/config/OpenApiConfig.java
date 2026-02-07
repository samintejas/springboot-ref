package com.vonnue.grab_resale.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Grab Resale API")
                        .version("0.0.1")
                        .description("Backend API for the Grab Resale portal")
                        .contact(new Contact()
                                .name("Vonnue")
                        )
                );
    }
}
