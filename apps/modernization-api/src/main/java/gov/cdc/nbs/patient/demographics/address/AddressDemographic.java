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
  
  public AddressDemographic(final LocalDate asOf) {
    this(null, asOf, null, null, null, null, null, null, null, null, null, null, null);
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

  public AddressDemographic withAddress(final String address) {
    return new AddressDemographic(
        identifier,
        asOf,
        type,
        use,
        address,
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

  public AddressDemographic withAddress2(final String address2) {
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

  public AddressDemographic withCity(final String city) {
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

  public AddressDemographic withState(final String state) {
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

  public AddressDemographic withZipcode(final String zipcode) {
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

  public AddressDemographic withCounty(final String county) {
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

  public AddressDemographic withCensusTract(final String censusTract) {
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

  public AddressDemographic withCountry(final String country) {
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

  public AddressDemographic withComment(final String comment) {
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
