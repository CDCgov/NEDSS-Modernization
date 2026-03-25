package gov.cdc.nbs.patient.identifier;

import org.springframework.stereotype.Component;

@Component
public class PatientLocalIdentifierResolver {

  private final PatientIdentifierSettings settings;

  public PatientLocalIdentifierResolver(final PatientIdentifierSettings settings) {
    this.settings = settings;
  }

  public String resolve(final long identifier) {
    long number = identifier + settings.initial();
    return settings.type() + number + settings.suffix();
  }
}
