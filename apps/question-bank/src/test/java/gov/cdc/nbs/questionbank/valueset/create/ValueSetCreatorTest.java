package gov.cdc.nbs.questionbank.valueset.create;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import gov.cdc.nbs.questionbank.entity.CodeSetGroupMetadatum;
import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.valueset.ValueSetCreator;
import gov.cdc.nbs.questionbank.valueset.repository.CodesetGroupMetadatumRepository;
import gov.cdc.nbs.questionbank.valueset.repository.ValueSetRepository;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetCreateRequest;
import gov.cdc.nbs.questionbank.valueset.response.CreateValueSetResponse;
import gov.cdc.nbs.questionbank.valueset.util.ValueSetConstants;

class ValueSetCreatorTest {

	@Mock
	ValueSetRepository valueSetRepository;

	@Mock
	CodesetGroupMetadatumRepository codeSetGrpMetaRepository;

	@InjectMocks
	ValueSetCreator valueSetCreator;

	private static final long userId = 12345l;

	public ValueSetCreatorTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createValueSetTest() {
		ValueSetCreateRequest request = getValueSetCreateRequest();
		Codeset requestCodeSet = new Codeset(valueSetCreator.asAdd(request, userId));
		CodeSetGroupMetadatum codeSetGrpRequest = new CodeSetGroupMetadatum();
		codeSetGrpRequest.setId(2l);
		requestCodeSet.setCodeSetGroup(codeSetGrpRequest);

		when(valueSetRepository.save(Mockito.any())).thenReturn(requestCodeSet);
		when(valueSetRepository.checkValueSetName(Mockito.anyString())).thenReturn(0l);
		when(codeSetGrpMetaRepository.checkCodeSetGrpMetaDatEntry(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(0l);
		when(valueSetRepository.getCodeSetGroupCeilID()).thenReturn(0);
		when(codeSetGrpMetaRepository.save(Mockito.any())).thenReturn(codeSetGrpRequest);
		CreateValueSetResponse response = valueSetCreator.createValueSet(request, userId);

		assertEquals(requestCodeSet.getAddTime(), response.getBody().addTime());
		assertEquals(requestCodeSet.getAddUserId(), response.getBody().addUserId());
		assertEquals(requestCodeSet.getValueSetNm(), response.getBody().valueSetNm());
		assertEquals(requestCodeSet.getCodeSetGroup().getId(), response.getBody().codeSetGroupId());
		assertEquals(HttpStatus.CREATED, response.getStatus());
		assertEquals(ValueSetConstants.SUCCESS_MESSAGE, response.getMessage());

	}

	@Test
	void createValueSetNameExistsTest() {
		ValueSetCreateRequest request = getValueSetCreateRequest();
		when(valueSetRepository.checkValueSetName(Mockito.anyString())).thenReturn(1l);
		CreateValueSetResponse response = valueSetCreator.createValueSet(request, userId);
		assertEquals(null, response.getBody());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
		assertEquals(ValueSetConstants.VALUE_SET_NAME_EXISTS, response.getMessage());
	}

	@Test
	void createValueSetGroupMetaDataExistsTest() {
		ValueSetCreateRequest request = getValueSetCreateRequest();
		when(valueSetRepository.checkValueSetName(Mockito.anyString())).thenReturn(0l);
		when(codeSetGrpMetaRepository.checkCodeSetGrpMetaDatEntry(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(1l);
		CreateValueSetResponse response = valueSetCreator.createValueSet(request, userId);
		assertEquals(null, response.getBody());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
		assertEquals(ValueSetConstants.CODE_SET_GRP_TEXT_NAME_EXISTS, response.getMessage());
	}

	@Test
	void createValueSetExceptionTest() {
		final String message = "Could not save ValueSet illegal argument provided";
		ValueSetCreateRequest request = getValueSetCreateRequest();
		when(valueSetRepository.checkValueSetName(Mockito.anyString())).thenReturn(0l);
		when(codeSetGrpMetaRepository.checkCodeSetGrpMetaDatEntry(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(0l);
		when(codeSetGrpMetaRepository.save(Mockito.any())).thenThrow(new IllegalArgumentException(message));
		CreateValueSetResponse response = valueSetCreator.createValueSet(request, userId);
		assertEquals(null, response.getBody());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
		assertEquals(message, response.getMessage());
	}

	@Test
	void checkValueSetNameExistsTest() {
		when(valueSetRepository.checkValueSetName(Mockito.anyString())).thenReturn(1l);
		boolean result = valueSetCreator.checkValueSetNameExists("codeNm");
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
		when(valueSetRepository.getCodeSetGroupCeilID()).thenReturn(0);
		long result = valueSetCreator.getCodeSetGroupID();
		assertEquals(9910l, result);
	}

	@Test
	void getCodeSetGroupIDNonDefaultTest() {
		when(valueSetRepository.getCodeSetGroupCeilID()).thenReturn(1);
		when(codeSetGrpMetaRepository.getCodeSetGroupMaxID()).thenReturn(99950l);
		long result = valueSetCreator.getCodeSetGroupID();
		assertEquals(99950l + 10, result);
	}

	private ValueSetCreateRequest getValueSetCreateRequest() {
		return new ValueSetCreateRequest(
				"Type", "valueSetCode", "codeSetNm", "codeDescTxt");
	}
}
