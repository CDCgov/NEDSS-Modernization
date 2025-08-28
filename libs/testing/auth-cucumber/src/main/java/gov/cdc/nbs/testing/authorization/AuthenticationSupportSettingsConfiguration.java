package gov.cdc.nbs.testing.authorization;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
class AuthenticationSupportSettingsConfiguration {

  @Bean
  AuthenticationSupportSettings authenticationSettings(
      final @Value("${testing.data.createdBy:9999}") long createdBy) {
    return new AuthenticationSupportSettings(
        createdBy,
        LocalDateTime.now()
    );
  }
}
