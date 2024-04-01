package gov.cdc.nbs.questionbank.page;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityManager;

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

        WaTemplate publishedWithDraft = new WaTemplate();
        publishedWithDraft.setId(2L);
        publishedWithDraft.setFormCd("Form_cd");
        publishedWithDraft.setTemplateType("Published With Draft");

        when(entityManager.find(WaTemplate.class, requestId)).thenReturn(page);

        PageDeleteResponse response = pageDeletor.deletePageDraft(requestId);
        assertEquals(requestId, response.templateId());
        assertEquals(PageConstants.DRAFT_DELETE_SUCCESS, response.message());

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
    void deletePageDraftPageNotFound() {
        Long requestId = 1L;
        WaTemplate page = new WaTemplate();
        page.setTemplateType(PageConstants.PUBLISHED);
        page.setId(requestId);
        when(entityManager.find(WaTemplate.class, page.getId())).thenReturn(page);
        assertThrows(PageUpdateException.class, () -> pageDeletor.deletePageDraft(requestId));
    }

    @Test
    void deletePageDraftNotFoundFromDraftPage() {
        Long requestId = 1L;
        WaTemplate page = new WaTemplate();
        page.setId(requestId);
        page.setTemplateType(PageConstants.DRAFT);
        when(entityManager.find(WaTemplate.class, page.getId())).thenReturn(null);
        assertThrows(PageNotFoundException.class, () -> pageDeletor.deletePageDraft(requestId));
    }
}
