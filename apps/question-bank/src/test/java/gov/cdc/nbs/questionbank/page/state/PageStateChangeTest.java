package gov.cdc.nbs.questionbank.page.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.PageStateChanger;
import gov.cdc.nbs.questionbank.page.exception.PageUpdateException;
import gov.cdc.nbs.questionbank.page.response.PageStateResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;

class PageStateChangeTest {

    @Mock
    private WaTemplateRepository templateRepository;

    @Mock
    private WaUiMetadataRepository waUiMetadataRepository;

    @InjectMocks
    PageStateChanger pageStateChanger;

    public PageStateChangeTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void pageStateUpdateTest() {
        Long requestId = 1l;
        WaTemplate before = getTemplate(requestId);
        when(templateRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(before));
        PageStateResponse response = pageStateChanger.savePageAsDraft(requestId);
        assertEquals(requestId, response.getTemplateId());
        assertEquals(PageConstants.SAVE_DRAFT_SUCCESS, response.getMessage());
    }

    @Test
    void pageStateExceptionTest() {
        Long requestId = 1l;
        when(templateRepository.findById(Mockito.anyLong())).thenThrow(new IllegalArgumentException());
        var exception = assertThrows(PageUpdateException.class, () -> pageStateChanger.savePageAsDraft(requestId));
        assertEquals(PageConstants.SAVE_DRAFT_FAIL, exception.getMessage());
    }

    @Test
    void pageStateNotFoundTest() {
        Long requestId = 1l;
        when(templateRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        var exception = assertThrows(PageUpdateException.class, () -> pageStateChanger.savePageAsDraft(requestId));
        assertEquals(PageConstants.SAVE_DRAFT_FAIL, exception.getMessage());
    }


    @Test
    void testCreateDraftCopy() {
        WaTemplate oldPage = getTemplate(10l);
        WaTemplate newPage = pageStateChanger.createDraftCopy(oldPage);
        assertEquals(newPage.getTemplateNm(), newPage.getTemplateNm());
        assertEquals("Draft", newPage.getTemplateType());
        assertEquals(0, newPage.getPublishVersionNbr().intValue());
        assertEquals('F', newPage.getPublishIndCd().charValue());
    }

    @Test
    void testCopyWaTemplateUIMetaData() {
        WaTemplate oldPage = getTemplate(10l);
        when(waUiMetadataRepository.findAllByWaTemplateUid(Mockito.any()))
                .thenReturn(List.of(getwaUiMetaDtum(oldPage)));
        WaTemplate newPage = pageStateChanger.createDraftCopy(oldPage);
        List<WaUiMetadata> result = pageStateChanger.copyWaTemplateUIMetaData(oldPage, newPage);
        assertNotNull(result);
        assertEquals(newPage.getId(), result.get(0).getWaTemplateUid().getId());

    }

    private WaUiMetadata getwaUiMetaDtum(WaTemplate aPage) {
        WaUiMetadata record = new WaUiMetadata();
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
