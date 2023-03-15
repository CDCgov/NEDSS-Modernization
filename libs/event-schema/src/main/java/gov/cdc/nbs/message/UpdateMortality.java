package gov.cdc.nbs.message;

import java.time.Instant;

import gov.cdc.nbs.message.enums.Deceased;

public record UpdateMortality(
		Instant asOf,
		Deceased deceased,
		Instant deceasedTime,
		String cityOfDeath,
		String stateOfDeath,
		String countyOfDeath,
		String countryOfDeath
) {

}
