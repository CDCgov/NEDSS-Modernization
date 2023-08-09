package gov.cdc.nbs.questionbank.page;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.questionbank.entity.PageCondMapping;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadatum;
import gov.cdc.nbs.questionbank.entity.repository.PageCondMappingRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadatumRepository;
import gov.cdc.nbs.questionbank.page.request.PageCreateRequest;
import gov.cdc.nbs.questionbank.page.response.PageCreateResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PageCreator {

	@Autowired
	private WaTemplateRepository templateRepository;

	@Autowired
	private PageCondMappingRepository pageConMappingRepository;
	
	@Autowired
	private WaUiMetadatumRepository waUiMetadatumRepository;

	public PageCreateResponse createPage(PageCreateRequest request, Long userId) {
		PageCreateResponse response = new PageCreateResponse();
		try {
			if (request.name() == null || request.name().isEmpty()) {
				response.setMessage(PageConstants.ADD_PAGE_NAME_EMPTY);
				response.setStatus(HttpStatus.BAD_REQUEST);
				return response;
			}

			if (request.conditionIds().isEmpty()) {
				response.setMessage(PageConstants.ADD_PAGE_CONDITION_EMPTY);
				response.setStatus(HttpStatus.BAD_REQUEST);
				return response;
			}

			if (request.eventType() == null || request.eventType().isEmpty()) {
				response.setMessage(PageConstants.ADD_PAGE_EVENTTYPE_EMPTY);
				response.setStatus(HttpStatus.BAD_REQUEST);
				return response;
			}

			if (request.templateId() == null || request.templateId().intValue() < 1) {
				response.setMessage(PageConstants.ADD_PAGE_TEMPLATE_EMPTY);
				response.setStatus(HttpStatus.BAD_REQUEST);
				return response;
			}

			if (request.messageMappingGuide() == null || request.messageMappingGuide().isEmpty()) {
				response.setMessage(PageConstants.ADD_PAGE_MMG_EMPTY);
				response.setStatus(HttpStatus.BAD_REQUEST);
				return response;
			}

			Optional<WaTemplate> existingPageName = templateRepository.findFirstByTemplateNm(request.name());

			if (existingPageName.isPresent()) {
				String finalMessage = String.format(PageConstants.ADD_PAGE_TEMPLATENAME_EXISTS, request.name());
				response.setMessage(finalMessage);
				response.setStatus(HttpStatus.BAD_REQUEST);
				return response;
			}

			Optional<WaTemplate> existingDataMartNm = templateRepository.findFirstByDatamartNm(request.dataMartName());
			if (existingDataMartNm.isPresent()) {
				String finalMessage = String.format(PageConstants.ADD_PAGE_DATAMART_NAME_EXISTS,
						request.dataMartName());
				response.setMessage(finalMessage);
				response.setStatus(HttpStatus.BAD_REQUEST);
				return response;
			}
			
			WaTemplate newPage = buildPage(request, (String) request.conditionIds().toArray()[0], request.eventType(),
					userId);
			WaTemplate savedPage = templateRepository.save(newPage);
			Set<PageCondMapping> mapping = savePageCondMapping(request, savedPage, userId);
			pageConMappingRepository.saveAll(mapping);
			
			Optional<WaTemplate> template = templateRepository.findById(request.templateId());
			if(template.isPresent()) {
				List<WaUiMetadatum> uiMappings = copyWaTemplateUIMetaData(template.get(), savedPage);
				waUiMetadatumRepository.saveAll(uiMappings);
			}
			
			
			response.setPageId(savedPage.getId());
			response.setPageName(savedPage.getTemplateNm());
			response.setMessage(savedPage.getTemplateNm() + PageConstants.ADD_PAGE_MESSAGE);
			response.setStatus(HttpStatus.CREATED);

			return response;

		} catch (Exception e) {
			response.setMessage(PageConstants.ADD_PAGE_FAIL + e.getMessage());
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return response;
		}

	}
	
	public  List<WaUiMetadatum> copyWaTemplateUIMetaData(WaTemplate template, WaTemplate newPage) {
		List<WaUiMetadatum> initialPageMappings = new ArrayList<>();
		List<WaUiMetadatum> pages = waUiMetadatumRepository.findAllByWaTemplateUid(template);
		for(WaUiMetadatum original : pages ) {
	    WaUiMetadatum clone = original;
	    clone.setWaTemplateUid(newPage);
	    initialPageMappings.add(clone);
		}
		return initialPageMappings;
		
	}

	public Set<PageCondMapping> savePageCondMapping(PageCreateRequest request, WaTemplate savePaged, Long userId) {
		Set<PageCondMapping> result = new HashSet<>();
		Iterator<String> map = request.conditionIds().iterator();
		while (map.hasNext()) {
			String conditionId = map.next();
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

	public WaTemplate buildPage(PageCreateRequest request, String chosenConditionId, String eventType, Long userId) {
		WaTemplate result = new WaTemplate();
		result.setTemplateType("Draft");
		result.setXmlPayload("XML Payload");
		result.setFormCd("PG_" + request.name());
		result.setConditionCd(chosenConditionId);
		result.setBusObjType(eventType);
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
