package gov.cdc.nbs.questionbank.valueset.update;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.valueset.ValueSetUpdater;
import gov.cdc.nbs.questionbank.valueset.repository.ValueSetRepository;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetUpdateRequest;
import gov.cdc.nbs.questionbank.valueset.response.UpdatedValueSetResponse;
import gov.cdc.nbs.questionbank.valueset.util.ValueSetConstants;

 class ValueSetUpdaterTest {

	@Mock
	ValueSetRepository valueSetRepository;

	@InjectMocks
	ValueSetUpdater valueSetUpdater;

	public ValueSetUpdaterTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void updateValueSetTest() {
		Codeset original = new Codeset();
		original.setValueSetNm("oldValueSetNm");
		original.setCodeSetDescTxt("oldCodeSetDescTxt");
		when(valueSetRepository.findByCodeSetNm(Mockito.anyString())).thenReturn(Optional.of(original));

		ValueSetUpdateRequest request = new ValueSetUpdateRequest();
		request.setCodeSetName("codeSetNm");
		request.setCodeSetDescTxt("codeSetDescTxt");
		request.setValueSetNm("valueSetNm");
		UpdatedValueSetResponse response = valueSetUpdater.updateValueSet(request);
		assertEquals(request.getValueSetNm(), response.getBody().valueSetNm());
		assertEquals(request.getCodeSetDescTxt(), response.getBody().codeSetDescTxt());
		assertEquals(HttpStatus.OK, response.getStatus());
		assertEquals(ValueSetConstants.UPDATE_SUCCESS_MESSAGE, response.getMessage());

	}

	@Test
	void updateValueSetExceptionTest() {
		final String message = "Could not update ValueSet illegal argument provided";
		when(valueSetRepository.findByCodeSetNm(Mockito.anyString())).thenReturn(Optional.of(new Codeset()));
		when(valueSetRepository.save(Mockito.any())).thenThrow(new IllegalArgumentException(message));
		ValueSetUpdateRequest request = new ValueSetUpdateRequest();
		request.setCodeSetName("codeSetNm");
		request.setCodeSetDescTxt("codeSetDescTxt");
		request.setValueSetNm("valueSetNm");
		UpdatedValueSetResponse response = valueSetUpdater.updateValueSet(request);
		assertEquals(null, response.getBody());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
		assertEquals(message, response.getMessage());

	}

	@Test
	void updateValueSetNoResultTest() {
		when(valueSetRepository.findByCodeSetNm(Mockito.anyString())).thenReturn(Optional.empty());
		ValueSetUpdateRequest request = new ValueSetUpdateRequest();
		request.setCodeSetName("codeSetNm");
		request.setCodeSetDescTxt("codeSetDescTxt");
		request.setValueSetNm("valueSetNm");
		UpdatedValueSetResponse response = valueSetUpdater.updateValueSet(request);
		assertEquals(ValueSetConstants.CODE_SET_NOT_FOUND,response.getMessage());
	}

	@Test
	void updateValueSetNoCodeSetNmTest() {
		ValueSetUpdateRequest request = new ValueSetUpdateRequest();
		UpdatedValueSetResponse response = valueSetUpdater.updateValueSet(request);
		assertEquals(ValueSetConstants.EMPTY_CODE_SET_NM, response.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());

	}

}
