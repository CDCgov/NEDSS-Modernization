package gov.cdc.nbs.patient.demographics.phone;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.cdc.nbs.time.json.FormattedLocalDateJsonDeserializer;
import java.time.LocalDate;

public record PhoneDemographic(
    Long identifier,
    @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class) LocalDate asOf,
    String type,
    String use,
    String countryCode,
    String phoneNumber,
    String extension,
    String email,
    String url,
    String comment) {

  public static PhoneDemographic phoneNumber(
      final LocalDate asOf, final String type, final String use, final String number) {
    return new PhoneDemographic(null, asOf, type, use, null, number, null, null, null, null);
  }

  public static PhoneDemographic email(
      final LocalDate asOf, final String type, final String use, final String email) {
    return new PhoneDemographic(null, asOf, type, use, null, null, null, email, null, null);
  }

  public PhoneDemographic withPhoneValues(
      final LocalDate asOf, final String type, final String use, final String number) {
    return new PhoneDemographic(
        identifier(),
        asOf,
        type,
        use,
        countryCode(),
        number,
        extension(),
        email(),
        url(),
        comment());
  }

  public PhoneDemographic withEmailValues(
      final LocalDate asOf, final String type, final String use, final String emailAddress) {
    return new PhoneDemographic(
        identifier(),
        asOf,
        type,
        use,
        countryCode(),
        phoneNumber(),
        extension(),
        emailAddress,
        url(),
        comment());
  }
}
