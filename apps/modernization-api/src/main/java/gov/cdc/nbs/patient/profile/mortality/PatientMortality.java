package gov.cdc.nbs.patient.profile.mortality;

import gov.cdc.nbs.geo.country.SimpleCountry;
import gov.cdc.nbs.geo.county.SimpleCounty;
import gov.cdc.nbs.geo.state.SimpleState;

import java.time.LocalDate;

record PatientMortality(
    long patient,
    long id,
    short version,
    LocalDate asOf,
    Deceased deceased,
    LocalDate deceasedOn,
    String city,
    SimpleState state,
    SimpleCounty county,
    SimpleCountry country
) {
    record Deceased(String id, String description) {
    }
}
