package gov.cdc.nbs.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OpenAPIConfiguration {

  @Bean
  OpenAPI openAPI() {
    return new OpenAPI().info(new Info().title("NBS Modernization API"));
  }
}
