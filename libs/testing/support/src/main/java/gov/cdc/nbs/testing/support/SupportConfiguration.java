package gov.cdc.nbs.testing.support;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("gov.cdc.nbs.testing.support.concept")
class SupportConfiguration {

  @Bean
  <I> Active<I> active() {
    return new Active<>();
  }

  @Bean
  <I> Available<I> available() {
    return new Available<>();
  }
}
