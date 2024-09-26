package gov.cdc.nbs.patient.profile.address;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.cdc.nbs.time.json.FormattedLocalDateJsonDeserializer;

import java.time.LocalDate;

public record AddressDemographic(
    @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class)
    LocalDate asOf,
    String type,
    String use,
    String address1,
    String address2,
    String city,
    String state,
    String zipcode,
    String county,
    String censusTract,
    String country,
    String comment
) {

}
