package gov.cdc.nbs.patient.profile.birth.change;

import java.time.LocalDate;

record UpdateBirthAndGender(
    long patient,
    LocalDate asOf,
    Birth birth,
    Gender gender
) {

    record Birth(
        LocalDate bornOn,
        String gender,
        String multipleBirth,
        Integer birthOrder,
        String city,
        String state,
        String county,
        String country
    ) {
    }


    record Gender(
        String current,
        String unknownReason,
        String preferred,
        String additional
    ) {
    }
}
