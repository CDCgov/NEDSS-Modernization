package gov.cdc.nbs.questionbank.valueset.command;

import gov.cdc.nbs.questionbank.entity.CodeSetGroupMetadatum;

import java.time.Instant;

public sealed interface ValueSetCommand {
	record AddValueSet (
			String valueSetType,
			String valueSetName,
			String valueSetCode,
			String valueSetDescription,
			CodeSetGroupMetadatum codeSetGroupMetadatum,
			Instant addTime,
			Long addUserId
	) implements ValueSetCommand {
	}

}
