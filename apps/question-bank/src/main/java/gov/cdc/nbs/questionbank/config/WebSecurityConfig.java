package gov.cdc.nbs.questionbank.config;

import gov.cdc.nbs.authentication.SecurityConfigurer;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@EnableJpaRepositories({"gov.cdc.nbs.questionbank"})
@EntityScan({"gov.cdc.nbs.questionbank"})
class WebSecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(
      final HttpSecurity http, final SecurityConfigurer configurer) throws Exception {
    return configurer.configure(http).build();
  }
}
