package gov.cdc.nbs.questionbank.page;

import java.time.Instant;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.questionbank.entity.PageCondMapping;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.repository.PageCondMappingRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadatumRepository;
import gov.cdc.nbs.questionbank.page.request.PageCreateRequest;
import gov.cdc.nbs.questionbank.page.response.PageCreateResponse;
import gov.cdc.nbs.repository.ConditionCodeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PageCreator {
	
	@Autowired
	private WaTemplateRepository templateRepository;
	
	@Autowired
	private PageCondMappingRepository pageConMappingRepository;
	
	@Autowired
	private WaUiMetadatumRepository waUiMetaDatumRepository;
	
	private ConditionCodeRepository conditionCodeRepository;
	
	
	
	public PageCreateResponse createPage(PageCreateRequest request,Long userId) {
		PageCreateResponse response = new PageCreateResponse();
		try {
		// get maxID and Increase+ 1
		Long newId =  templateRepository.getMaxTemplateID() + 1l;
		WaTemplate newPage = buildPage(request,newId, (String) request.conditionIds().toArray()[0] ,request.eventType() , userId);
       // create page
		// create condition mappings
		WaTemplate savePaged = templateRepository.save(newPage);
		Set<PageCondMapping> mapping = savePageCondMapping(request,savePaged,userId);
		pageConMappingRepository.saveAll(mapping);	
		
		return response;
		
		}catch(Exception e) {
		return response;	
		}
		
	}
	
	public Set<PageCondMapping> savePageCondMapping(PageCreateRequest request, WaTemplate savePaged, Long userId) {
		Set<PageCondMapping> result = new HashSet<>();
		Iterator map = request.conditionIds().iterator();
		while (map.hasNext()) {
			String conditionId = map.next().toString();
			PageCondMapping aMapping = new PageCondMapping();
			aMapping.setWaTemplateUid(savePaged);
			aMapping.setLastChgTime(Instant.now());
			aMapping.setLastChgUserId(userId);
			aMapping.setAddTime(Instant.now());
			aMapping.setAddUserId(userId);
			aMapping.setConditionCd(conditionId);
			result.add(aMapping);
		}
		return result;
	}
	
	public WaTemplate buildPage(PageCreateRequest request, Long newId, String chosenConditionId, String EventObjType,Long userId) {
		WaTemplate result = new WaTemplate();
		result.setId(newId);
		result.setTemplateType("Draft");
		result.setXmlPayload("XML Payload");
		result.setFormCd("PG_"+ request.name());
		result.setConditionCd(chosenConditionId);
		result.setBusObjType(EventObjType);
		result.setDatamartNm(request.dataMartName());
		result.setRecordStatusCd("Active");	
		result.setRecordStatusTime(Instant.now());
		result.setLastChgTime(Instant.now());
		result.setLastChgUserId(userId);
		result.setDescTxt(request.pageDescription());		
		result.setTemplateNm(request.name());
		result.setPublishIndCd('F');
		result.setAddTime(Instant.now());
		result.setAddUserId(userId);
		result.setNndEntityIdentifier(request.messageMappingGuide());		
		
		return result;
	}

}
