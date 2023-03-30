package gov.cdc.nbs.patient.contact;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class TestContactTracings {

  private final Collection<Long> identifiers;

  public TestContactTracings() {
    identifiers = new ArrayList<>();
  }

  void available(final long patient) {
    this.identifiers.add(patient);
  }

  void reset() {
    this.identifiers.clear();
  }

  public Collection<Long> available() {
    return List.copyOf(this.identifiers);
  }

  public Optional<Long> maybeOne() {
    return this.identifiers.stream().findFirst();
  }


}
