package gov.cdc.nbs.search.redirect.simple;

import gov.cdc.nbs.option.Option;

import java.time.LocalDate;

record SimplePatientSearchCriteria(
    String lastName,
    String firstName,
    LocalDate dateOfBirth,
    Option gender,
    String id
) {


}
