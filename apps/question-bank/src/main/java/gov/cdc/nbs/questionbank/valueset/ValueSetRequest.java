package gov.cdc.nbs.questionbank.valueset;

public sealed interface ValueSetRequest {
	

	record createValueSet(
			String code,
			String description,
			String  name,
			String codeSet
			) implements ValueSetRequest  {
		
	}
}
