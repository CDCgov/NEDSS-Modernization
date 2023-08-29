package gov.cdc.nbs.questionbank.page.read;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import gov.cdc.nbs.questionbank.page.PageFinder;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import gov.cdc.nbs.questionbank.page.response.PageDetailResponse.PagedDetail;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;

 class PageFinderTest {

	@Mock
	private WaTemplateRepository templateRepository;

	@Mock
	private WaUiMetadataRepository waUiMetadatumRepository;

	@Mock
	private WaRuleMetaDataRepository waRuleMetaDataRepository;

	private Long pageId = 1234l;

	@InjectMocks
	private PageFinder PageFinder;

	public PageFinderTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testgetPageDetails() {
		WaTemplate page = getTemplate(pageId);
		WaUiMetadata tab = getwaUiMetaDtum(page, PageConstants.TAB_COMPONENT, 2);
		WaUiMetadata sectionOne = getwaUiMetaDtum(page, PageConstants.SECTION_COMPONENT, 3);
		WaUiMetadata sectionTwo = getwaUiMetaDtum(page, PageConstants.SECTION_COMPONENT, 5);
		List<WaUiMetadata> sections = new ArrayList<>();
		sections.add(sectionOne);
		sections.add(sectionTwo);

		WaUiMetadata subSectionOne = getwaUiMetaDtum(page, PageConstants.SUB_SECTION_COMPONENT, 4);
		WaUiMetadata subSectionTwo = getwaUiMetaDtum(page, PageConstants.SUB_SECTION_COMPONENT, 6);
		List<WaUiMetadata> subSections = new ArrayList<>();
		subSections.add(subSectionOne);
		subSections.add(subSectionTwo);

		WaUiMetadata questionOne = getwaUiMetaDtum(page, PageConstants.SPE_QUESTION_COMPONENT, 5);
		WaUiMetadata questionTwo = getwaUiMetaDtum(page, PageConstants.SPE_QUESTION_COMPONENT, 6);
		List<WaUiMetadata> questions = new ArrayList<>();
		questions.add(questionOne);
		questions.add(questionTwo);

		when(waUiMetadatumRepository.findOrderedChildComponents(pageId, PageConstants.SECTION_COMPONENT,
				tab.getOrderNbr(),tab.getOrderNbr() +1)).thenReturn(sections);

		when(waUiMetadatumRepository.findOrderedChildComponents(pageId, PageConstants.SUB_SECTION_COMPONENT,
				sectionOne.getOrderNbr(), sectionTwo.getOrderNbr())).thenReturn(subSections);

		when(waUiMetadatumRepository.findOrderedChildComponents(pageId, PageConstants.GEN_QUESTION_COMPONENT,
				subSectionOne.getOrderNbr(),subSectionTwo.getOrderNbr())).thenReturn(questions);

		when(waUiMetadatumRepository.findOrderedTabsForPage(Mockito.anyLong(), Mockito.anyLong()))
				.thenReturn(List.of(tab));
		when(templateRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(page));
		
		when(waRuleMetaDataRepository.findByWaTemplateUid(Mockito.anyLong())).thenReturn(List.of(rawRule()));
		
		PagedDetail response = PageFinder.getPageDetails(pageId);
		assertNotNull(response);
		assertNotNull(response.pageTabs());
		assertNotNull(response.pageTabs().get(0).tabSections());
		assertNotNull(response.pageTabs().get(0).tabSections().get(0).sectionSubSections());
		assertNotNull(response.pageTabs().get(0).tabSections().get(0).sectionSubSections().get(0).pageQuestions());
		assertNotNull(response.pageRules());
		

	}

	@Test
	void testGetPageDetailsNotFound() {
		when( templateRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		var exception = assertThrows(PageNotFoundException.class, () -> PageFinder.getPageDetails(pageId));
		assertEquals(PageConstants.PAGE_NOT_EXISTS + pageId, exception.getMessage());

	}

	@Test
	void testGetChildComponents() {

		WaTemplate page = getTemplate(pageId);
		Integer orderNumber = randomOrderNumber();
		Integer max = orderNumber + 10;
		Integer resultOrderNumber = rangedRandomOrderNumber(orderNumber + 1, max);
		WaUiMetadata aSubSection = getwaUiMetaDtum(page, PageConstants.SUB_SECTION_COMPONENT, resultOrderNumber);

		when(waUiMetadatumRepository.findOrderedChildComponents(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt(),
				Mockito.anyInt())).thenReturn(List.of(aSubSection));

		List<WaUiMetadata> subSections = PageFinder.getChildComponents(page, PageConstants.SECTION_COMPONENT,
				orderNumber, max);
		assertNotNull(subSections);
		WaUiMetadata subSection = subSections.get(0);
		assertTrue(subSection.getOrderNbr() > orderNumber);
		assertTrue(subSection.getOrderNbr() < max);
	}

	@Test
	void testMergeOrderedQuetionLists() {
		List<WaUiMetadata> one  = new ArrayList<>();
		List<WaUiMetadata> two  = new ArrayList<>();
		WaUiMetadata questionOne = getwaUiMetaDtum(null, PageConstants.GEN_QUESTION_COMPONENT, 5);
		WaUiMetadata questionTwo = getwaUiMetaDtum(null, PageConstants.SPE_QUESTION_COMPONENT, 3);
		
		one.add(questionOne);
		two.add(questionTwo);
		
		List<WaUiMetadata> merged = PageFinder.mergeOrderedQuetionLists(one , two);
		assertNotNull(merged);
		assertEquals(2, merged.size());
		Integer min = merged.get(0).getOrderNbr();
		Integer max = merged.get(1).getOrderNbr();
		assertTrue(min.compareTo(max) < 0);
		assertEquals(3, min.intValue());
		assertEquals(5, max.intValue());
	}

	@Test
	void testGetPageRules() {
		when(waRuleMetaDataRepository.findByWaTemplateUid(Mockito.anyLong())).thenReturn(List.of(rawRule()));
		List<PagedDetail.PageRule> rules = PageFinder.getPageRules(pageId);
		assertNotNull(rules);
		PagedDetail.PageRule  rule = rules.get(0);
		assertEquals(99L, rule.id());
		assertEquals(pageId.longValue(), rule.pageId());
		assertEquals("Hide", rule.function());
		assertEquals(">=", rule.logic());
		assertEquals("Admission Date", rule.values());
		assertEquals("INV146", rule.targetField());
		assertEquals("INV145", rule.sourceField());

	}

	private Integer rangedRandomOrderNumber(Integer min, Integer max) {
		Random order = new Random();
		int rangedOrder = order.nextInt(max - min) + min;
		return rangedOrder;
	}

	private Integer randomOrderNumber() {
		Random order = new Random();
		return order.nextInt();
	}

	private WaTemplate getTemplate(Long id) {
		WaTemplate template = new WaTemplate();
		template.setId(id);
		template.setTemplateType("Published");
		template.setPublishVersionNbr(1);
		template.setPublishIndCd('T');
		return template;
	}

	private WaUiMetadata getwaUiMetaDtum(WaTemplate aPage, Long nbsUiComponentUid, Integer orderNumber) {
		WaUiMetadata record = new WaUiMetadata();
		record.setId(3l);
		record.setWaTemplateUid(aPage);
		record.setNbsUiComponentUid(nbsUiComponentUid);
		record.setOrderNbr(orderNumber);
		record.setVersionCtrlNbr(0);		
		return record;
	}

	private WaRuleMetadata rawRule() {
		WaRuleMetadata ruleMetadata = new WaRuleMetadata();
		ruleMetadata.setId(99L);
		ruleMetadata.setRuleCd("Hide");
		ruleMetadata.setRuleExpression("testRuleExpression");
		ruleMetadata.setErrormsgText("testErrorMsg");
		ruleMetadata.setLogic(">=");
		ruleMetadata.setSourceValues("Admission Date");
		ruleMetadata.setTargetType("Question");
		ruleMetadata.setSourceQuestionIdentifier("INV145");
		ruleMetadata.setTargetQuestionIdentifier("INV146");

		return ruleMetadata;
	}

}
