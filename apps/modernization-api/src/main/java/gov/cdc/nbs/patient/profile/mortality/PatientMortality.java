package gov.cdc.nbs.patient.profile.mortality;

import gov.cdc.nbs.geo.country.SimpleCountry;
import gov.cdc.nbs.geo.state.SimpleState;

import java.time.Instant;
import java.time.LocalDate;

record PatientMortality(
    long patient,
    long id,
    short version,
    Instant asOf,
    Deceased deceased,
    LocalDate deceasedOn,
    String city,
    SimpleState state,
    SimpleCountry country
) {
    record Deceased(String id, String description) {
    }
}
