package gov.cdc.nbs.questionbank.valueset.delete;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import gov.cdc.nbs.questionbank.valueset.ValueSetDeletor;
import gov.cdc.nbs.questionbank.valueset.repository.ValueSetRepository;
import gov.cdc.nbs.questionbank.valueset.response.DeleteValueSetResponse;
import gov.cdc.nbs.questionbank.valueset.util.ValueSetConstants;

 class ValueSetDeletorTest {
	@Mock
	ValueSetRepository valueSetRepository;

	@InjectMocks
	ValueSetDeletor valueSetDeletor;

	ValueSetDeletorTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void deleteValueSetTest() {
		String codeSetNm = "testCodeSetNm";
		when(valueSetRepository.inActivateValueSet(Mockito.anyString())).thenReturn(1);
		DeleteValueSetResponse response = valueSetDeletor.deleteValueSet(codeSetNm);
		assertEquals(codeSetNm, response.getCodeSetNm());
		assertEquals(HttpStatus.OK, response.getStatus());
		assertEquals(ValueSetConstants.SUCCESS_MESSAGE, response.getMessage());

	}

	@Test
	void deleteValueSetUpdateFailureTest() {
		String codeSetNm = "testCodeSetNm";
		when(valueSetRepository.inActivateValueSet(Mockito.anyString())).thenReturn(0);
		DeleteValueSetResponse response = valueSetDeletor.deleteValueSet(codeSetNm);
		assertEquals(codeSetNm, response.getCodeSetNm());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
		assertEquals(ValueSetConstants.DELETE_FAILURE_MESSAGE, response.getMessage());

	}

	@Test
	void deleteValueSetNullParamTest() {
		DeleteValueSetResponse response = valueSetDeletor.deleteValueSet(null);
		assertEquals(ValueSetConstants.EMPTY_CODE_SET_NM, response.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
	}

	@Test
	void deleteValueSetExceptionTest() {
		final String message = "Illegal argument provided";
		when(valueSetRepository.inActivateValueSet(Mockito.anyString()))
				.thenThrow(new IllegalArgumentException(message));
		DeleteValueSetResponse response = valueSetDeletor.deleteValueSet("testCodeSetNm");
		assertEquals(null, response.getCodeSetNm());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
		assertEquals(message, response.getMessage());

	}
}
