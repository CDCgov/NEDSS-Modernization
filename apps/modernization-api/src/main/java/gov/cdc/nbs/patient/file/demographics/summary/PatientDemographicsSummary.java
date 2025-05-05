package gov.cdc.nbs.patient.file.demographics.summary;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.cdc.nbs.demographics.address.DisplayableAddress;
import gov.cdc.nbs.demographics.indentification.DisplayableIdentification;
import gov.cdc.nbs.demographics.phone.DisplayablePhone;

import java.util.Collection;

@JsonInclude(JsonInclude.Include.NON_NULL)
record PatientDemographicsSummary(
    DisplayableAddress address,
    DisplayablePhone phone,
    String email,
    String ethnicity,
    Collection<DisplayableIdentification> identifications,
    Collection<String> races
) {
}
