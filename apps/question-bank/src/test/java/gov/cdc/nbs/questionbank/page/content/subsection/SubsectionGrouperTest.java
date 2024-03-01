package gov.cdc.nbs.questionbank.page.content.subsection;

import gov.cdc.nbs.questionbank.entity.WaRdbMetadata;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.UpdateSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.request.GroupSubSectionRequest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubsectionGrouperTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    SubSectionValidator subSectionValidator;

    @InjectMocks
    private SubSectionGrouper grouper;


    @Test
    void should_group_subsection() {
        WaTemplate page = mock(WaTemplate.class);
        when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        GroupSubSectionRequest request = new GroupSubSectionRequest(100l, "BLOCK_X", getValidBatchList(), (int) 2);
        waUiMetadata.setId(request.id());
        waUiMetadata.setBlockNm(request.blockName());
        doNothing().when(page).groupSubSection(any());

        Long userId = 456L;
        grouper.group(1l, request, userId);
        verify(page).groupSubSection(any());
    }

    @Test
    void should_not_update_null_blockName() {
        Long userId = 456L;
        GroupSubSectionRequest nullBlockNameRequest = new GroupSubSectionRequest(100l, null, getValidBatchList(), 2);
        UpdateSubSectionException exception =
            assertThrows(UpdateSubSectionException.class, () -> grouper.group(1l, nullBlockNameRequest, userId));
        assertEquals("SubSection Block Name is required", exception.getMessage());

        GroupSubSectionRequest emptyBlockNameRequest = new GroupSubSectionRequest(100l, " ", getValidBatchList(), 2);
        UpdateSubSectionException exception2 =
            assertThrows(UpdateSubSectionException.class, () -> grouper.group(1l, emptyBlockNameRequest, userId));
        assertEquals("SubSection Block Name is required", exception2.getMessage());
    }

    @Test
    void should_not_update_invalid_repeatingNbr() {
        Long userId = 456L;
        GroupSubSectionRequest request = new GroupSubSectionRequest(100l, "test", getValidBatchList(), 6);
        UpdateSubSectionException exception =
            assertThrows(UpdateSubSectionException.class, () -> grouper.group(1l, request, userId));
        assertEquals("Valid repeat Number values include 0-5", exception.getMessage());
    }

    @Test
    void should_not_update_null_columWith() {
        GroupSubSectionRequest request = new GroupSubSectionRequest(100l, "BLOCK_X", getUnValidBatchColumnWidth(), 2);
        Long userId = 456L;
        UpdateSubSectionException exception =
            assertThrows(UpdateSubSectionException.class, () -> grouper.group(1l, request, userId));
        assertEquals("Batch TableColumnWidth is required", exception.getMessage());
    }

    @Test
    void should_not_update_columnWith_lessThan_100() {
        GroupSubSectionRequest request =
            new GroupSubSectionRequest(100l, "BLOCK_X", getUnValidBatchColumnWidthTotal(), 2);
        Long userId = 456L;
        UpdateSubSectionException exception =
            assertThrows(UpdateSubSectionException.class, () -> grouper.group(1l, request, userId));
        assertEquals("The total of batch TableColumnWidth must calculate to 100", exception.getMessage());
    }


    @Test
    void should_unGroup_subsection() {
        long subsectionId = 100l;
        WaTemplate page = mock(WaTemplate.class);
        when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setId(subsectionId);
        List<Long> batches = new ArrayList<>(Arrays.asList(101L, 102L));
        doNothing().when(page).unGroupSubSection(any());
        grouper.unGroup(1l, subsectionId, 456L);
        verify(page).unGroupSubSection(any());
    }



    @Test
    void testGroupSubSection() {
        WaUiMetadata subsection = mock(WaUiMetadata.class);
        when(subsection.getId()).thenReturn(1L);
        when(subsection.getNbsUiComponentUid()).thenReturn(1016L);
        PageContentCommand.GroupSubsection command = mock(PageContentCommand.GroupSubsection.class);
        when(command.blockName()).thenReturn("BlockName");
        when(command.batches()).thenReturn(getValidBatchList());
        when(command.subsection()).thenReturn(1L);
        WaUiMetadata questionBatch1 = createBatch(101l, 1008l);
        WaUiMetadata questionBatch2 = createBatch(102l, 1008l);
        questionBatch2.setWaRdbMetadatum(new WaRdbMetadata());
        List<WaUiMetadata> uiMetadata = Arrays.asList(subsection, questionBatch1, questionBatch2);
        WaTemplate waTemplate = new WaTemplate();
        waTemplate.setUiMetadata(uiMetadata);
        waTemplate.groupSubSection(command);
        verify(subsection).update(any(PageContentCommand.GroupSubsection.class), anyInt());
    }



    @Test
    void testUnGroupSubSection() {
        WaUiMetadata subsection = new WaUiMetadata();
        subsection.setId(1L);
        subsection.setNbsUiComponentUid(1016L);
        List<Long> batchIds = Arrays.asList(2L, 3L);
        PageContentCommand.UnGroupSubsection command = mock(PageContentCommand.UnGroupSubsection.class);
        when(command.subsection()).thenReturn(1L);
        when(command.batches()).thenReturn(batchIds);
        WaUiMetadata questionBatch1 = createBatch(2L, 1008l);
        WaUiMetadata questionBatch2 = createBatch(3L, 1008l);
        questionBatch2.setWaRdbMetadatum(new WaRdbMetadata());
        List<WaUiMetadata> uiMetadata = Arrays.asList(subsection, questionBatch1, questionBatch2);
        WaTemplate waTemplate = new WaTemplate();
        waTemplate.setUiMetadata(uiMetadata);
        waTemplate.unGroupSubSection(command);
        assertNull(subsection.getBlockNm());
    }

    @Test
    void should_not_ungroup_subsection_contains_non_question_elements() {
        WaUiMetadata subsection = new WaUiMetadata();
        subsection.setId(1L);
        subsection.setNbsUiComponentUid(1016L);
        List<Long> batchIds = Arrays.asList(2L, 3L);
        PageContentCommand.UnGroupSubsection command = mock(PageContentCommand.UnGroupSubsection.class);
        when(command.subsection()).thenReturn(1L);
        when(command.batches()).thenReturn(batchIds);
        WaUiMetadata questionBatch1 = createBatch(2L, 1008l);
        WaUiMetadata questionBatch2 = createBatch(3L, 1008l);
        WaUiMetadata nonQuestionBatch = createBatch(103l, 1014l);
        List<WaUiMetadata> uiMetadata = Arrays.asList(subsection, questionBatch1, questionBatch2, nonQuestionBatch);
        WaTemplate waTemplate = new WaTemplate();
        waTemplate.setUiMetadata(uiMetadata);
        waTemplate.unGroupSubSection(command);
        Assert.assertNull(subsection.getBlockNm());

    }


    @Test
    void testUpdateGroupQuestionBatch() {
        PageContentCommand.GroupSubsection command = mock(PageContentCommand.GroupSubsection.class);
        GroupSubSectionRequest.Batch batch = new GroupSubSectionRequest.Batch(123l, 'Y', "TableHeader", 50);
        when(command.batches()).thenReturn(Arrays.asList(batch));
        when(command.blockName()).thenReturn("BlockName");
        WaUiMetadata waUiMetadata = getInitialWaUiMetadata();
        waUiMetadata.setId(123l);

        waUiMetadata.updateQuestionBatch(command, 10);
        assertEquals("BLOCKNAME", waUiMetadata.getBlockNm());
        assertEquals('Y', waUiMetadata.getBatchTableAppearIndCd());
        assertEquals("TableHeader", waUiMetadata.getBatchTableHeader());
        assertEquals(50, waUiMetadata.getBatchTableColumnWidth());
    }

    @Test
    void testUpdateGroupQuestionBatch_null_QuestionLabel() {
        PageContentCommand.GroupSubsection command = mock(PageContentCommand.GroupSubsection.class);
        GroupSubSectionRequest.Batch batch1 = new GroupSubSectionRequest.Batch(123l, 'N', null, 50);
        GroupSubSectionRequest.Batch batch2 = new GroupSubSectionRequest.Batch(124l, 'N', null, 50);
        when(command.batches()).thenReturn(Arrays.asList(batch1, batch2));
        when(command.blockName()).thenReturn("BlockName");
        WaUiMetadata question1 = getInitialWaUiMetadata();
        question1.setId(123l);
        question1.setQuestionLabel("testLabel length < 50");
        WaUiMetadata question2 = getInitialWaUiMetadata();
        question2.setId(124l);
        question2.setQuestionLabel("testLabel length > 50.........................end....");

        question1.updateQuestionBatch(command, 10);
        assertEquals('N', question1.getBatchTableAppearIndCd());
        assertEquals("testLabel length < 50", question1.getBatchTableHeader());

        question2.updateQuestionBatch(command, 11);
        assertEquals('N', question1.getBatchTableAppearIndCd());
        assertEquals("testLabel length > 50.........................end", question2.getBatchTableHeader());
    }



    @Test
    void testUpdateGroup() {
        PageContentCommand.GroupSubsection command = mock(PageContentCommand.GroupSubsection.class);
        when(command.blockName()).thenReturn("BlockName");
        WaUiMetadata waUiMetadata = getInitialWaUiMetadata();
        waUiMetadata.update(command, 2);
        assertEquals("BLOCKNAME", waUiMetadata.getBlockNm());
    }

    @Test
    void testUpdateUnGroup() {
        PageContentCommand.UnGroupSubsection command = mock(PageContentCommand.UnGroupSubsection.class);
        WaUiMetadata waUiMetadata = getInitialWaUiMetadata();
        waUiMetadata.update(command);
        assertEquals(null, waUiMetadata.getBlockNm());
    }

    @Test
    void testUpdateUnGroupQuestionBatch() {
        PageContentCommand.UnGroupSubsection command = mock(PageContentCommand.UnGroupSubsection.class);
        WaUiMetadata waUiMetadata = getInitialWaUiMetadata();
        waUiMetadata.updateQuestionBatch(command);
        assertEquals(null, waUiMetadata.getBlockNm());
        assertEquals(null, waUiMetadata.getBatchTableAppearIndCd());
        assertEquals(null, waUiMetadata.getBatchTableHeader());
        assertEquals(null, waUiMetadata.getBatchTableColumnWidth());
    }

    List<GroupSubSectionRequest.Batch> getValidBatchList() {
        List<GroupSubSectionRequest.Batch> batchList = new ArrayList<>();
        batchList.add(new GroupSubSectionRequest.Batch(101l, 'Y', "header1", 25));
        batchList.add(new GroupSubSectionRequest.Batch(102l, 'Y', "header2", 75));
        return batchList;
    }

    List<GroupSubSectionRequest.Batch> getUnValidBatchListWithNonQuestionElements() {
        List<GroupSubSectionRequest.Batch> batchList = new ArrayList<>();
        batchList.add(new GroupSubSectionRequest.Batch(101l, 'Y', "header1", 25));
        batchList.add(new GroupSubSectionRequest.Batch(102l, 'Y', "header2", 50));
        batchList.add(new GroupSubSectionRequest.Batch(103l, 'Y', "header2", 25));
        return batchList;
    }

    List<GroupSubSectionRequest.Batch> getUnValidBatchColumnWidth() {
        List<GroupSubSectionRequest.Batch> batchList = new ArrayList<>();
        batchList.add(new GroupSubSectionRequest.Batch(101l, 'Y', "header1", 0));
        batchList.add(new GroupSubSectionRequest.Batch(102l, 'Y', "header2", 75));
        return batchList;
    }

    List<GroupSubSectionRequest.Batch> getUnValidBatchColumnWidthTotal() {
        List<GroupSubSectionRequest.Batch> batchList = new ArrayList<>();
        batchList.add(new GroupSubSectionRequest.Batch(101l, 'Y', "header1", 30));
        batchList.add(new GroupSubSectionRequest.Batch(102l, 'Y', "header2", 40));
        return batchList;
    }

    WaUiMetadata getInitialWaUiMetadata() {
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setId(1L);
        waUiMetadata.setBlockNm("InitialBlockName");
        waUiMetadata.setBatchTableAppearIndCd('N');
        waUiMetadata.setBatchTableHeader("InitialTableHeader");
        waUiMetadata.setBatchTableColumnWidth(0);
        return waUiMetadata;
    }

    WaUiMetadata createBatch(long batchId, Long nbsUiComponentUid) {
        WaUiMetadata questionBatch = new WaUiMetadata();
        questionBatch.setId(batchId);
        questionBatch.setNbsUiComponentUid(nbsUiComponentUid);
        return questionBatch;
    }

}
