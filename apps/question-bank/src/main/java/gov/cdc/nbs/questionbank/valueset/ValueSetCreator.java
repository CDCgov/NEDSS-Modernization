package gov.cdc.nbs.questionbank.valueset;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.questionbank.entity.CodeSetGroupMetadatum;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralRepository;
import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.entity.CodesetId;
import gov.cdc.nbs.questionbank.valueset.command.ValueSetCommand;
import gov.cdc.nbs.questionbank.valueset.repository.CodesetGroupMetadatumRepository;
import gov.cdc.nbs.questionbank.valueset.repository.ValueSetRepository;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetRequest;
import gov.cdc.nbs.questionbank.valueset.response.CreateValueSetResponse;
import gov.cdc.nbs.questionbank.valueset.util.ValueSetConstants;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValueSetCreator {

	@Autowired
	private ValueSetRepository valueSetRepository; // Value Set Repo
	
	private CodeValueGeneralRepository codeValueRepository; // ValueSet Concept Repo
	
	@Autowired
	private CodesetGroupMetadatumRepository codeSetGrpMetaRepository; // Value Set Grp Meta

	public CreateValueSetResponse createValueSet(ValueSetRequest request, long userId) {
		CreateValueSetResponse response = new CreateValueSetResponse();
		String codeSetName = request.getValueSetNm();
		String codeShrtDescTxt = request.getCodeSetDescTxt();

		if (checkValueSetNameExists(codeSetName)) {

			response.setMessage(ValueSetConstants.VALUE_SET_NAME_EXISTS);
			response.setStatus(HttpStatus.BAD_REQUEST);
			return response;
		}
		if (checkCodeSetGrpMetaDatEntry(codeShrtDescTxt, codeSetName)) {
			response.setMessage(ValueSetConstants.CODE_SET_GRP_TEXT_NAME_EXISTS);
			response.setStatus(HttpStatus.BAD_REQUEST);
			return response;
		}
		try {
			// Add CodeSet Group MetaData
			long codeSetGroupID = getCodeSetGroupID();
			CodeSetGroupMetadatum codeGrp = new  CodeSetGroupMetadatum();
			codeGrp.setId(codeSetGroupID);
			codeGrp.setCodeSetDescTxt(request.getCodeSetDescTxt());
			codeGrp.setCodeSetNm(request.getValueSetNm());
			codeGrp.setLdfPicklistIndCd(request.getLdfPicklistIndCd());
			// Add ValueSet
			Codeset valueSet = new Codeset(asAdd(request,userId));
			CodesetId id = new CodesetId();
			id.setClassCd(ValueSetConstants.CREATE_CLASS_CD);
			id.setCodeSetNm(codeSetName);
			valueSet.setId(id);
			// save changes
			Codeset resultCodeSet = valueSetRepository.save(valueSet);
			codeSetGrpMetaRepository.save(codeGrp);
			// Add any accompanying Value Concepts
			
			if(request.getValues() != null) {
			for (ValueSetRequest.CreateCodedValue valueConcept : request.getValues()) {
				codeValueRepository.save(createValueConcept(valueConcept,userId));

			}
			}
			CreateValueSetResponse.ValueSetCreateShort body = new CreateValueSetResponse.ValueSetCreateShort(
					resultCodeSet.getId(), resultCodeSet.getAddTime(), resultCodeSet.getAddUserId(),
					resultCodeSet.getValueSetNm());

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

	public ValueSetCommand.AddValueSet asAdd(final ValueSetRequest request, long userId) {
		return new ValueSetCommand.AddValueSet(
				request.getAssigningAuthorityCd(),
				request.getAssigningAuthorityDescTxt(), 
				request.getCodeSetDescTxt(), 
				request.getEffectiveFromTime(),
				request.getEffectiveToTime(), 
				request.getIsModifiableInd(), 
				request.getNbsUid(),
				request.getSourceVersionTxt(), 
				request.getSourceDomainNm(), 
				request.getStatusCd(),
				request.getStatusToTime(), 
				request.getCodeSetGroup(), 
				request.getAdminComments(),
				request.getValueSetNm(), 
				request.getLdfPicklistIndCd(), 
				request.getValueSetCode(),
				request.getValueSetTypeCd(), 
				request.getValueSetOid(), 
				request.getValueSetStatusCd(),
				request.getValueSetStatusTime(),
				request.getParentIsCd(), 
				request.getAddTime(), 
				userId);

	}

	private CodeValueGeneral createValueConcept(final ValueSetRequest.CreateCodedValue valueConcept, long userId) {
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
		codeValueGen.setAddUserId(userId);

		return codeValueGen;
	}

}
