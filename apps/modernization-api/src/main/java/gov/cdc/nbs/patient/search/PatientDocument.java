package gov.cdc.nbs.patient.search;

import com.fasterxml.jackson.annotation.JsonProperty;

record PatientDocument(
    @JsonProperty("person_uid")
    long identifier
) {

    record Name(
        String first,
        String last,
        String middle,
        String prefix,
        String suffix
    ){}

}
