package gov.cdc.nbs.patient.profile.phone.change;

import java.time.LocalDate;

public record NewPatientPhoneInput(
    long patient,
    LocalDate asOf,
    String type,
    String use,
    String countryCode,
    String number,
    String extension,
    String email,
    String url,
    String comment
) {
}
