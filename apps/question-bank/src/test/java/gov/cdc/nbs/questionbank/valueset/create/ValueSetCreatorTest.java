package gov.cdc.nbs.questionbank.valueset.create;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.Period;
import java.util.ArrayList;

import org.apache.kafka.common.Uuid;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import gov.cdc.nbs.questionbank.entity.CodeSet;
import gov.cdc.nbs.questionbank.entity.CodeSetGroupMetaDatum;
import gov.cdc.nbs.questionbank.valueset.CreateValueSetResponse;
import gov.cdc.nbs.questionbank.valueset.ValueSetCreator;
import gov.cdc.nbs.questionbank.valueset.ValueSetRequest;
import gov.cdc.nbs.questionbank.valueset.repository.CodesetGroupMetadatumRepository;
import gov.cdc.nbs.questionbank.valueset.repository.ValueSetRepository;
import gov.cdc.nbs.questionbank.valueset.util.ValueSetConstants;
import gov.cdc.nbs.repository.CodeValueGeneralRepository;

class ValueSetCreatorTest {

	@Mock
	ValueSetRepository valueSetRepository;

	@Mock
	CodeValueGeneralRepository codeValueRepository;

	@Mock
	CodesetGroupMetadatumRepository codeSetGrpMetaRepository;

	@InjectMocks
	ValueSetCreator valueSetCreator;

	public ValueSetCreatorTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createValueSetTest() {
		ValueSetRequest request = getValueSetRequest();
		CodeSet requestCodeSet = new CodeSet(valueSetCreator.asAdd(request));
		CodeSetGroupMetaDatum codeSetGrpRequest = new CodeSetGroupMetaDatum();
		request.setCodeSetGroup(codeSetGrpRequest);

		when(valueSetRepository.save(Mockito.any())).thenReturn(requestCodeSet);
		when(valueSetRepository.checkValueSetName(Mockito.anyString())).thenReturn(0l);
		when(codeSetGrpMetaRepository.checkCodeSetGrpMetaDatEntry(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(0l);
		when(valueSetRepository.getCodeSetGroupCeilID()).thenReturn(0l);
		when(codeSetGrpMetaRepository.save(Mockito.any())).thenReturn(codeSetGrpRequest);
		CreateValueSetResponse response = valueSetCreator.createValueSet(request);
		
		assertEquals(requestCodeSet.getAddTime(),response.getBody().getAddTime());
		assertEquals(requestCodeSet.getAddUserId(),response.getBody().getAddUserId() );
		assertEquals(requestCodeSet.getValueSetNm(),response.getBody().getValueSetNm());
		assertEquals(HttpStatus.CREATED, response.getStatus());
		assertEquals(ValueSetConstants.SUCCESS_MESSAGE,response.getMessage());

	}

	@Test
	void createValueSetWithConceptTest() {
		ValueSetRequest request = getValueSetRequest();
		CodeSet requestCodeSet = new CodeSet(valueSetCreator.asAdd(request));
		CodeSetGroupMetaDatum codeSetGrpRequest = new CodeSetGroupMetaDatum();
		request.setCodeSetGroup(codeSetGrpRequest);

		ValueSetRequest.CreateCodedValue valueConcept = getCodedValue();
		request.getValues().add(valueConcept);

		when(valueSetRepository.save(Mockito.any())).thenReturn(requestCodeSet);
		when(valueSetRepository.checkValueSetName(Mockito.anyString())).thenReturn(0l);
		when(codeSetGrpMetaRepository.checkCodeSetGrpMetaDatEntry(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(0l);
		when(valueSetRepository.getCodeSetGroupCeilID()).thenReturn(0l);
		when(codeSetGrpMetaRepository.save(Mockito.any())).thenReturn(codeSetGrpRequest);
		CreateValueSetResponse response = valueSetCreator.createValueSet(request);
		
		assertEquals(requestCodeSet.getAddTime(),response.getBody().getAddTime());
		assertEquals(requestCodeSet.getAddUserId(),response.getBody().getAddUserId());
		assertEquals(requestCodeSet.getValueSetNm(),response.getBody().getValueSetNm());
		assertEquals(HttpStatus.CREATED,response.getStatus());
		assertEquals(ValueSetConstants.SUCCESS_MESSAGE,response.getMessage());

	}

	@Test
	void createValueSetNameExistsTest() {
		ValueSetRequest request = new ValueSetRequest();
		request.setValueSetNm("codeSetNm");
		request.setCodeSetDescTxt("codeDescTxt");	
		when(valueSetRepository.checkValueSetName(Mockito.anyString())).thenReturn(1l);
		CreateValueSetResponse response = valueSetCreator.createValueSet(request);
		assertEquals(null,response.getBody());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
		assertEquals(ValueSetConstants.VALUE_SET_NAME_EXISTS,response.getMessage());

	}

	@Test
	void createValueSetGroupMetaDataExistsTest() {
		ValueSetRequest request = new ValueSetRequest();
		request.setValueSetNm("codeSetNm");
		request.setCodeSetDescTxt("codeDescTxt");
		when(valueSetRepository.checkValueSetName(Mockito.anyString())).thenReturn(0l);
		when(codeSetGrpMetaRepository.checkCodeSetGrpMetaDatEntry(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(1l);
		CreateValueSetResponse response = valueSetCreator.createValueSet(request);
		assertEquals(null,response.getBody());
		assertEquals(HttpStatus.BAD_REQUEST,response.getStatus());
		assertEquals(ValueSetConstants.CODE_SET_GRP_TEXT_NAME_EXISTS,response.getMessage());

	}

	@Test
	void createValueSetExceptionTest() {
		final String message = "Could not save ValueSet illegal argument provided";
		ValueSetRequest request = new ValueSetRequest();
		request.setValueSetNm("codeSetNm");
		request.setCodeSetDescTxt("codeDescTxt");
		CodeSetGroupMetaDatum codeSetGrpRequest = new CodeSetGroupMetaDatum();
		request.setCodeSetGroup(codeSetGrpRequest);
		when(valueSetRepository.checkValueSetName(Mockito.anyString())).thenReturn(0l);
		when(codeSetGrpMetaRepository.checkCodeSetGrpMetaDatEntry(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(0l);
		when(codeSetGrpMetaRepository.save(Mockito.any())).thenThrow(new IllegalArgumentException(message));
		CreateValueSetResponse response = valueSetCreator.createValueSet(request);
		assertEquals(null, response.getBody());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatus());
		assertEquals(message,response.getMessage());

	}

	@Test
	void checkValueSetNameExistsTest() {
		when(valueSetRepository.checkValueSetName(Mockito.anyString())).thenReturn(1l);
		boolean result = valueSetCreator.checkValueSetNameExists("codeNm")	;
		assertTrue(result);
	}

	@Test
	void checkValueSetNameExistsNullTest() {
		boolean result = valueSetCreator.checkValueSetNameExists(null);
		assertFalse(result);
	}

	@Test
	void checkCodeSetGrpMetaDatEntryTest() {
		when(codeSetGrpMetaRepository.checkCodeSetGrpMetaDatEntry(Mockito.anyString(), Mockito.anyString()))
		.thenReturn(1l);
		boolean result = valueSetCreator.checkCodeSetGrpMetaDatEntry("codeDesc", "codeNm");
		assertTrue(result);

	}

	@Test
	void checkCodeSetGrpMetaDatEntryNullTest() {
		boolean result = valueSetCreator.checkCodeSetGrpMetaDatEntry("codeDesc", null);
		assertFalse(result);

	}

	@Test
	void getCodeSetGroupIDTest() {
		when(valueSetRepository.getCodeSetGroupCeilID()).thenReturn(0l);
		long result =valueSetCreator.getCodeSetGroupID();
		assertEquals(9910l,result);


	}

	@Test
	void getCodeSetGroupIDNonDefaultTest() {
		when(valueSetRepository.getCodeSetGroupCeilID()).thenReturn(1l);
		when(valueSetRepository.getCodeSetGroupMaxID()).thenReturn(99950l);
		long result =valueSetCreator.getCodeSetGroupID();
		assertEquals(99950l + 10, result);


	}

	private ValueSetRequest.CreateCodedValue getCodedValue() {
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

	private ValueSetRequest getValueSetRequest() {
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
		request.setCodeSetGroup(new CodeSetGroupMetaDatum());
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

}
