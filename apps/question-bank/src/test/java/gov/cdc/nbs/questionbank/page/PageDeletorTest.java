package gov.cdc.nbs.questionbank.page;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import gov.cdc.nbs.questionbank.page.exception.PageUpdateException;
import gov.cdc.nbs.questionbank.page.response.PageDeleteResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import jakarta.persistence.EntityManager;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PageDeletorTest {
  @Mock private EntityManager entityManager;

  @Mock private PageUidFinder pageUidFinder;

  @InjectMocks PageDeletor pageDeletor;

  @Test
  void deletePageDraft_withPublishedWithDraft() {
    Long requestId = 1l;

    WaTemplate page = new WaTemplate();
    page.setId(requestId);
    page.setTemplateNm("some name");
    page.setFormCd("Form_cd");
    page.setTemplateType("Draft");

    WaTemplate publishedWithDraft = new WaTemplate();
    publishedWithDraft.setId(2L);
    publishedWithDraft.setFormCd("Form_cd");
    publishedWithDraft.setTemplateType("Published With Draft");

    when(entityManager.find(WaTemplate.class, requestId)).thenReturn(page);
    when(pageUidFinder.findTemplateByType(page.getFormCd(), PageConstants.PUBLISHED_WITH_DRAFT))
        .thenReturn(2L);
    when(entityManager.find(WaTemplate.class, publishedWithDraft.getId()))
        .thenReturn(publishedWithDraft);

    PageDeleteResponse response = pageDeletor.deletePageDraft(requestId);
    assertEquals(requestId, response.templateId());
    assertEquals(PageConstants.DRAFT_DELETE_SUCCESS, response.message());
  }

  @Test
  void deletePageDraft_SinglePageDraft() {
    Long requestId = 1l;
    WaTemplate page = new WaTemplate();
    page.setId(requestId);
    page.setTemplateNm("some name");
    page.setFormCd("Form_cd");
    page.setTemplateType("Draft");

    when(entityManager.find(WaTemplate.class, requestId)).thenReturn(page);
    when(pageUidFinder.findTemplateByType(page.getFormCd(), PageConstants.PUBLISHED_WITH_DRAFT))
        .thenReturn(null);

    PageDeleteResponse response = pageDeletor.deletePageDraft(requestId);
    assertEquals(requestId, response.templateId());
    assertEquals(PageConstants.DRAFT_DELETE_SUCCESS, response.message());
  }

  @Test
  void deletePageDraft_DraftNotFound() {
    Long requestId = 1L;
    WaTemplate page = new WaTemplate();
    page.setId(requestId);
    when(entityManager.find(WaTemplate.class, page.getId())).thenReturn(null);
    Assert.assertThrows(PageNotFoundException.class, () -> pageDeletor.deletePageDraft(requestId));
  }

  @Test
  void deletePageDraft_PageNotDraft() {
    Long requestId = 1L;
    WaTemplate page = new WaTemplate();
    page.setTemplateType(PageConstants.PUBLISHED);
    page.setId(requestId);
    when(entityManager.find(WaTemplate.class, page.getId())).thenReturn(page);
    Assert.assertThrows(PageUpdateException.class, () -> pageDeletor.deletePageDraft(requestId));
  }
}
