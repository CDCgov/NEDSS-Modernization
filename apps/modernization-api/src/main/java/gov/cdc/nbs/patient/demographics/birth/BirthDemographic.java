package gov.cdc.nbs.patient.demographics.birth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.cdc.nbs.time.json.FormattedLocalDateJsonDeserializer;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BirthDemographic(
    @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class)
    LocalDate asOf,
    @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class)
    LocalDate bornOn,
    String sex,
    String multiple,
    Integer order,
    String city,
    String state,
    String county,
    String country
) {

  BirthDemographic(final LocalDate asOf) {
    this(asOf, null, null, null, null, null, null, null, null);
  }

  BirthDemographic withAsOf(final LocalDate asOf) {
    return new BirthDemographic(
        asOf,
        bornOn,
        sex,
        multiple,
        order,
        city,
        state,
        county,
        country
    );
  }

  BirthDemographic withBornOn(final LocalDate value) {
    return new BirthDemographic(
        asOf(),
        value,
        sex(),
        multiple(),
        order(),
        city(),
        state(),
        county(),
        country()
    );
  }

  BirthDemographic withSex(final String value) {
    return new BirthDemographic(
        asOf(),
        bornOn(),
        value,
        multiple(),
        order(),
        city(),
        state(),
        county(),
        country()
    );
  }

  BirthDemographic withMultiple(final String value) {
    return new BirthDemographic(
        asOf(),
        bornOn(),
        sex(),
        value,
        order(),
        city(),
        state(),
        county(),
        country()
    );
  }

  BirthDemographic withOrder(final int value) {
    return new BirthDemographic(
        asOf(),
        bornOn(),
        sex(),
        multiple(),
        value,
        city(),
        state(),
        county(),
        country()
    );
  }

  BirthDemographic withCity(final String value) {
    return new BirthDemographic(
        asOf(),
        bornOn(),
        sex(),
        multiple(),
        order(),
        value,
        state(),
        county(),
        country()
    );
  }

  BirthDemographic withCounty(final String value) {
    return new BirthDemographic(
        asOf(),
        bornOn(),
        sex(),
        multiple(),
        order(),
        city(),
        state(),
        value,
        country()
    );
  }

  BirthDemographic withState(final String value) {
    return new BirthDemographic(
        asOf(),
        bornOn(),
        sex(),
        multiple(),
        order(),
        city(),
        value,
        county(),
        country()
    );
  }

  BirthDemographic withCountry(final String value) {
    return new BirthDemographic(
        asOf(),
        bornOn(),
        sex(),
        multiple(),
        order(),
        city(),
        state(),
        county(),
        value
    );
  }
}
