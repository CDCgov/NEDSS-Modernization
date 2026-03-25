package gov.cdc.nbs.authentication;

import java.util.List;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan({"gov.cdc.nbs.authentication"})
@EnableJpaRepositories({"gov.cdc.nbs.authentication"})
@EntityScan({"gov.cdc.nbs.authentication.entity"})
@Configuration
@EnableConfigurationProperties(AuthenticationConfiguration.PathSettings.class)
class AuthenticationConfiguration {

  @ConfigurationProperties(prefix = "nbs.authentication.paths")
  record PathSettings(List<String> ignored) {}

  @Bean
  IgnoredPaths configuredIgnoredPaths(final PathSettings settings) {
    return new IgnoredPaths(settings.ignored());
  }
}
