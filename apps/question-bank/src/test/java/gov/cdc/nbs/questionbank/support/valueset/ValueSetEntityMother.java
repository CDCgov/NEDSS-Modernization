package gov.cdc.nbs.questionbank.support.valueset;

import java.time.Instant;
import java.util.ArrayList;

import org.apache.kafka.common.Uuid;

import gov.cdc.nbs.questionbank.entity.CodeSetGroupMetadatum;
import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.entity.CodesetId;
import gov.cdc.nbs.questionbank.valueset.command.ValueSetCommand;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetRequest;
import gov.cdc.nbs.questionbank.valueset.util.ValueSetConstants;

public class ValueSetEntityMother {
	
	public static Codeset valueSet() {
		ValueSetRequest request = valueSetRequest();
		Codeset valueSet = new Codeset(asAdd(request,1234l));
		CodesetId id = new CodesetId();
		id.setClassCd(ValueSetConstants.CREATE_CLASS_CD);
		id.setCodeSetNm(request.getValueSetNm());
		valueSet.setId(id);
		return valueSet;
		
	}
	
	
	public ValueSetRequest.CreateCodedValue getCodedValue() {
		ValueSetRequest.CreateCodedValue valueConcept = new ValueSetRequest.CreateCodedValue();
		valueConcept.setCodeDescTxt("codeDescTxt");
		valueConcept.setCodeShortDescTxt("codeShortDescTxt");
		valueConcept.setCodeSystemCd("codeSystemCd");
		valueConcept.setCodeSystemDescTxt("codeSystemDescTxt");
		valueConcept.setEffectiveFromTime(Instant.now());
		valueConcept.setParentIsCd("parentIsCd");
		valueConcept.setSourceConceptId("sourceConceptId");
		valueConcept.setSuperCodeSetNm("superCodeSetNm");
		valueConcept.setSuperCode("superCode");
		valueConcept.setStatusCd(Character.valueOf('C'));
		valueConcept.setStatusTime(Instant.now());
		valueConcept.setConceptTypeCd("conceptTypeCd");
		valueConcept.setConceptCode("conceptCode");
		valueConcept.setConceptNm("conceptNm");
		valueConcept.setConceptPreferredNm("conceptPreferredNm");
		valueConcept.setConceptStatusCd("conceptStatusCd");
		valueConcept.setConceptStatusTime(Instant.now());
		valueConcept.setCodeSystemVersionNbr("codeSystemVersionNbr");
		valueConcept.setConceptOrderNbr(Integer.valueOf(10));
		valueConcept.setEffectiveToTime(Instant.now().plusSeconds(500));
		valueConcept.setIsModifiableInd(Character.valueOf('Y'));
		valueConcept.setNbsUid(10);
		valueConcept.setAdminComments("adminComments");
		valueConcept.setAddTime(Instant.now());
		valueConcept.setAddUserId(Uuid.randomUuid().getLeastSignificantBits());

		return valueConcept;

	}
	
	public static ValueSetRequest valueSetRequest() {
		ValueSetRequest request = new ValueSetRequest();
		request.setValueSetNm("codeSetNm");
		request.setCodeSetDescTxt("codeDescTxt");
		request.setAssigningAuthorityCd("cssigningAuthorityCd");
		request.setAssigningAuthorityDescTxt("assigningDescTx");
		request.setCodeSetDescTxt("codeSetDescTxt");
		request.setEffectiveFromTime(Instant.now());
		request.setEffectiveToTime(Instant.now().plusSeconds(500));
		request.setIsModifiableInd(Character.valueOf('Y'));
		request.setNbsUid(10);
		request.setSourceVersionTxt("sourceVersionTxt");
		request.setSourceDomainNm("SourceDomainNm");
		request.setStatusCd("statusCd");
		request.setStatusToTime(Instant.now());
		request.setCodeSetGroup(new CodeSetGroupMetadatum());
		request.setAdminComments("adminComments");
		request.setValueSetNm("valueSetNm");
		request.setLdfPicklistIndCd(Character.valueOf('L'));
		request.setValueSetCode("valueSetCode");
		request.setValueSetTypeCd("valueSetTypeCd");
		request.setValueSetOid("valueSetOid");
		request.setValueSetStatusCd("valueSetStatusCd");
		request.setValueSetStatusTime(Instant.now());
		request.setParentIsCd(Uuid.randomUuid().getLeastSignificantBits());
		request.setAddTime(Instant.now());
		request.setAddUserId(Uuid.randomUuid().getLeastSignificantBits());
		request.setValues(new ArrayList<>());
		return request;
		
	}
	
	private static ValueSetCommand.AddValueSet asAdd(final ValueSetRequest request, long userId) {
		return new ValueSetCommand.AddValueSet(
				request.getAssigningAuthorityCd(),
				request.getAssigningAuthorityDescTxt(), 
				request.getCodeSetDescTxt(), 
				request.getEffectiveFromTime() != null ? request.getEffectiveFromTime() : Instant.now() ,
				request.getEffectiveToTime(), 
				request.getIsModifiableInd(), 
				request.getNbsUid(),
				request.getSourceVersionTxt(), 
				request.getSourceDomainNm(), 
				"A",
				request.getStatusToTime(), 
				request.getCodeSetGroup(), 
				request.getAdminComments(),
				request.getValueSetNm().toUpperCase(), 
				request.getLdfPicklistIndCd(), 
				request.getValueSetCode(),
				request.getValueSetTypeCd(), 
				request.getValueSetOid(), 
				request.getValueSetStatusCd(),
				request.getValueSetStatusTime(),
				request.getParentIsCd(), 
				Instant.now(), 
				userId);

	}

}
