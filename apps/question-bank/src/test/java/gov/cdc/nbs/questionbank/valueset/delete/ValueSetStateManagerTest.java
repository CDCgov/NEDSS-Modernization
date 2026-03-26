package gov.cdc.nbs.questionbank.valueset.delete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.valueset.ValueSetStateManager;
import gov.cdc.nbs.questionbank.valueset.repository.ValueSetRepository;
import gov.cdc.nbs.questionbank.valueset.response.ValueSetStateChangeResponse;
import gov.cdc.nbs.questionbank.valueset.util.ValueSetConstants;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

class ValueSetStateManagerTest {
  @Mock ValueSetRepository valueSetRepository;

  @InjectMocks ValueSetStateManager valueSetStateManager;

  private String codeSetNm = "testCodeSetNm";
  private final String message = "Illegal argument provided";

  ValueSetStateManagerTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void deleteValueSetTest() {
    when(valueSetRepository.inActivateValueSet(Mockito.anyString())).thenReturn(1);
    ValueSetStateChangeResponse response = valueSetStateManager.deleteValueSet(codeSetNm);
    assertEquals(codeSetNm, response.getCodeSetNm());
    assertEquals(HttpStatus.OK, response.getStatus());
    assertEquals(ValueSetConstants.DELETE_SUCCESS_MESSAGE, response.getMessage());
  }

  @Test
  void deleteValueSetUpdateFailureTest() {
    when(valueSetRepository.inActivateValueSet(Mockito.anyString())).thenReturn(0);
    ValueSetStateChangeResponse response = valueSetStateManager.deleteValueSet(codeSetNm);
    assertEquals(codeSetNm, response.getCodeSetNm());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
    assertEquals(ValueSetConstants.DELETE_FAILURE_MESSAGE, response.getMessage());
  }

  @Test
  void deleteValueSetNullParamTest() {
    ValueSetStateChangeResponse response = valueSetStateManager.deleteValueSet(null);
    assertEquals(ValueSetConstants.EMPTY_CODE_SET_NM, response.getMessage());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
  }

  @Test
  void deleteValueSetExceptionTest() {
    when(valueSetRepository.inActivateValueSet(Mockito.anyString()))
        .thenThrow(new IllegalArgumentException(message));
    ValueSetStateChangeResponse response = valueSetStateManager.deleteValueSet("testCodeSetNm");
    assertEquals(null, response.getCodeSetNm());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
    assertEquals(message, response.getMessage());
  }

  // activate tests

  @Test
  void activateValueSetTest() {
    when(valueSetRepository.activateValueSet(Mockito.anyString())).thenReturn(1);
    ValueSetStateChangeResponse response = valueSetStateManager.activateValueSet(codeSetNm);
    assertEquals(codeSetNm, response.getCodeSetNm());
    assertEquals(HttpStatus.OK, response.getStatus());
    assertEquals(ValueSetConstants.ACTIVATE_SUCCESS_MESSAGE, response.getMessage());
  }

  @Test
  void activateValueSetUpdateFailureTest() {
    when(valueSetRepository.activateValueSet(Mockito.anyString())).thenReturn(0);
    ValueSetStateChangeResponse response = valueSetStateManager.activateValueSet(codeSetNm);
    assertEquals(codeSetNm, response.getCodeSetNm());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
    assertEquals(ValueSetConstants.ACTIVATE_FAILURE_MESSAGE, response.getMessage());
  }

  @Test
  void activateValueSetNullParamTest() {
    ValueSetStateChangeResponse response = valueSetStateManager.activateValueSet(null);
    assertEquals(ValueSetConstants.EMPTY_CODE_SET_NM, response.getMessage());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
  }

  @Test
  void activateValueSetExceptionTest() {
    when(valueSetRepository.activateValueSet(Mockito.anyString()))
        .thenThrow(new IllegalArgumentException(message));
    ValueSetStateChangeResponse response = valueSetStateManager.activateValueSet(codeSetNm);
    assertEquals(null, response.getCodeSetNm());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
    assertEquals(message, response.getMessage());
  }
}
