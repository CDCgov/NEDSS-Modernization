package gov.cdc.nbs.time;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ClockProvider {

  @Bean
  Clock clock() {
    return Clock.systemDefaultZone();
  }
}
