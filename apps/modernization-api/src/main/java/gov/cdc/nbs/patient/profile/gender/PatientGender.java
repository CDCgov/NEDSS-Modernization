package gov.cdc.nbs.patient.profile.gender;

import java.time.LocalDate;

record PatientGender(
    long patient,
    long id,
    short version,
    LocalDate asOf,

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
