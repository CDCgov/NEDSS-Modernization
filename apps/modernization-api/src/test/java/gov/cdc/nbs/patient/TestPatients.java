package gov.cdc.nbs.patient;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Component
public class TestPatients {

  private final Collection<Long> identifiers;
  private final TestPatientCleaner cleaner;

  public TestPatients(final TestPatientCleaner cleaner) {
    this.cleaner = cleaner;
    identifiers = new ArrayList<>();
  }

  void available(final long patient) {
    this.identifiers.add(patient);
  }

  void reset() {
    this.identifiers.forEach(cleaner::clean);
    this.identifiers.clear();
  }

  public Optional<Long> maybeOne() {
    return this.identifiers.stream().findFirst();
  }

  public long one() {
    return maybeOne().orElseThrow(() -> new IllegalStateException("there is no patient"));
  }

}
