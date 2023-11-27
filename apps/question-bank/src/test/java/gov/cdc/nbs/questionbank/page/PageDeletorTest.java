package gov.cdc.nbs.questionbank.page;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import gov.cdc.nbs.questionbank.page.exception.PageUpdateException;
import gov.cdc.nbs.questionbank.page.response.PageDeleteResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;

@ExtendWith(MockitoExtension.class)
class PageDeletorTest {
    @Mock
    private EntityManager entityManager;

    @Mock
    private PageDraftFinder pageDraftFinder;

    @InjectMocks
    PageDeletor pageDeletor;

    @Test
    void deleteSinglePageDraft() {
        Long requestId = 1l;

        WaTemplate page = new WaTemplate();
        page.setId(requestId);
        page.setTemplateNm("some name");
        page.setFormCd("Form_cd");
        page.setTemplateType("Draft");

        when(entityManager.find(WaTemplate.class, requestId)).thenReturn(page);

        doNothing().when(entityManager).remove(page);
        PageDeleteResponse response = pageDeletor.deletePageDraft(requestId);
        assertEquals(requestId, response.templateId());
        assertEquals(PageConstants.DRAFT_DELETE_SUCCESS, response.message());

    }

    @Test
    void deletePublishedWithDraftPage() {
        Long requestId = 1l;
        WaTemplate page = new WaTemplate();
        page.setId(requestId);
        page.setFormCd("Form_cd");
        page.setTemplateType("Published With Draft");

        WaTemplate draftPage = new WaTemplate();
        draftPage.setId(2L);
        draftPage.setFormCd("Form_cd");
        draftPage.setTemplateType("Draft");


        when(entityManager.find(WaTemplate.class, requestId)).thenReturn(page);

        when(pageDraftFinder.findDraftTemplate(page)).thenReturn(draftPage.getId());

        when(entityManager.find(WaTemplate.class, draftPage.getId())).thenReturn(draftPage);

        PageDeleteResponse response = pageDeletor.deletePageDraft(requestId);
        assertEquals(requestId, response.templateId());
        assertEquals(PageConstants.DRAFT_DELETE_SUCCESS, response.message());
        assertEquals(PageConstants.PUBLISHED, page.getTemplateType());

    }

    @Test
    void deletePageDraftDraftNotFound() {
        Long requestId = 1L;
        WaTemplate page = new WaTemplate();
        page.setId(requestId);
        when(entityManager.find(WaTemplate.class, page.getId())).thenReturn(null);
        assertThrows(PageNotFoundException.class, () -> pageDeletor.deletePageDraft(requestId));
    }

    @Test
    void deletePageDraftNotFoundFromPublishedWithDraftPage() {
        Long requestId = 1L;
        WaTemplate page = new WaTemplate();
        page.setId(requestId);
        page.setTemplateType(PageConstants.PUBLISHED_WITH_DRAFT);
        when(entityManager.find(WaTemplate.class, page.getId())).thenReturn(page);

        Long draftId = 2L;

        when(pageDraftFinder.findDraftTemplate(page)).thenReturn(draftId);

        when(entityManager.find(WaTemplate.class, draftId)).thenReturn(null);

        assertThrows(PageNotFoundException.class, () -> pageDeletor.deletePageDraft(requestId));
    }

    @Test
    void deletePageDraftPageNotFound() {
        Long requestId = 1L;
        WaTemplate page = new WaTemplate();
        page.setTemplateType(PageConstants.PUBLISHED);
        page.setId(requestId);
        when(entityManager.find(WaTemplate.class, page.getId())).thenReturn(page);
        assertThrows(PageUpdateException.class, () -> pageDeletor.deletePageDraft(requestId));
    }
}
