package gov.cdc.nbs.patient.profile.gender;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.cdc.nbs.time.json.FormattedLocalDateJsonDeserializer;

import java.time.LocalDate;

public record GenderDemographic(
    @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class)
    LocalDate asOf,
    String current,
    String unknownReason,
    String transgenderInformation,
    String additionalGender
) {

  GenderDemographic(final LocalDate asOf) {
    this(asOf, null, null, null, null);
  }

  GenderDemographic withCurrent(final String value) {
    return new GenderDemographic(asOf(), value, unknownReason(), transgenderInformation(), additionalGender());
  }

  GenderDemographic withUnknownReason(final String value) {
    return new GenderDemographic(asOf(), current(), value, transgenderInformation(), additionalGender());
  }

  GenderDemographic withTransgenderInformation(final String value) {
    return new GenderDemographic(asOf(), current(), unknownReason(), value, additionalGender());
  }

  GenderDemographic withAdditionalGender(final String value) {
    return new GenderDemographic(asOf(), current(), unknownReason(), transgenderInformation(), value);
  }
}
