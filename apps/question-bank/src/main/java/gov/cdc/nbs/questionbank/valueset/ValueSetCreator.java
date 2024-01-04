package gov.cdc.nbs.questionbank.valueset;

import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import gov.cdc.nbs.questionbank.entity.CodeSetGroupMetadatum;
import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.valueset.command.ValueSetCommand;
import gov.cdc.nbs.questionbank.valueset.repository.CodesetGroupMetadatumRepository;
import gov.cdc.nbs.questionbank.valueset.repository.ValueSetRepository;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetCreateRequest;
import gov.cdc.nbs.questionbank.valueset.response.CreateValueSetResponse;
import gov.cdc.nbs.questionbank.valueset.util.ValueSetConstants;


@Service
public class ValueSetCreator {

	private final ValueSetRepository valueSetRepository;

	private final CodesetGroupMetadatumRepository codeSetGrpMetaRepository;

	public ValueSetCreator(ValueSetRepository valueSetRepository,
			CodesetGroupMetadatumRepository codeSetGrpMetaRepository) {
		this.valueSetRepository = valueSetRepository;
		this.codeSetGrpMetaRepository = codeSetGrpMetaRepository;
	}

	public CreateValueSetResponse createValueSet(ValueSetCreateRequest request, long userId) {
		CreateValueSetResponse response = new CreateValueSetResponse();
		String codeSetName = request.valueSetCode().toUpperCase();
		String codeShortDescTxt = request.valueSetName();

		if (checkValueSetNameExists(codeSetName)) {
			response.setMessage(ValueSetConstants.VALUE_SET_NAME_EXISTS);
			response.setStatus(HttpStatus.BAD_REQUEST);
			return response;
		}
		if (checkCodeSetGrpMetaDatEntry(codeShortDescTxt, codeSetName)) {
			response.setMessage(ValueSetConstants.CODE_SET_GRP_TEXT_NAME_EXISTS);
			response.setStatus(HttpStatus.BAD_REQUEST);
			return response;
		}
		try {
			long codeSetGroupID = getCodeSetGroupID();
			CodeSetGroupMetadatum codeGrp = new CodeSetGroupMetadatum();
			codeGrp.setId(codeSetGroupID);
			codeGrp.setCodeSetDescTxt(request.valueSetDescription());
			codeGrp.setCodeSetShortDescTxt(codeShortDescTxt);
			codeGrp.setCodeSetNm(codeSetName);
			codeGrp.setVadsValueSetCode(codeSetName);
			codeGrp.setLdfPicklistIndCd('Y');
			codeGrp.setPhinStdValInd('N');   // will add all this properties inside the constructor

			Codeset valueSet = new Codeset(asAdd(request, userId));

			CodeSetGroupMetadatum savedGroup = codeSetGrpMetaRepository.save(codeGrp);
			valueSet.setCodeSetGroup(savedGroup);
			Codeset resultCodeSet = valueSetRepository.save(valueSet);

			CreateValueSetResponse.ValueSetCreateShort body = new CreateValueSetResponse.ValueSetCreateShort(
					resultCodeSet.getId(), resultCodeSet.getAddTime(), resultCodeSet.getAddUserId(),
					resultCodeSet.getValueSetNm(), resultCodeSet.getCodeSetGroup().getId());

			response.setBody(body);
			response.setMessage(ValueSetConstants.SUCCESS_MESSAGE);
			response.setStatus(HttpStatus.CREATED);

		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;

	}

	public boolean checkValueSetNameExists(String codeSetNm) {
		return (codeSetNm != null && valueSetRepository.checkValueSetName(codeSetNm) > 0);

	}

	public boolean checkCodeSetGrpMetaDatEntry(String codeShrtDescTxt, String codeSetNm) {
		return (codeShrtDescTxt != null && codeSetNm != null
				&& codeSetGrpMetaRepository.checkCodeSetGrpMetaDatEntry(codeShrtDescTxt, codeSetNm) > 0);
	}

	public long getCodeSetGroupID() {
		long maxGroupID = valueSetRepository.getCodeSetGroupCeilID();
		if (maxGroupID > 0) {
			maxGroupID = codeSetGrpMetaRepository.getCodeSetGroupMaxID() + 10;
		} else {
			maxGroupID = 9910;
		}
		return maxGroupID;
	}

	public ValueSetCommand.AddValueSet asAdd(final ValueSetCreateRequest request, long userId) {
		return new ValueSetCommand.AddValueSet(
				request.valueSetType(),
				request.valueSetName(),
				request.valueSetCode(),
				request.valueSetDescription(),
				Instant.now(),
				userId);

	}
}
