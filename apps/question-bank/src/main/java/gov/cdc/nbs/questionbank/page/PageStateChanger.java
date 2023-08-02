package gov.cdc.nbs.questionbank.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadatum;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadatumRepository;
import gov.cdc.nbs.questionbank.page.response.PageStateResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PageStateChanger {

	@Autowired
	private WaTemplateRepository templateRepository;
	
	@Autowired
	private WaUiMetadatumRepository waUiMetadatumRepository;

	public PageStateResponse savePageAsDraft(Long id) {
		PageStateResponse response = new PageStateResponse();
		try {
			Optional<WaTemplate> result = templateRepository.findById(id);
			if (result.isPresent()) {
				WaTemplate page = result.get();
				WaTemplate draftPage = createDraftCopy(page);
				page.setTemplateType("Published With Draft");
				templateRepository.save(page);
				templateRepository.save(draftPage);
				List<WaUiMetadatum> draftMappings = copyWaTemplateUIMetaData(page, draftPage);
				waUiMetadatumRepository.saveAll(draftMappings);
				
				response.setMessage(PageConstants.SAVE_DRAFT_SUCCESS);
				response.setStatus(HttpStatus.OK);
				response.setTemplateId(page.getId());

			} else {
				response.setMessage(PageConstants.SAVE_DRAFT_FAIL);
				response.setStatus(HttpStatus.NOT_FOUND);
				response.setTemplateId(id);
			}
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setTemplateId(id);

		}
		return response;
	}
	
	public  List<WaUiMetadatum> copyWaTemplateUIMetaData(WaTemplate page, WaTemplate clonePage ) {
		List<WaUiMetadatum> draftMappings = new ArrayList<WaUiMetadatum>();
		List<WaUiMetadatum> pages = waUiMetadatumRepository.findAllByWaTemplateUid(page);
		for(WaUiMetadatum original : pages ) {
	    WaUiMetadatum clone = original;
	    clone.setWaTemplateUid(clonePage);
	    draftMappings.add(clone);
		}
		return draftMappings;
		
	}
	
	public WaTemplate createDraftCopy(WaTemplate oldPage) {
		if (oldPage.getTemplateType().equals("Draft")) {
			return oldPage;
		}
		Long id = templateRepository.getMaxTemplateID();
		WaTemplate draftCopy = new WaTemplate();
		draftCopy.setId(id);
		draftCopy.setTemplateNm(oldPage.getTemplateNm());
		draftCopy.setTemplateType("Draft");
		draftCopy.setPublishVersionNbr(0);
		draftCopy.setPublishIndCd('F');
		draftCopy.setAddTime(oldPage.getAddTime());
		draftCopy.setAddUserId(oldPage.getAddUserId());
		draftCopy.setBusObjType(oldPage.getBusObjType());
		draftCopy.setConditionCd(oldPage.getConditionCd());
		draftCopy.setConditionMappings(oldPage.getConditionMappings());
		draftCopy.setDatamartNm(oldPage.getDatamartNm());
		draftCopy.setDescTxt(oldPage.getDescTxt());
		draftCopy.setFormCd(oldPage.getFormCd());
		draftCopy.setLastChgTime(oldPage.getLastChgTime());
		draftCopy.setLastChgUserId(oldPage.getLastChgUserId());
		draftCopy.setLocalId(oldPage.getLocalId());
		draftCopy.setNndEntityIdentifier(oldPage.getNndEntityIdentifier());
		draftCopy.setParentTemplateUid(oldPage.getParentTemplateUid());
		draftCopy.setRecordStatusCd(oldPage.getRecordStatusCd());
		draftCopy.setRecordStatusTime(oldPage.getRecordStatusTime());
		draftCopy.setSourceNm(oldPage.getSourceNm());
		draftCopy.setVersionNote(oldPage.getVersionNote());
		draftCopy.setXmlPayload(oldPage.getXmlPayload());
		

		return draftCopy;

	}
	

}
