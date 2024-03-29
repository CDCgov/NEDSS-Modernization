package gov.cdc.nbs.questionbank.page.state;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import gov.cdc.nbs.questionbank.entity.*;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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

class PageStateChangeTest {

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
    PageStateChanger pageStateChanger;


    public PageStateChangeTest() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void pageStateUpdateTest() {
        Long requestId = 1l;
        WaTemplate before = getTemplate(requestId, "TestPage", "Pblished");
        when(templateRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(before));
        when(templateRepository.save(Mockito.any())).thenReturn(before);
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
        WaTemplate oldPage = getTemplate(10l, "testName", "Published");
        WaTemplate newPage = pageStateChanger.createDraftCopy(oldPage);
        assertEquals(newPage.getTemplateNm(), newPage.getTemplateNm());
        assertEquals("Draft", newPage.getTemplateType());
        assertEquals(0, newPage.getPublishVersionNbr().intValue());
        assertEquals('F', newPage.getPublishIndCd().charValue());
    }

    @Test
    void testCopyWaTemplateUIMetaData() {
        WaTemplate oldPage = getTemplate(10l, "testName", "Published");
        when(waUiMetadataRepository.findAllByWaTemplateUid(Mockito.any()))
            .thenReturn(List.of(getwaUiMetaDtum(oldPage)));
        WaTemplate newPage = pageStateChanger.createDraftCopy(oldPage);
        List<WaUiMetadata> result = pageStateChanger.copyWaTemplateUIMetaData(oldPage, newPage);
        assertNotNull(result);
        assertEquals(newPage.getId(), result.get(0).getWaTemplateUid().getId());

    }

    @Test
    void testCopyWaTemplateUIMetaDataRdb() {
        WaTemplate oldPage = getTemplate(10l, "testName", "Published");

        when(wARDBMetadataRepository.findAllByWaTemplateUid(Mockito.any()))
            .thenReturn(List.of(getWaRdbMetadata(oldPage)));

        when(waUiMetadataRepository.findAllByWaTemplateUid(Mockito.any()))
            .thenReturn(List.of(getwaUiMetaDtum(oldPage)));

        WaTemplate newPage = pageStateChanger.createDraftCopy(oldPage);
        List<WaRdbMetadata> result = pageStateChanger.copyWaTemplateUIMetaDataRdb(oldPage, newPage);
        assertNotNull(result);
        assertEquals(newPage.getId(), result.get(0).getWaTemplateUid().getId());
    }

    @Test
    void testCopyNndMetaData() {
        WaTemplate oldPage = getTemplate(10l, "testName", "Published");

        when(wanndMetadataRepository.findAllByWaTemplateUid(Mockito.any()))
            .thenReturn(List.of(getWaNndMetadatum(oldPage)));
        WaTemplate newPage = pageStateChanger.createDraftCopy(oldPage);
        List<WaNndMetadatum> result = pageStateChanger.copyNndMetaData(oldPage, newPage);
        assertNotNull(result);
        assertEquals(newPage.getId(), result.get(0).getWaTemplateUid().getId());
    }

    @Test
    void testCopyRules() {
        WaTemplate oldPage = getTemplate(10l, "testName", "Published");

        when(waRuleMetaDataRepository.findByWaTemplateUid(Mockito.any()))
            .thenReturn(List.of(getWaRuleMetadata(oldPage)));

        WaTemplate newPage = pageStateChanger.createDraftCopy(oldPage);
        List<WaRuleMetadata> result = pageStateChanger.copyRules(oldPage.getId(), 11l);
        assertNotNull(result);
    }


    private WaUiMetadata getwaUiMetaDtum(WaTemplate aPage) {
        WaUiMetadata record = new WaUiMetadata();
        record.setWaTemplateUid(aPage);
        record.setQuestionIdentifier("identifier");
        return record;
    }

    private WaRdbMetadata getWaRdbMetadata(WaTemplate aPage) {
        WaRdbMetadata record = new WaRdbMetadata();
        record.setQuestionIdentifier("identifier");
        record.setWaTemplateUid(aPage);
        return record;
    }

    private WaRuleMetadata getWaRuleMetadata(WaTemplate aPage) {
        WaRuleMetadata record = new WaRuleMetadata();
        record.setWaTemplateUid(aPage.getId());
        return record;
    }

    private WaNndMetadatum getWaNndMetadatum(WaTemplate aPage) {
        WaNndMetadatum record = new WaNndMetadatum();
        record.setQuestionIdentifier("identifier");
        record.setWaTemplateUid(aPage);
        return record;
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
