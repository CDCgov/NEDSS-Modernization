package gov.cdc.nbs.patient;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class TestPatients {

  private final Collection<Long> identifiers;

  public TestPatients() {
    identifiers = new ArrayList<>();
  }

  void available(final long patient) {
    this.identifiers.add(patient);
  }

  void unavailable(final long patient) {
    this.identifiers.remove(patient);
  }

  void reset() {
    this.identifiers.clear();
  }

  public Collection<Long> available() {
    return List.copyOf(this.identifiers);
  }

  public Optional<Long> one() {
    return this.identifiers.stream().findFirst();
  }


}
