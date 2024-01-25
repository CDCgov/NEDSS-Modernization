package gov.cdc.nbs.patient.documentsrequiringreview;

import java.util.Collection;
import java.util.Objects;

public record PatientActivity(
    CaseReport cases,
    MorbidityReport morbidity,
    LabReport labs
) {

  public PatientActivity {
    Objects.requireNonNull(cases);
    Objects.requireNonNull(morbidity);
    Objects.requireNonNull(labs);
  }

  public record CaseReport(Collection<Long> identifiers, long total) {
  }


  public record MorbidityReport(Collection<Long> identifiers, long total) {
  }


  public record LabReport(Collection<Long> identifiers, long total) {
  }

  public boolean isEmpty () {
    return this.cases().identifiers().isEmpty()
        && this.morbidity().identifiers().isEmpty()
        && this.labs().identifiers().isEmpty();
  }

  public long total() {
    return this.cases().total() + this.morbidity().total() + this.labs().total();
  }

}
