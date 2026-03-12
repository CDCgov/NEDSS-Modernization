package gov.cdc.nbs.questionbank.valueset;

import gov.cdc.nbs.questionbank.valueset.repository.ValueSetRepository;
import gov.cdc.nbs.questionbank.valueset.response.ValueSetStateChangeResponse;
import gov.cdc.nbs.questionbank.valueset.util.ValueSetConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ValueSetStateManager {

  private final ValueSetRepository valueSetRepository;

  public ValueSetStateManager(final ValueSetRepository valueSetRepository) {
    this.valueSetRepository = valueSetRepository;
  }

  public ValueSetStateChangeResponse deleteValueSet(String codeSetNm) {
    ValueSetStateChangeResponse response = new ValueSetStateChangeResponse();
    if (codeSetNm == null) {
      response.setMessage(ValueSetConstants.EMPTY_CODE_SET_NM);
      response.setStatus(HttpStatus.BAD_REQUEST);
      return response;
    }

    try {

      int result = valueSetRepository.inActivateValueSet(codeSetNm);
      response.setCodeSetNm(codeSetNm);
      if (result == 1) {
        response.setMessage(ValueSetConstants.DELETE_SUCCESS_MESSAGE);
        response.setStatus(HttpStatus.OK);
        response.setStatusCd("I");
      } else {
        response.setMessage(ValueSetConstants.DELETE_FAILURE_MESSAGE);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
      }

    } catch (Exception e) {
      response.setMessage(e.getMessage());
      response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
      return response;
    }

    return response;
  }

  public ValueSetStateChangeResponse activateValueSet(String codeSetNm) {
    ValueSetStateChangeResponse response = new ValueSetStateChangeResponse();
    if (codeSetNm == null) {
      response.setMessage(ValueSetConstants.EMPTY_CODE_SET_NM);
      response.setStatus(HttpStatus.BAD_REQUEST);
      return response;
    }

    try {

      int result = valueSetRepository.activateValueSet(codeSetNm);
      response.setCodeSetNm(codeSetNm);
      if (result == 1) {
        response.setMessage(ValueSetConstants.ACTIVATE_SUCCESS_MESSAGE);
        response.setStatus(HttpStatus.OK);
        response.setStatusCd("A");
      } else {
        response.setMessage(ValueSetConstants.ACTIVATE_FAILURE_MESSAGE);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
      }

    } catch (Exception e) {
      response.setMessage(e.getMessage());
      response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
      return response;
    }

    return response;
  }
}
