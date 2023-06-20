package gov.cdc.nbs.questionbank.valueset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.questionbank.valueset.repository.ValueSetRepository;
import gov.cdc.nbs.questionbank.valueset.response.DeleteValueSetResponse;
import gov.cdc.nbs.questionbank.valueset.util.ValueSetConstants;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValueSetDeletor {

	@Autowired
	private ValueSetRepository valueSetRepository;

	public DeleteValueSetResponse deleteValueSet(String codeSetNm) {
		DeleteValueSetResponse response = new DeleteValueSetResponse();
		if (codeSetNm == null) {
			response.setMessage(ValueSetConstants.EMPTY_CODE_SET_NM);
			response.setStatus(HttpStatus.BAD_REQUEST);
			return response;
		}

		try {

			int result = valueSetRepository.inActivateValueSet(codeSetNm);
			response.setCodeSetNm(codeSetNm);
			if (result == 1) {
				response.setMessage(ValueSetConstants.SUCCESS_MESSAGE);
				response.setStatus(HttpStatus.OK);
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

}
