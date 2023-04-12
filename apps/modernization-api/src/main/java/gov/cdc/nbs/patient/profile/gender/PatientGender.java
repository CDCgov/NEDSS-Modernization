package gov.cdc.nbs.patient.profile.gender;

import java.time.Instant;

record PatientGender(
    long patient,
    long id,
    short version,
    Instant asOf,

    Gender birth,
    Gender current,
    UnknownReason unknownReason,
    PreferredGender preferred,
    String additional
) {

    record Gender(String id, String description) {
    }


    record PreferredGender(String id, String description) {
    }


    record UnknownReason(String id, String description) {
    }

}
