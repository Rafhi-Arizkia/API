package com.web.api.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringdocConfig {
    @Bean
    public OpenAPI costumOpenAPI(){
        return  new OpenAPI().info(new Info().title("API documentation").version("1.0.0"));
    }
}
