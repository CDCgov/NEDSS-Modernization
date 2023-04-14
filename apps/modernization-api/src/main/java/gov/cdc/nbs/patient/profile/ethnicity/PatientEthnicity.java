package gov.cdc.nbs.patient.profile.ethnicity;

import java.time.Instant;
import java.util.Collection;

record PatientEthnicity(
    long patient,
    long id,
    short version,
    Instant asOf,
    EthnicGroup ethnicGroup,
    UnknownReason unknownReason,
    Collection<Ethnicity> detailed
) {

    record EthnicGroup(
        String id,
        String description
    ) {
    }


    record UnknownReason(
        String id,
        String description
    ) {
    }


    record Ethnicity(
        String id,
        String description
    ) {
    }
}
