package gov.cdc.nbs.questionbank.page;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.entity.PageCondMapping;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.repository.PageCondMappingRepository;
import gov.cdc.nbs.questionbank.entity.repository.WANNDMetadataRepository;
import gov.cdc.nbs.questionbank.entity.repository.WARDBMetadataRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.exception.PageUpdateException;
import gov.cdc.nbs.questionbank.page.response.PageStateResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;

@ExtendWith(MockitoExtension.class)
class PageDeletorTest {

    @Mock
    private WaTemplateRepository templateRepository;

    @Mock
    private WaUiMetadataRepository waUiMetadataRepository;

    @Mock
    private WANNDMetadataRepository wanndMetadataRepository;

    @Mock
    private WARDBMetadataRepository wARDBMetadataRepository;

    @Mock
    private WaRuleMetaDataRepository waRuleMetaDataRepository;

    @Mock
    private PageCondMappingRepository pageConMappingRepository;

    @InjectMocks
    PageDeletor pageDeletor;

    @Test
    void deleteSinglePageDraft() {
        Long requestId = 1l;
        // WaTemplate page = getTemplate(requestId, "DraftPage", "Draft");

        WaTemplate page = new WaTemplate();
        page.setId(requestId);
        page.setFormCd("Form_cd");
        page.setTemplateType("Draft");

        when(templateRepository.findById(requestId)).thenReturn(Optional.of(page));

        when(templateRepository.findByFormCdAndTemplateType(page.getFormCd(), PageConstants.PUBLISHED_WITH_DRAFT))
                .thenReturn(null);

        doNothing().when(wanndMetadataRepository).deleteByWaTemplateUid(page);

        doNothing().when(wARDBMetadataRepository).deleteByWaTemplateUid(page);

        doNothing().when(waUiMetadataRepository).deleteAllByWaTemplateUid(page);

        doNothing().when(waRuleMetaDataRepository).deleteByWaTemplateUid(page.getId());

        doNothing().when(templateRepository).deleteById(page.getId());

        PageStateResponse response = pageDeletor.deletePageDraft(requestId);
        assertEquals(requestId, response.getTemplateId());
        assertEquals(page.getTemplateNm() + " " + PageConstants.DRAFT_DELETE_SUCCESS, response.getMessage());

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


        when(templateRepository.findById(requestId)).thenReturn(Optional.of(page));

        when(templateRepository.findByFormCdAndTemplateType(page.getFormCd(), PageConstants.DRAFT))
                .thenReturn(draftPage);

        doNothing().when(wanndMetadataRepository).deleteByWaTemplateUid(draftPage);

        doNothing().when(wARDBMetadataRepository).deleteByWaTemplateUid(draftPage);

        doNothing().when(waUiMetadataRepository).deleteAllByWaTemplateUid(draftPage);

        doNothing().when(waRuleMetaDataRepository).deleteByWaTemplateUid(draftPage.getId());

        doNothing().when(templateRepository).deleteById(draftPage.getId());

        PageStateResponse response = pageDeletor.deletePageDraft(requestId);
        assertEquals(requestId, response.getTemplateId());
        assertEquals(page.getTemplateNm() + " " + PageConstants.DRAFT_DELETE_SUCCESS, response.getMessage());
        assertEquals(PageConstants.PUBLISHED, page.getTemplateType());

    }

    @Test
    void deleteDraftPageAndChangePublishedWithDraftPage() {
        Long requestId = 1l;
        WaTemplate page = new WaTemplate();
        page.setId(2L);
        page.setFormCd("Form_cd");
        page.setTemplateType("Published With Draft");

        WaTemplate draftPage = new WaTemplate();
        draftPage.setId(requestId);
        draftPage.setFormCd("Form_cd");
        draftPage.setTemplateType("Draft");


        when(templateRepository.findById(requestId)).thenReturn(Optional.of(draftPage));

        when(templateRepository.findByFormCdAndTemplateType(draftPage.getFormCd(), PageConstants.PUBLISHED_WITH_DRAFT))
                .thenReturn(page);

        doNothing().when(wanndMetadataRepository).deleteByWaTemplateUid(draftPage);

        doNothing().when(wARDBMetadataRepository).deleteByWaTemplateUid(draftPage);

        doNothing().when(waUiMetadataRepository).deleteAllByWaTemplateUid(draftPage);

        doNothing().when(waRuleMetaDataRepository).deleteByWaTemplateUid(draftPage.getId());

        doNothing().when(templateRepository).deleteById(draftPage.getId());

        PageStateResponse response = pageDeletor.deletePageDraft(requestId);
        assertEquals(requestId, response.getTemplateId());
        assertEquals(page.getTemplateNm() + " " + PageConstants.DRAFT_DELETE_SUCCESS, response.getMessage());
        assertEquals(PageConstants.PUBLISHED, page.getTemplateType());

    }

    @Test
    void deletePageDraftDraftNotFound() {
        Long requestId = 1l;
        WaTemplate NoDraft = getTemplate(requestId, "NoDraftPage", "Pblished");
        when(templateRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(NoDraft));
        var exception = assertThrows(PageUpdateException.class, () -> pageDeletor.deletePageDraft(requestId));
        assertEquals(PageConstants.DRAFT_NOT_FOUND, exception.getMessage());
    }

    @Test
    void deletePageDraftException() {
        Long requestId = 1l;
        WaTemplate page = getTemplate(requestId, "NoDraftPage", "Published With Draft");
        when(templateRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(page));
        var exception = assertThrows(PageUpdateException.class, () -> pageDeletor.deletePageDraft(requestId));
        assertEquals(PageConstants.DELETE_DRAFT_FAIL, exception.getMessage());
    }

    @Test
    void deletePageDraftPageNotFound() {
        Long requestId = 1l;
        when(templateRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        var exception = assertThrows(PageUpdateException.class, () -> pageDeletor.deletePageDraft(requestId));
        assertEquals(PageConstants.PAGE_NOT_FOUND, exception.getMessage());
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
        one.setAddUserId(2l);
        one.setLastChgTime(Instant.now());
        one.setLastChgUserId(2l);
        one.setWaTemplateUid(template);

        template.setConditionMappings(Set.of(one));

        return template;
    }
}
