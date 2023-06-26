package gov.cdc.nbs.questionbank.valueset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.questionbank.valueset.repository.ValueSetRepository;

@Service
public class ValueSetUpdater {

	@Autowired
	private ValueSetRepository valueSetRepository;
	
	
	public UpdatedValueSetResponse updateValueSet() {
		
	}

}
