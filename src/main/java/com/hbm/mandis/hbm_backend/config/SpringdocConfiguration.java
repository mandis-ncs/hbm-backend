package com.hbm.mandis.hbm_backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringdocConfiguration {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("hbm-api")
                        .version("0.0.1.SNAPSHOT")
                        .description("API for monitoring the heart beat")
                );
    }
}