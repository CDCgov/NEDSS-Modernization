package gov.cdc.nbs.patient.profile.ethnicity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.cdc.nbs.accumulation.Including;
import gov.cdc.nbs.time.json.FormattedLocalDateJsonDeserializer;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public record EthnicityDemographic(
    @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class) LocalDate asOf,
    String ethnicGroup,
    String unknownReason,
    List<String> detailed
) {

  public EthnicityDemographic(final LocalDate asOf) {
    this(asOf, null, null, Collections.emptyList());
  }

  public EthnicityDemographic withEthnicGroup(final String ethnicGroup) {
    return new EthnicityDemographic(asOf(), ethnicGroup, unknownReason(), detailed());
  }

  public EthnicityDemographic withUnknownReason(final String unknownReason) {
    return new EthnicityDemographic(asOf(), ethnicGroup(), unknownReason, detailed());
  }

  public EthnicityDemographic withDetail(final String detail) {
    return new EthnicityDemographic(asOf(), ethnicGroup(), unknownReason(), Including.include(detailed(), detail));
  }
}
