package gov.cdc.nbs.questionbank.support.valueset;

import java.time.Instant;

import gov.cdc.nbs.questionbank.entity.CodeSetGroupMetadatum;
import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.entity.CodesetId;
import gov.cdc.nbs.questionbank.valueset.command.ValueSetCommand;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetCreateRequest;
import gov.cdc.nbs.questionbank.valueset.util.ValueSetConstants;

public class ValueSetEntityMother {

	public static Codeset valueSet() {
		ValueSetCreateRequest request =
				new ValueSetCreateRequest("valueSetType", "valueSetCode", "valueSetValue", "valueSetDescription");
		Codeset valueSet = new Codeset(asAdd(request,null, 1234l));
		CodesetId id = new CodesetId();
		id.setClassCd(ValueSetConstants.CREATE_CLASS_CD);
		id.setCodeSetNm(request.valueSetCode());
		valueSet.setId(id);
		return valueSet;
	}

	public static ValueSetCommand.AddValueSet asAdd(final ValueSetCreateRequest request,
			CodeSetGroupMetadatum codeSetGroupMetadatum,long userId) {
		return new ValueSetCommand.AddValueSet(
				request.valueSetType(),
				request.valueSetName(),
				request.valueSetCode(),
				request.valueSetDescription(),
				codeSetGroupMetadatum,
				Instant.now(),
				userId);
	}
}
