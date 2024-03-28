package gov.cdc.nbs.questionbank.page.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import gov.cdc.nbs.questionbank.entity.PageCondMapping;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.PageCondMappingRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.PageCreator;
import gov.cdc.nbs.questionbank.page.PageValidator;
import gov.cdc.nbs.questionbank.page.request.PageCreateRequest;
import gov.cdc.nbs.questionbank.page.response.PageCreateResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;

class PageCreatorTest {

  @Mock
  private WaTemplateRepository templateRepository;

  @Mock
  private PageCondMappingRepository pageConMappingRepository;

  @Mock
  private WaUiMetadataRepository waUiMetadatumRepository;

  @Mock
  WaRuleMetaDataRepository waRuleMetaDataRepository;

  @Mock
  private PageValidator validator;

  @InjectMocks
  private PageCreator pageCreator;

  public PageCreatorTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreatePage() {
    Long id = 1l;
    PageCreateRequest request =
        new PageCreateRequest("INV",
            Arrays.asList("1023"),
            "TestPage",
            10l,
            "HEP_Case_Map_V1.0",
            "unit test",
            "dataMart");
    WaTemplate page = pageCreator.buildPage(request, "INV", 10l);
    page.setId(id);
    when(templateRepository.save(Mockito.any())).thenReturn(page);
    PageCreateResponse response = pageCreator.createPage(request, 1l);
    assertEquals(page.getId(), response.pageId());
    assertEquals(page.getTemplateNm(), response.pageName());
    assertEquals(page.getTemplateNm() + PageConstants.ADD_PAGE_MESSAGE, response.message());
  }


  @Test
  void testCopyWaTemplateUIMetaData() {
    WaTemplate oldPage = getTemplate(10l);
    when(waUiMetadatumRepository.findAllByWaTemplateUid(Mockito.any()))
        .thenReturn(List.of(getwaUiMetaDtum(oldPage)));
    WaTemplate newPage = getTemplate(20l);
    List<WaUiMetadata> result = pageCreator.copyWaTemplateUIMetaData(oldPage, newPage);
    assertNotNull(result);
    assertEquals(newPage.getId(), result.get(0).getWaTemplateUid().getId());

  }

  @Test
  void testCopyWaTemplateRuleMetaData() {
    WaTemplate oldPage = getTemplate(10l);
    when(waRuleMetaDataRepository.findByWaTemplateUid(Mockito.any()))
        .thenReturn(List.of(getwaRuleMetaDtum(oldPage)));
    WaTemplate newPage = getTemplate(20l);
    List<WaRuleMetadata> result = pageCreator.copyWaTemplateRuleMetaData(oldPage, newPage);
    assertNotNull(result);
    assertEquals(newPage.getId(), result.get(0).getWaTemplateUid());

  }

  @Test
  @SuppressWarnings("unchecked")
  void testSavePageCondMapping() {
    List<String> conditionIds = new ArrayList<>();
    conditionIds.add("1023");
    conditionIds.add("1024");
    conditionIds.add("1025");
    PageCreateRequest request = new PageCreateRequest(
        null,
        conditionIds,
        null,
        0l,
        null,
        null,
        null);
    Long templateId = 1l;
    WaTemplate page = getTemplate(templateId);
    ArgumentCaptor<List<PageCondMapping>> captor = ArgumentCaptor.forClass(List.class);
    when(pageConMappingRepository.saveAll(captor.capture())).thenReturn(null);
    pageCreator.createPageCondMappings(request, page, 2l);
    assertNotNull(captor.getValue());
    assertEquals(3, captor.getValue().size());

  }

  @Test
  void testBuildPage() {
    PageCreateRequest request = pageRequest();
    WaTemplate page = pageCreator.buildPage(request, "INV", 10l);
    assertEquals("Draft", page.getTemplateType());
    assertEquals("PG_" + request.name(), page.getFormCd());
    assertEquals(request.name(), page.getTemplateNm());
  }

  private PageCreateRequest pageRequest() {
    return new PageCreateRequest(
        "INV",
        Arrays.asList("1023"),
        "TestPage",
        10l,
        "HEP_Case_Map_V1.0",
        "unit test",
        "dataMart");
  }

  private WaUiMetadata getwaUiMetaDtum(WaTemplate aPage) {
    WaUiMetadata record = new WaUiMetadata();
    record.setWaTemplateUid(aPage);
    return record;
  }

  private WaRuleMetadata getwaRuleMetaDtum(WaTemplate aPage) {
    WaRuleMetadata record = new WaRuleMetadata();
    record.setWaTemplateUid(aPage.getId());
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

