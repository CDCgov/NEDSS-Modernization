package gov.cdc.nbs.patient.profile.names;

import java.time.Instant;

public record NameDemographic(
    Instant asOf,
    String type,
    String prefix,
    String first,
    String middle,
    String secondMiddle,
    String last,
    String secondLast,
    String suffix,
    String degree
) {

  public NameDemographic(
      Instant asOf,
      String type) {
    this(asOf, type, null, null, null, null, null, null, null, null);
  }

  public NameDemographic withPrefix(final String value) {
    return new NameDemographic(
        asOf(),
        type(),
        value,
        first(),
        middle(),
        secondMiddle(),
        last(),
        secondLast(),
        suffix(),
        degree()
    );
  }

  public NameDemographic withFirst(final String value) {
    return new NameDemographic(
        asOf(),
        type(),
        prefix(),
        value,
        middle(),
        secondMiddle(),
        last(),
        secondLast(),
        suffix(),
        degree()
    );
  }

  public NameDemographic withMiddle(final String value) {
    return new NameDemographic(
        asOf(),
        type(),
        prefix(),
        first(),
        value,
        secondMiddle(),
        last(),
        secondLast(),
        suffix(),
        degree()
    );
  }

  public NameDemographic withSecondMiddle(final String value) {
    return new NameDemographic(
        asOf(),
        type(),
        prefix(),
        first(),
        middle(),
        value,
        last(),
        secondLast(),
        suffix(),
        degree()
    );
  }


  public NameDemographic withLast(final String value) {
    return new NameDemographic(
        asOf(),
        type(),
        prefix(),
        first(),
        middle(),
        secondMiddle(),
        value,
        secondLast(),
        suffix(),
        degree()
    );
  }

  public NameDemographic withSecondLast(final String value) {
    return new NameDemographic(
        asOf(),
        type(),
        prefix(),
        first(),
        middle(),
        secondMiddle(),
        last(),
        value,
        suffix(),
        degree()
    );
  }

  public NameDemographic withSuffix(final String value) {
    return new NameDemographic(
        asOf(),
        type(),
        prefix(),
        first(),
        middle(),
        secondMiddle(),
        last(),
        secondLast(),
        value,
        degree()
    );
  }

  public NameDemographic withDegree(final String value) {
    return new NameDemographic(
        asOf(),
        type(),
        prefix(),
        first(),
        middle(),
        secondMiddle(),
        last(),
        secondLast(),
        suffix(),
        value
    );
  }

}