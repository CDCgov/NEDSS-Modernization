package gov.cdc.nbs.questionbank.valueset;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.valueset.repository.ValueSetRepository;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetUpdateRequest;
import gov.cdc.nbs.questionbank.valueset.response.UpdatedValueSetResponse;
import gov.cdc.nbs.questionbank.valueset.response.UpdatedValueSetResponse.ValueSetUpdateShort;
import gov.cdc.nbs.questionbank.valueset.util.ValueSetConstants;

@Service
public class ValueSetUpdater {

	@Autowired
	private ValueSetRepository valueSetRepository;

	public UpdatedValueSetResponse updateValueSet(ValueSetUpdateRequest update) {
		UpdatedValueSetResponse response = new UpdatedValueSetResponse();
		if (update.codeSetName() == null) {
			response.setMessage(ValueSetConstants.EMPTY_CODE_SET_NM);
			response.setStatus(HttpStatus.BAD_REQUEST);
			return response;
		}
		try {
			Optional<Codeset> codeSet = valueSetRepository.findByCodeSetNm(update.codeSetName());
			if (codeSet.isPresent()) {
				Codeset result = codeSet.get();
				result.setValueSetNm(update.valueSetNm() != null ? update.valueSetNm() : result.getValueSetNm());
				result.setCodeSetDescTxt(
						update.codeSetDescTxt() != null ? update.codeSetDescTxt() : result.getCodeSetDescTxt());
				valueSetRepository.save(result);
				response.setMessage(ValueSetConstants.UPDATE_SUCCESS_MESSAGE);
				response.setStatus(HttpStatus.OK);
				ValueSetUpdateShort body = new ValueSetUpdateShort(update.codeSetName(), update.valueSetNm(),update.codeSetDescTxt());
				response.setBody(body);

			} else {
				response.setMessage(ValueSetConstants.CODE_SET_NOT_FOUND);
				response.setStatus(HttpStatus.BAD_REQUEST);
				return response;
			}
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

}
