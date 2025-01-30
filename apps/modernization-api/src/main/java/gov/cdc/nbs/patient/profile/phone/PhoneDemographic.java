package gov.cdc.nbs.patient.profile.phone;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.cdc.nbs.time.json.FormattedLocalDateJsonDeserializer;

import java.time.LocalDate;

public record PhoneDemographic(
    @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class) LocalDate asOf,
    String type,
    String use,
    String countryCode,
    String phoneNumber,
    String extension,
    String email,
    String url,
    String comment
) {
}
