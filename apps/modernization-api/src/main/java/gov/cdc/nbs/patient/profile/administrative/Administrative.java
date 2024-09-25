package gov.cdc.nbs.patient.profile.administrative;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.cdc.nbs.time.json.FormattedLocalDateJsonDeserializer;

import java.time.LocalDate;

public record Administrative(
    @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class)
    LocalDate asOf,
    String comment
) {

  public Administrative(final LocalDate asOf) {
    this(asOf, null);
  }

  public Administrative withAsOf(final LocalDate value) {
    return new Administrative(value, comment());
  }

  public Administrative withComment(final String value) {
    return new Administrative(asOf(), value);
  }
}
