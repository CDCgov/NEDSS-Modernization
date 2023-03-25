package gov.cdc.nbs.patient.document;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Component
class TestDocuments {
  private final Collection<Long> identifiers;

  TestDocuments() {
    identifiers = new ArrayList<>();
  }

  void available(final long patient) {
    this.identifiers.add(patient);
  }

  void reset() {
    this.identifiers.clear();
  }

  public Optional<Long> maybeOne() {
    return this.identifiers.stream().findFirst();
  }

  public long one() {
    return maybeOne()
        .orElseThrow(() -> new IllegalStateException("there is no document"));
  }

}
