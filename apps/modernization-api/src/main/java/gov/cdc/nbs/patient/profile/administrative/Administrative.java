package gov.cdc.nbs.patient.profile.administrative;

import java.time.Instant;

public record Administrative(
    Instant asOf,
    String comment
) {

  public Administrative(final Instant asOf) {
    this(asOf, null);
  }

  public Administrative withComment(final String comment) {
    return new Administrative(asOf(), comment);
  }
}
