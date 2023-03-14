package gov.cdc.nbs.patient.document;

import java.time.Instant;

public record PatientDocument (
    long document,
    Instant receivedOn,
    String type,
    String sendingFacility,
    Instant reportedOn,
    String condition,
    String event,
    Investigation associatedWith
) {

  public record Investigation(
      long id,
      String local
  ) {
  }

}
