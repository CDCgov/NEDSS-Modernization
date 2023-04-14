package gov.cdc.nbs.patient.profile.birth;

import gov.cdc.nbs.geo.country.SimpleCountry;
import gov.cdc.nbs.geo.state.SimpleState;

import java.time.Instant;
import java.time.LocalDate;

record PatientBirth(
    long patient,
    long id,
    short version,
    Instant asOf,
    LocalDate bornOn,
    Integer age,
    MultipleBirth multipleBirth,
    String city,
    SimpleState state,
    SimpleCountry country
) {

    record MultipleBirth(String id, String description) {
    }

}
