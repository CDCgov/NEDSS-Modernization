package gov.cdc.nbs.questionbank.valueset.util;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.valueset.response.ValueSet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ValueSetHolder {
	Page<ValueSet> valueSetResults;
	Codeset valueSet;

}
