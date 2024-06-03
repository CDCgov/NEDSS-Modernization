package gov.cdc.nbs.questionbank.page.create;

import gov.cdc.nbs.questionbank.entity.PageCondMapping;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.entity.repository.PageCondMappingRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.PageCreator;
import gov.cdc.nbs.questionbank.page.PageValidator;
import gov.cdc.nbs.questionbank.page.request.PageCreateRequest;
import gov.cdc.nbs.questionbank.page.response.PageCreateResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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

  @Test
  void testCreatePage() {
    Long id = 1L;
    PageCreateRequest request =
        new PageCreateRequest("INV",
            List.of("1023"),
            "TestPage",
            10L,
            "HEP_Case_Map_V1.0",
            "unit test",
            "dataMart");
    WaTemplate page = pageCreator.buildPage(request, "INV", 10L);
    page.setId(id);
    when(templateRepository.save(Mockito.any())).thenReturn(page);
    PageCreateResponse response = pageCreator.createPage(request, 1L);
    assertEquals(page.getId(), response.pageId());
    assertEquals(page.getTemplateNm(), response.pageName());
    assertEquals(page.getTemplateNm() + PageConstants.ADD_PAGE_MESSAGE, response.message());
  }


  @Test
  void testCopyWaTemplateUIMetaData() {
    WaTemplate oldPage = getTemplate(10L);
    when(waUiMetadatumRepository.findAllByWaTemplateUid(Mockito.any()))
        .thenReturn(List.of(getwaUiMetaDatum(oldPage)));
    WaTemplate newPage = getTemplate(20L);
    List<WaUiMetadata> result = pageCreator.copyWaTemplateUIMetaData(oldPage, newPage);
    assertNotNull(result);
    assertEquals(newPage.getId(), result.getFirst().getWaTemplateUid().getId());

  }

  @Test
  void testCopyWaTemplateRuleMetaData() {
    WaTemplate oldPage = getTemplate(10L);
    when(waRuleMetaDataRepository.findByWaTemplateUid(Mockito.any()))
        .thenReturn(List.of(getwaRuleMetaDtum(oldPage)));
    WaTemplate newPage = getTemplate(20L);
    List<WaRuleMetadata> result = pageCreator.copyWaTemplateRuleMetaData(oldPage, newPage);
    assertNotNull(result);
    assertEquals(newPage.getId(), result.getFirst().getWaTemplateUid());

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
        0L,
        null,
        null,
        null);
    Long templateId = 1L;
    WaTemplate page = getTemplate(templateId);
    ArgumentCaptor<List<PageCondMapping>> captor = ArgumentCaptor.forClass(List.class);
    when(pageConMappingRepository.saveAll(captor.capture())).thenReturn(null);
    pageCreator.createPageCondMappings(request, page, 2L);
    assertNotNull(captor.getValue());
    assertEquals(3, captor.getValue().size());

  }

  @Test
  void testBuildPage() {
    PageCreateRequest request = pageRequest();
    WaTemplate page = pageCreator.buildPage(request, "INV", 10L);
    assertEquals("Draft", page.getTemplateType());
    assertEquals("PG_" + request.name(), page.getFormCd());
    assertEquals(request.name(), page.getTemplateNm());
  }

  private PageCreateRequest pageRequest() {
    return new PageCreateRequest(
        "INV",
        List.of("1023"),
        "TestPage",
        10L,
        "HEP_Case_Map_V1.0",
        "unit test",
        "dataMart");
  }

  private WaUiMetadata getwaUiMetaDatum(WaTemplate aPage) {
    WaUiMetadata metadata = new WaUiMetadata();
    metadata.setWaTemplateUid(aPage);
    return metadata;
  }

  private WaRuleMetadata getwaRuleMetaDtum(WaTemplate aPage) {
    WaRuleMetadata metadata = new WaRuleMetadata();
    metadata.setWaTemplateUid(aPage.getId());
    return metadata;
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

