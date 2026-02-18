package gov.cdc.nbs.questionbank.time;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ClockProvider {

  @Bean
  public Clock clock() {
    return Clock.systemDefaultZone();
  }
}
