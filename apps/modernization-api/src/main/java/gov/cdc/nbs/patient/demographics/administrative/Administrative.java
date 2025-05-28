package gov.cdc.nbs.patient.demographics.administrative;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.cdc.nbs.time.json.FormattedLocalDateJsonDeserializer;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Administrative(
    @JsonProperty(required = true)
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
