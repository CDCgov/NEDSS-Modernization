package gov.cdc.nbs.patient.demographics.name;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.cdc.nbs.time.json.FormattedLocalDateJsonDeserializer;

import java.time.LocalDate;

public record NameDemographic(
    @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class) 
    LocalDate asOf,
    String type,
    String prefix,
    String first,
    String middle,
    String secondMiddle,
    String last,
    String secondLast,
    String suffix,
    String degree,
    Short sequence) {

  public NameDemographic(
      LocalDate asOf,
      String type) {
    this(asOf, type, null, null, null, null, null, null, null, null, null);
  }

  public NameDemographic withAsOf(final LocalDate value) {
    return new NameDemographic(
        value,
        type(),
        prefix(),
        first(),
        middle(),
        secondMiddle(),
        last(),
        secondLast(),
        suffix(),
        degree(),
        sequence());
  }

  public NameDemographic withType(final String value) {
    return new NameDemographic(
        asOf(),
        value,
        prefix(),
        first(),
        middle(),
        secondMiddle(),
        last(),
        secondLast(),
        suffix(),
        degree(),
        sequence());
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
        degree(),
        sequence());
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
        degree(),
        sequence());
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
        degree(),
        sequence());
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
        degree(),
        sequence());
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
        degree(),
        sequence());
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
        degree(),
        sequence());
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
        degree(),
        sequence());
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
        value,
        sequence());
  }

}
