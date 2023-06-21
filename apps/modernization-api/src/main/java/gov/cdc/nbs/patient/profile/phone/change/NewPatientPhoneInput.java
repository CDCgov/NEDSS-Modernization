package gov.cdc.nbs.patient.profile.phone.change;

import java.time.Instant;

public record NewPatientPhoneInput(
    long patient,
    Instant asOf,
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
