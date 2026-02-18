package gov.cdc.nbs.patient.demographics.mortality;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.cdc.nbs.time.json.FormattedLocalDateJsonDeserializer;
import java.time.LocalDate;

public record MortalityDemographic(
    @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class) LocalDate asOf,
    String deceased,
    @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class) LocalDate deceasedOn,
    String city,
    String state,
    String county,
    String country) {

  public MortalityDemographic(final LocalDate asOf) {
    this(asOf, null, null, null, null, null, null);
  }

  public MortalityDemographic withAsOf(final LocalDate asOf) {
    return new MortalityDemographic(
        asOf, deceased(), deceasedOn(), city(), state(), county(), country());
  }

  public MortalityDemographic withDeceased(final String deceased) {
    return new MortalityDemographic(
        asOf(), deceased, deceasedOn(), city(), state(), county(), country());
  }

  public MortalityDemographic withDeceasedOn(final LocalDate deceasedOn) {
    return new MortalityDemographic(
        asOf(), deceased(), deceasedOn, city(), state(), county(), country());
  }

  public MortalityDemographic withCity(final String city) {
    return new MortalityDemographic(
        asOf(), deceased(), deceasedOn(), city, state(), county(), country());
  }

  public MortalityDemographic withState(final String state) {
    return new MortalityDemographic(
        asOf(), deceased(), deceasedOn(), city(), state, county(), country());
  }

  public MortalityDemographic withCounty(final String county) {
    return new MortalityDemographic(
        asOf(), deceased(), deceasedOn(), city(), state(), county, country());
  }

  public MortalityDemographic withCountry(final String country) {
    return new MortalityDemographic(
        asOf(), deceased(), deceasedOn(), city(), state(), county(), country);
  }
}
