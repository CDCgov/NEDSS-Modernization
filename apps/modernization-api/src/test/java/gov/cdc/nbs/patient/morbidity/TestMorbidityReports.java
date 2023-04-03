package gov.cdc.nbs.patient.morbidity;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Component
class TestMorbidityReports {
  private final Collection<Long> identifiers;

  TestMorbidityReports() {
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
        .orElseThrow(() -> new IllegalStateException("there is no morbidity report"));
  }

}
