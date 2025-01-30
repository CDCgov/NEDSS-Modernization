package gov.cdc.nbs.patient.profile.phone.change;

import java.time.LocalDate;

public record UpdatePatientPhoneInput(
    long patient,
    long id,
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
