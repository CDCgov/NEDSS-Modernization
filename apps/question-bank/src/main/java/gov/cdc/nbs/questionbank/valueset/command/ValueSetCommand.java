package gov.cdc.nbs.questionbank.valueset.command;

import java.time.Instant;

public sealed interface ValueSetCommand {

	record AddValueSet (
			String valueSetType,
			String valueSetName,
			String valueSetCode,
			String valueSetDescription,
			Instant addTime,
			Long addUserId
	) implements ValueSetCommand {
	}

}
