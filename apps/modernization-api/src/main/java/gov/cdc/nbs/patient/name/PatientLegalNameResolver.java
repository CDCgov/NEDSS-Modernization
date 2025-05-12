package gov.cdc.nbs.patient.name;

import gov.cdc.nbs.demographics.name.DisplayableName;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Optional;

@Component
public class PatientLegalNameResolver {

  private final Clock clock;
  private final PatientLegalNameFinder finder;


  PatientLegalNameResolver(
      final Clock clock,
      final PatientLegalNameFinder finder
  ) {
    this.clock = clock;
    this.finder = finder;
  }

  public Optional<DisplayableName> resolve(final long patient) {
    return resolve(patient, LocalDate.now(clock));
  }

  public Optional<DisplayableName> resolve(final long patient, final LocalDate asOf) {
    return this.finder.find(patient, asOf);
  }
}
