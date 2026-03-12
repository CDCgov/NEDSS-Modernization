package gov.cdc.nbs.patient.identifier;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class PatientLocalIdentifierGeneratorTestConfiguration {

  @Bean
  @Primary
  PatientLocalIdentifierGenerator testPatientLocalIdentifierGenerator(
      final PatientIdentifierSettings settings) {
    return new TestPatientLocalIdentifierGenerator(settings);
  }

  private static class TestPatientLocalIdentifierGenerator extends PatientLocalIdentifierGenerator {

    private final AtomicLong next;
    private final PatientIdentifierSettings settings;

    private TestPatientLocalIdentifierGenerator(final PatientIdentifierSettings settings) {
      super(null);
      this.next = new AtomicLong(settings.initial());
      this.settings = settings;
    }

    @Override
    public String generate() {
      return settings.type() + this.next.incrementAndGet() + settings.suffix();
    }
  }
}
