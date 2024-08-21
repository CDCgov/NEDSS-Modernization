package gov.cdc.nbs.search.redirect.simple;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gov.cdc.nbs.option.Option;
import gov.cdc.nbs.time.json.FormattedLocalDateJsonSerializer;

import java.time.LocalDate;

record SimplePatientSearchCriteria(
    String lastName,
    String firstName,
    @JsonSerialize(using = FormattedLocalDateJsonSerializer.class)
    LocalDate dateOfBirth,
    Option gender,
    String id
) {


}
