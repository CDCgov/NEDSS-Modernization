package gov.cdc.nbs.questionbank.page.state;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.entity.PageCondMapping;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.entity.repository.PageCondMappingRepository;
import gov.cdc.nbs.questionbank.entity.repository.WANNDMetadataRepository;
import gov.cdc.nbs.questionbank.entity.repository.WARDBMetadataRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.PageStateChanger;
import gov.cdc.nbs.questionbank.page.exception.PageUpdateException;
import gov.cdc.nbs.questionbank.page.response.PageStateResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PageStateChangeTest {

  @Mock private WaTemplateRepository templateRepository;

  @Mock private WaUiMetadataRepository waUiMetadataRepository;

  @Mock private WANNDMetadataRepository wanndMetadataRepository;

  @Mock private WARDBMetadataRepository wARDBMetadataRepository;

  @Mock private WaRuleMetaDataRepository waRuleMetaDataRepository;

  @Mock private PageCondMappingRepository pageConMappingRepository;

  @InjectMocks PageStateChanger pageStateChanger;

  @Test
  void pageStateUpdateTest() {
    Long requestId = 1L;
    WaTemplate before = getTemplate(requestId, "TestPage", "Pblished");
    when(templateRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(before));
    when(templateRepository.save(Mockito.any())).thenReturn(before);
    PageStateResponse response = pageStateChanger.savePageAsDraft(requestId);
    assertEquals(requestId, response.getTemplateId());
    assertEquals(PageConstants.SAVE_DRAFT_SUCCESS, response.getMessage());
  }

  @Test
  void pageStateExceptionTest() {
    Long requestId = 1L;
    when(templateRepository.findById(Mockito.anyLong())).thenThrow(new IllegalArgumentException());
    var exception =
        assertThrows(PageUpdateException.class, () -> pageStateChanger.savePageAsDraft(requestId));
    assertEquals(PageConstants.SAVE_DRAFT_FAIL, exception.getMessage());
  }

  @Test
  void pageStateNotFoundTest() {
    Long requestId = 1L;
    when(templateRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    var exception =
        assertThrows(PageUpdateException.class, () -> pageStateChanger.savePageAsDraft(requestId));
    assertEquals(PageConstants.SAVE_DRAFT_FAIL, exception.getMessage());
  }

  @Test
  void testCreateDraftCopy() {
    WaTemplate oldPage = getTemplate(10L, "testName", "Published");
    WaTemplate newPage = pageStateChanger.createDraftCopy(oldPage);

    assertEquals("Draft", newPage.getTemplateType());
    assertEquals(0, newPage.getPublishVersionNbr().intValue());
    assertEquals('F', newPage.getPublishIndCd().charValue());
  }

  @Test
  void testCopyWaTemplateUIMetaData() {
    WaTemplate oldPage = getTemplate(10L, "testName", "Published");
    when(waUiMetadataRepository.findAllByWaTemplateUid(Mockito.any()))
        .thenReturn(List.of(getwaUiMetaDtum(oldPage)));
    WaTemplate newPage = pageStateChanger.createDraftCopy(oldPage);
    List<WaUiMetadata> result = pageStateChanger.copyWaTemplateUIMetaData(oldPage, newPage);
    assertNotNull(result);
    assertEquals(newPage.getId(), result.getFirst().getWaTemplateUid().getId());
  }

  @Test
  void testCopyRules() {
    WaTemplate oldPage = getTemplate(10L, "testName", "Published");

    when(waRuleMetaDataRepository.findByWaTemplateUid(Mockito.any()))
        .thenReturn(List.of(getWaRuleMetadata(oldPage)));

    List<WaRuleMetadata> result = pageStateChanger.copyRules(oldPage.getId(), 11L);
    assertNotNull(result);
  }

  private WaUiMetadata getwaUiMetaDtum(WaTemplate aPage) {
    WaUiMetadata metadata = new WaUiMetadata();
    metadata.setWaTemplateUid(aPage);
    metadata.setQuestionIdentifier("identifier");
    return metadata;
  }

  private WaRuleMetadata getWaRuleMetadata(WaTemplate aPage) {
    WaRuleMetadata metadata = new WaRuleMetadata();
    metadata.setWaTemplateUid(aPage.getId());
    return metadata;
  }

  private WaTemplate getTemplate(Long id, String templateName, String templateType) {
    PageCondMapping one = new PageCondMapping();

    WaTemplate template = new WaTemplate();
    template.setId(id);
    template.setTemplateNm(templateName);
    template.setTemplateType(templateType);
    template.setPublishVersionNbr(1);
    template.setPublishIndCd('T');

    one.setAddTime(Instant.now());
    one.setAddUserId(2L);
    one.setLastChgTime(Instant.now());
    one.setLastChgUserId(2L);
    one.setWaTemplateUid(template);

    template.setConditionMappings(Set.of(one));

    return template;
  }
}
