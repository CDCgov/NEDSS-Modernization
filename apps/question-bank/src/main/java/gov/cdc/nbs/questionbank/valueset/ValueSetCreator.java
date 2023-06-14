package gov.cdc.nbs.questionbank.valueset;



import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.entity.srte.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.CodeSet;
import gov.cdc.nbs.questionbank.valueset.command.ValueSetCommand;
import gov.cdc.nbs.questionbank.valueset.repository.CodesetGroupMetadatumRepository;
import gov.cdc.nbs.questionbank.valueset.repository.ValueSetRepository;
import gov.cdc.nbs.questionbank.valueset.util.ValueSetConstants;
import gov.cdc.nbs.repository.CodeValueGeneralRepository;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class ValueSetCreator {

	private ValueSetRepository valueSetRepository; // Value Set Repo
	private CodeValueGeneralRepository codeValueRepository; // ValueSet Concept Repo
	private CodesetGroupMetadatumRepository codeSetGrpMetaRepository; // Value Set Grp Meta

	public CreateValueSetResponse createValueSet(ValueSetRequest request) {
		CreateValueSetResponse response = new CreateValueSetResponse();
		String codeSetName = request.getValueSetNm();
		String codeShrtDescTxt = request.getCodeSetDescTxt();

		if (checkValueSetNameExists(codeSetName)) {

			response.setMessage(ValueSetConstants.VALUE_SET_NAME_EXISTS);
			response.setStatus(HttpStatus.BAD_REQUEST);
			return response;
		}
		if (checkCodeSetGrpMetaDatEntry(codeShrtDescTxt, codeSetName)) {
			response.setMessage(ValueSetConstants.VALUE_SET_NAME_EXISTS);
			response.setStatus(HttpStatus.BAD_REQUEST);
			return response;
		}
		try {
			// Add CodeSet Group MetaData
			request.getCodeSetGroup().setId(getCodeSetGroupID());
			codeSetGrpMetaRepository.save(request.getCodeSetGroup());
			// Add ValueSet
			CodeSet valueSet = new CodeSet(asAdd(request));
			valueSet.getCodeSetGroup().setId(getCodeSetGroupID());
			CodeSet resultCodeSet = valueSetRepository.save(valueSet);
			// Add any accompanying Value Concepts
			for (ValueSetRequest.CreateCodedValue valueConcept : request.getValues()) {
				codeValueRepository.save(createValueConcept(valueConcept));

			}
			CreateValueSetResponse.ValueSetCreateShort body = new CreateValueSetResponse.ValueSetCreateShort();
			body.setId(resultCodeSet.getId());
			body.setAddTime(resultCodeSet.getAddTime());
			body.setAddUserId(resultCodeSet.getAddUserId());
			body.setValueSetNm(resultCodeSet.getValueSetNm());
			response.setBody(body);
			response.setMessage(ValueSetConstants.SUCCESS_MESSAGE);
			response.setStatus(HttpStatus.CREATED);

		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return response;

	}

	private boolean checkValueSetNameExists(String codeSetNm) {
		return (codeSetNm != null && valueSetRepository.checkValueSetName(codeSetNm) > 0) ? true : false;

	}

	private boolean checkCodeSetGrpMetaDatEntry(String codeShrtDescTxt, String codeSetNm) {
		return (codeShrtDescTxt != null && codeSetNm != null
				&& codeSetGrpMetaRepository.checkCodeSetGrpMetaDatEntry(codeShrtDescTxt, codeSetNm) > 0) ? true : false;
	}

	private long getCodeSetGroupID() {
		long maxGroupID = valueSetRepository.getCodeSetGroupCeilID();
		if (maxGroupID > 0) {
			maxGroupID = valueSetRepository.getCodeSetGroupMaxID() + 10;
		} else {
			maxGroupID = 9910;
		}
		return maxGroupID;
	}

	private ValueSetCommand.AddValueSet asAdd(final ValueSetRequest request) {
		return new ValueSetCommand.AddValueSet(request.getAssigningAuthorityCd(),
				request.getAssigningAuthorityDescTxt(), request.getCodeSetDescTxt(), request.getEffectiveFromTime(),
				request.getEffectiveToTime(), request.getIsModifiableInd(), request.getNbsUid(),
				request.getSourceVersionTxt(), request.getSourceDomainNm(), request.getStatusCd(),
				request.getStatusToTime(), request.getCodeSetGroup(), request.getAdminComments(),
				request.getValueSetNm(), request.getLdfPicklistIndCd(), request.getValueSetCode(),
				request.getValueSetTypeCd(), request.getValueSetOid(), request.getValueSetStatusCd(),
				request.getValueSetStatusTime(), request.getParentIsCd(), request.getAddTime(), request.getAddUserId());

	}

	private CodeValueGeneral createValueConcept(final ValueSetRequest.CreateCodedValue valueConcept) {
		CodeValueGeneral codeValueGen = new CodeValueGeneral();
		codeValueGen.setCodeDescTxt(valueConcept.getCodeDescTxt());
		codeValueGen.setCodeShortDescTxt(valueConcept.getCodeShortDescTxt());
		codeValueGen.setCodeSystemCd(valueConcept.getCodeSystemCd());
		codeValueGen.setCodeSystemDescTxt(valueConcept.getCodeSystemDescTxt());
		codeValueGen.setEffectiveFromTime(valueConcept.getEffectiveFromTime());
		codeValueGen.setEffectiveToTime(valueConcept.getEffectiveToTime());
		codeValueGen.setIndentLevelNbr(valueConcept.getIndentLevelNbr());
		codeValueGen.setIsModifiableInd(valueConcept.getIsModifiableInd());
		codeValueGen.setNbsUid(valueConcept.getNbsUid());
		codeValueGen.setParentIsCd(valueConcept.getParentIsCd());
		codeValueGen.setSourceConceptId(valueConcept.getSourceConceptId());
		codeValueGen.setSuperCodeSetNm(valueConcept.getSuperCodeSetNm());
		codeValueGen.setSuperCode(valueConcept.getSuperCode());
		codeValueGen.setStatusCd(valueConcept.getStatusCd());
		codeValueGen.setStatusTime(valueConcept.getStatusTime());
		codeValueGen.setConceptTypeCd(valueConcept.getConceptTypeCd());
		codeValueGen.setConceptCode(valueConcept.getConceptCode());
		codeValueGen.setConceptNm(valueConcept.getConceptNm());
		codeValueGen.setConceptPreferredNm(valueConcept.getConceptPreferredNm());
		codeValueGen.setConceptStatusCd(valueConcept.getConceptStatusCd());
		codeValueGen.setConceptStatusTime(valueConcept.getConceptStatusTime());
		codeValueGen.setCodeSystemVersionNbr(valueConcept.getCodeSystemVersionNbr());
		codeValueGen.setConceptOrderNbr(valueConcept.getConceptOrderNbr());
		codeValueGen.setAdminComments(valueConcept.getAdminComments());
		codeValueGen.setAddTime(valueConcept.getAddTime());
		codeValueGen.setAddUserId(valueConcept.getAddUserId());

		return codeValueGen;
	}

}
