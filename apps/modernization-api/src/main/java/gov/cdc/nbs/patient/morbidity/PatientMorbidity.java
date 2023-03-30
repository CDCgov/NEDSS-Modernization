package gov.cdc.nbs.patient.morbidity;

import java.time.Instant;
import java.util.Collection;

record PatientMorbidity(
    long morbidity,
    Instant receivedOn,
    String provider,
    Instant reportedOn,
    String condition,
    String jurisdiction,
    String event,
    Investigation associatedWith,
    Collection<String> treatments
) {

  record Investigation(
      long id,
      String local,
      String condition
  ) {
  }

}
