package gov.cdc.nbs.patient.profile.ethnicity;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public record EthnicityDemographic(
    Instant asOf,
    String ethnicGroup,
    String unknownReason,
    List<String> detailed) {

  public EthnicityDemographic(final Instant asOf) {
    this(asOf, null, null, null);
  }

  public EthnicityDemographic withEthnicGroup(final String ethnicGroup) {
    return new EthnicityDemographic(asOf(), ethnicGroup, unknownReason(), detailed());
  }

  public EthnicityDemographic withUnknownReason(final String unknownReason) {
    return new EthnicityDemographic(asOf(), ethnicGroup(), unknownReason, detailed());
  }

  public EthnicityDemographic withDetailed(final String detailed) {
    return new EthnicityDemographic(asOf(), ethnicGroup(), unknownReason(), Arrays.asList(detailed));
  }
}
