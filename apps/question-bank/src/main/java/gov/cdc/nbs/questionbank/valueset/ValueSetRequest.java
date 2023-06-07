package gov.cdc.nbs.questionbank.valueset;

import java.util.List;

public sealed interface ValueSetRequest {
	

	record createValueSet(
			String code,
			String description,
			String  name,
			String codeSet,
			List<ValueSetRequest.createValue> values
			) implements ValueSetRequest  {
		
	}
	
	record createValue(
		String code,
		String display,
		String value,
		Integer displayOrder,
		String comment		
	) implements ValueSetRequest {
		
	}
}
