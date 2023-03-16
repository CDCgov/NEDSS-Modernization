package gov.cdc.nbs.message.patient.event;

import java.time.Instant;

import gov.cdc.nbs.message.enums.Deceased;

public record UpdateMortalityEvent(
        long patient,
        String requestId,
        long updatedBy,
        Instant asOf,
        Deceased deceased,
        Instant deceasedTime,
        String cityOfDeath,
        String stateOfDeath,
        String countyOfDeath,
        String countryOfDeath) {
}
