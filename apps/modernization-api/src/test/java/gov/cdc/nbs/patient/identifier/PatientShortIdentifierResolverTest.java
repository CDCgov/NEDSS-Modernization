package gov.cdc.nbs.patient.identifier;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.OptionalLong;
import org.junit.jupiter.api.Test;

class PatientShortIdentifierResolverTest {

  @Test
  void should_resolve_patient_short_id() {
    PatientIdentifierSettings settings = new PatientIdentifierSettings("prefix", 6329, "suffix");

    PatientShortIdentifierResolver resolver = new PatientShortIdentifierResolver(settings);

    OptionalLong actual = resolver.resolve("prefix112909suffix");

    assertThat(actual).hasValue(106580);
  }

  @Test
  void should_resolve_empty_when_identifier_does_not_match_identifier_settings() {
    PatientIdentifierSettings settings = new PatientIdentifierSettings("prefix", 6329, "suffix");

    PatientShortIdentifierResolver resolver = new PatientShortIdentifierResolver(settings);

    OptionalLong actual = resolver.resolve("other112909invalid");

    assertThat(actual).isNotPresent();
  }
}
