package gov.cdc.nbs.patient.demographics.address;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.cdc.nbs.time.json.FormattedLocalDateJsonDeserializer;

import java.time.LocalDate;

public record AddressDemographic(
    Long identifier,
    @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class)
    LocalDate asOf,
    String type,
    String use,
    String address1,
    String address2,
    String city,
    String state,
    String zipcode,
    String county,
    String censusTract,
    String country,
    String comment
) {

  public AddressDemographic(
      LocalDate asOf,
      String type,
      String use,
      String address1,
      String address2,
      String city,
      String state,
      String zipcode,
      String county,
      String censusTract,
      String country,
      String comment
  ) {
    this(null, asOf, type, use, address1, address2, city, state, zipcode, county, censusTract, country, comment);
  }

  public AddressDemographic withAsOf(final LocalDate asOf) {
    return new AddressDemographic(
        identifier,
        asOf,
        type,
        use,
        address1,
        address2,
        city,
        state,
        zipcode,
        county,
        censusTract,
        country,
        comment
    );
  }

  public AddressDemographic withType(final String type) {
    return new AddressDemographic(
        identifier,
        asOf,
        type,
        use,
        address1,
        address2,
        city,
        state,
        zipcode,
        county,
        censusTract,
        country,
        comment
    );
  }

  public AddressDemographic withUse(final String use) {
    return new AddressDemographic(
        identifier,
        asOf,
        type,
        use,
        address1,
        address2,
        city,
        state,
        zipcode,
        county,
        censusTract,
        country,
        comment
    );
  }

}
