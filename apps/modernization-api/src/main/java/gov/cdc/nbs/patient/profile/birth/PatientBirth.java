package gov.cdc.nbs.patient.profile.birth;

import gov.cdc.nbs.geo.country.SimpleCountry;
import gov.cdc.nbs.geo.county.SimpleCounty;
import gov.cdc.nbs.geo.state.SimpleState;

import java.time.LocalDate;

record PatientBirth(
    long patient,
    long id,
    short version,
    LocalDate asOf,
    LocalDate bornOn,
    Integer age,
    MultipleBirth multipleBirth,
    Short birthOrder,
    String city,
    SimpleState state,
    SimpleCounty county,
    SimpleCountry country
) {

    record MultipleBirth(String id, String description) {
    }

}
