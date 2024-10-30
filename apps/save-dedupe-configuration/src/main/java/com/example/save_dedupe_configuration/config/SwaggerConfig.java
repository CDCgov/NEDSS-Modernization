package com.example.save_dedupe_configuration.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Deduplication Configuration API")
                        .description("API documentation for managing data elements, pass configurations, blocking, and matching criteria for NBS deduplication")
                        .version("1.0"));
    }

    @Bean
    public GroupedOpenApi configurationsApi() {
        return GroupedOpenApi.builder()
                .group("configurations")
                .pathsToMatch("/api/configurations/**")
                .build();
    }

    @Bean
    public GroupedOpenApi dataElementsApi() {
        return GroupedOpenApi.builder()
                .group("data-elements")
                .pathsToMatch("/api/data-elements/**")
                .build();
    }
}
