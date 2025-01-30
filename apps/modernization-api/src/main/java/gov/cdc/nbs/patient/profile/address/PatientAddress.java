package gov.cdc.nbs.patient.profile.address;

import gov.cdc.nbs.geo.country.SimpleCountry;
import gov.cdc.nbs.geo.county.SimpleCounty;
import gov.cdc.nbs.geo.state.SimpleState;

import java.time.LocalDate;

public record PatientAddress(
    long patient,
    long id,
    short version,
    LocalDate asOf,
    Type type,
    Use use,
    String address1,
    String address2,
    String city,
    SimpleCounty county,
    SimpleState state,
    String zipcode,
    SimpleCountry country,
    String censusTract,
    String comment) {

  record Type(
      String id,
      String description) {
  }


  record Use(
      String id,
      String description) {
  }

}
