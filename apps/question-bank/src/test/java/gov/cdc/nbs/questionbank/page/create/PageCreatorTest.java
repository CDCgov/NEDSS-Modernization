package gov.cdc.nbs.questionbank.page.create;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import gov.cdc.nbs.questionbank.entity.PageCondMapping;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadatum;
import gov.cdc.nbs.questionbank.entity.repository.PageCondMappingRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadatumRepository;
import gov.cdc.nbs.questionbank.page.PageCreator;
import gov.cdc.nbs.questionbank.page.request.PageCreateRequest;
import gov.cdc.nbs.questionbank.page.response.PageCreateResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;

 class PageCreatorTest {

	@Mock
	private WaTemplateRepository templateRepository;

	@Mock
	private PageCondMappingRepository pageConMappingRepository;
	
	@Mock
	private WaUiMetadatumRepository waUiMetadatumRepository;

	@InjectMocks
	private PageCreator pageCreator;

	public PageCreatorTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreatePage() {
		Long id = 1l;
		PageCreateRequest request = new PageCreateRequest("INV", Set.of("1023"), "TestPage", 10l, "HEP_Case_Map_V1.0",
				"unit test", "dataMart");
		WaTemplate page = pageCreator.buildPage(request, "1023", "INV", 10l);
		page.setId(id);
		when(templateRepository.save(Mockito.any())).thenReturn(page);
		PageCreateResponse response = pageCreator.createPage(request, 1l);
		assertEquals(page.getId(), response.getPageId());
		assertEquals(page.getTemplateNm(), response.getPageName());
		assertEquals(HttpStatus.CREATED, response.getStatus());
		assertEquals(page.getTemplateNm() + PageConstants.ADD_PAGE_MESSAGE, response.getMessage());

	}
	
	@Test
	void testCreatePageTemplateNameExists() {
		 WaTemplate existing = getTemplate(10l);
		 String templateName= "TestPageExist";
		 PageCreateRequest request = new PageCreateRequest("INV", Set.of("1023"), templateName, 10l, "HEP_Case_Map_V1.0",
					"unit test", "dataMart");
		 
		 existing.setTemplateNm(templateName);
		 when(templateRepository.findFirstByTemplateNm(Mockito.any())).thenReturn(Optional.of(existing));
		 
		
		String finalMessage = String.format(PageConstants.ADD_PAGE_TEMPLATENAME_EXISTS, templateName);
		PageCreateResponse response = pageCreator.createPage(request, 1l);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
		assertEquals(finalMessage, response.getMessage());
	}
	
	
	@Test
	void testCreatePageDataMartNameExist() {
		 WaTemplate existing = getTemplate(10l);
		 String dataMartNm="dataMartExist";
		 PageCreateRequest request = new PageCreateRequest("INV", Set.of("1023"), "TestPage", 10l, "HEP_Case_Map_V1.0",
					"unit test", dataMartNm);
		 
		 String finalMessage = String.format(PageConstants.ADD_PAGE_DATAMART_NAME_EXISTS,
				 dataMartNm);
		 existing.setDatamartNm(dataMartNm);
		 when(templateRepository.findFirstByDatamartNm(Mockito.any())).thenReturn(Optional.of(existing));
		 
		
		PageCreateResponse response = pageCreator.createPage(request, 1l);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
		assertEquals(finalMessage, response.getMessage());
	}
	
	@Test
	void testCreatePageNOName() {
		PageCreateRequest request = new PageCreateRequest(null, Set.of(), null, 0l, "HEP_Case_Map_V1.0",
				"unit test", "dataMart");
		
		PageCreateResponse response = pageCreator.createPage(request, 1l);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
		assertEquals(PageConstants.ADD_PAGE_NAME_EMPTY, response.getMessage());
	}
	
	@Test
	void testCreatePageNOCondition() {
		PageCreateRequest request = new PageCreateRequest(null, Set.of(), "TestPage", 0l, "HEP_Case_Map_V1.0",
				"unit test", "dataMart");
		
		PageCreateResponse response = pageCreator.createPage(request, 1l);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
		assertEquals(PageConstants.ADD_PAGE_CONDITION_EMPTY, response.getMessage());
	}
	
	@Test
	void testCreatePageNOEventType() {
		PageCreateRequest request = new PageCreateRequest(null, Set.of("1023"), "TestPage", 0l, "HEP_Case_Map_V1.0",
				"unit test", "dataMart");
		
		PageCreateResponse response = pageCreator.createPage(request, 1l);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
		assertEquals(PageConstants.ADD_PAGE_EVENTTYPE_EMPTY, response.getMessage());
	}
	
	@Test
	void testCreatePageNOTemplate() {
		PageCreateRequest request = new PageCreateRequest("INV", Set.of("1023"), "TestPage", 0l, "HEP_Case_Map_V1.0",
				"unit test", "dataMart");
		
		PageCreateResponse response = pageCreator.createPage(request, 1l);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
		assertEquals(PageConstants.ADD_PAGE_TEMPLATE_EMPTY, response.getMessage());
	}
	
	@Test
	void testCreatePageNOMMG() {
		PageCreateRequest request = new PageCreateRequest("INV", Set.of("1023"), "TestPage", 10l, null,
				"unit test", "dataMart");
		
		PageCreateResponse response = pageCreator.createPage(request, 1l);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
		assertEquals(PageConstants.ADD_PAGE_MMG_EMPTY, response.getMessage());

	}

	@Test
	void testCreatePageException() {
		final String message = "Could not find page invalid id provided";
		when(templateRepository.save(Mockito.any())).thenThrow(new IllegalArgumentException(message));
		PageCreateRequest request = new PageCreateRequest("INV", Set.of("1023"), "TestPage", 10l, "HEP_Case_Map_V1.0",
				"unit test", "dataMart");
		PageCreateResponse response = pageCreator.createPage(request, 1l);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
		assertEquals(PageConstants.ADD_PAGE_FAIL + message, response.getMessage());

	}
	
	@Test
	void testCopyWaTemplateUIMetaData() {
		WaTemplate oldPage = getTemplate(10l);
		when(waUiMetadatumRepository.findAllByWaTemplateUid(Mockito.any()))
				.thenReturn(List.of(getwaUiMetaDtum(oldPage)));
		WaTemplate newPage = getTemplate(20l);
		List<WaUiMetadatum> result = pageCreator.copyWaTemplateUIMetaData(oldPage, newPage);
		assertNotNull(result);
		assertEquals(newPage.getId(), result.get(0).getWaTemplateUid().getId());

	}

	@Test
	void testSavePageCondMapping() {
		Set<String> conditionIds = new HashSet<String>();
		conditionIds.add("1023");
		conditionIds.add("1024");
		conditionIds.add("1025");
		PageCreateRequest request = new PageCreateRequest(null, conditionIds, null, 0l, null, null, null);
		Long templateId = 1l;
		WaTemplate page = getTemplate(templateId);
		Set<PageCondMapping> result = pageCreator.savePageCondMapping(request, page, 2l);
		assertNotNull(result);
		assertEquals(3, result.size());

	}

	@Test
	void testBuildPage() {
		PageCreateRequest request = new PageCreateRequest("INV", Set.of("1023"), "TestPage", 10l, "HEP_Case_Map_V1.0",
				"unit test", "dataMart");
		WaTemplate page = pageCreator.buildPage(request, "1023", "INV", 10l);
		assertEquals("Draft", page.getTemplateType());
		assertEquals("PG_" + request.name(), page.getFormCd());
		assertEquals(request.name(), page.getTemplateNm());
	}
	
	private WaUiMetadatum getwaUiMetaDtum(WaTemplate aPage) {
		WaUiMetadatum record = new WaUiMetadatum();
		record.setWaTemplateUid(aPage);
		return record;
	}


	private WaTemplate getTemplate(Long id) {
		WaTemplate template = new WaTemplate();
		template.setId(id);
		template.setTemplateType("Published");
		template.setPublishVersionNbr(1);
		template.setPublishIndCd('T');
		return template;
	}
}
