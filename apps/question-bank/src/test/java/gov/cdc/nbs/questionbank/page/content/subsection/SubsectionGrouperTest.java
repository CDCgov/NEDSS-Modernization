package gov.cdc.nbs.questionbank.page.content.subsection;

import gov.cdc.nbs.questionbank.entity.WaRdbMetadata;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.UpdateSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.request.GroupSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.request.UnGroupSubSectionRequest;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import gov.cdc.nbs.questionbank.question.QuestionManagementUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
    QuestionManagementUtil questionManagementUtil;

    @Mock
    SubSectionQuestionFinder finder;

    @InjectMocks
    private SubSectionGrouper grouper;

    List<Long> questionNbsUiComponentUids = Arrays.asList(1007l, 1008l, 1009l);

    @Test
    void should_group_subsection() {
        WaTemplate page = mock(WaTemplate.class);
        when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        GroupSubSectionRequest request = new GroupSubSectionRequest(100l, "BLOCK_X", getValidBatchList(), (int) 2);
        waUiMetadata.setId(request.id());
        waUiMetadata.setBlockNm(request.blockName());
        doNothing().when(page).groupSubSection(any(), any());

        Collection<RdbQuestion> listOfQuestions = new ArrayList<RdbQuestion>();

        listOfQuestions.add(new RdbQuestion(1L, 101L, page.getId(), 2));
        listOfQuestions.add(new RdbQuestion(2L, 102L, page.getId(), 2));

        when(finder.resolve(1L)).thenReturn(listOfQuestions);

        WaRdbMetadata temp1 = new WaRdbMetadata();
        WaRdbMetadata temp2 = new WaRdbMetadata();
        when(entityManager.find(WaRdbMetadata.class, 1L)).thenReturn(temp1);
        when(entityManager.find(WaRdbMetadata.class, 2L)).thenReturn(temp2);

        Long userId = 456L;
        grouper.group(1l, request, userId);
        verify(page).groupSubSection(any(), any());
    }

    @Test
    void should_not_group_subsection_no_page_found() {
        when(entityManager.find(WaTemplate.class, 1l)).thenReturn(null);
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        GroupSubSectionRequest request = new GroupSubSectionRequest(100l, "BLOCK_X", getValidBatchList(), 1);
        waUiMetadata.setId(request.id());
        waUiMetadata.setBlockNm(request.blockName());
        Long userId = 456L;
        assertThrows(PageNotFoundException.class, () -> grouper.group(1l, request, userId));
    }

    @Test
    void should_not_update_null_blockName() {
        GroupSubSectionRequest request = new GroupSubSectionRequest(100l, null, getValidBatchList(), 2);
        Long userId = 456L;
        UpdateSubSectionException exception =
                assertThrows(UpdateSubSectionException.class, () -> grouper.group(1l, request, userId));
        assertEquals("SubSection Block Name is required", exception.getMessage());
    }

    @Test
    void should_not_update_null_columWith() {
        GroupSubSectionRequest request = new GroupSubSectionRequest(100l, "BLOCK_X", getUnValidBatchColumnWidth(), 2);
        Long userId = 456L;
        UpdateSubSectionException exception =
                assertThrows(UpdateSubSectionException.class, () -> grouper.group(1l, request, userId));
        assertEquals("batch TableColumnWidth is required", exception.getMessage());
    }

    @Test
    void should_not_update_columnWith_lessThan_100() {
        GroupSubSectionRequest request =
                new GroupSubSectionRequest(100l, "BLOCK_X", getUnValidBatchColumnWidthTotal(), 2);
        Long userId = 456L;
        UpdateSubSectionException exception =
                assertThrows(UpdateSubSectionException.class, () -> grouper.group(1l, request, userId));
        assertEquals("the total of batch TableColumnWidth must calculate to 100", exception.getMessage());
    }


    @Test
    void should_unGroup_subsection() {
        WaTemplate page = mock(WaTemplate.class);
        when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setId(100l);
        List<Long> batches = new ArrayList<>(Arrays.asList(101L, 102L));
        UnGroupSubSectionRequest request = new UnGroupSubSectionRequest(100l, batches);
        doNothing().when(page).unGroupSubSection(any(), any());
        Long userId = 456L;
        grouper.unGroup(1l, request, userId);
        verify(page).unGroupSubSection(any(), any());
    }

    @Test
    void should_not_unGroup_subsection_no_page_found() {
        when(entityManager.find(WaTemplate.class, 1l)).thenReturn(null);
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setId(100l);
        List<Long> batches = new ArrayList<>(Arrays.asList(101L, 102L));
        UnGroupSubSectionRequest request = new UnGroupSubSectionRequest(100l, batches);
        Long userId = 456L;
        assertThrows(PageNotFoundException.class, () -> grouper.unGroup(1l, request, userId));
    }


    @Test
    void testGroupSubSection() {
        WaUiMetadata subsection = mock(WaUiMetadata.class);
        when(subsection.getId()).thenReturn(1L);
        when(subsection.getNbsUiComponentUid()).thenReturn(1016L);
        PageContentCommand.GroupSubsection command = mock(PageContentCommand.GroupSubsection.class);
        when(command.batches()).thenReturn(getValidBatchList());
        when(command.subsection()).thenReturn(1L);
        WaUiMetadata questionBatch1 = createBatch(101l, 1008l);
        WaUiMetadata questionBatch2 = createBatch(102l, 1008l);
        List<WaUiMetadata> uiMetadata = Arrays.asList(subsection, questionBatch1, questionBatch2);
        WaTemplate waTemplate = new WaTemplate();
        waTemplate.setUiMetadata(uiMetadata);
        waTemplate.groupSubSection(command, questionNbsUiComponentUids);
        verify(subsection).update(any(PageContentCommand.GroupSubsection.class), anyInt());
    }

    @Test
    void should_not_group_subsection_contains_non_question_elements() {
        WaUiMetadata subsection = new WaUiMetadata();
        subsection.setId(1L);
        subsection.setNbsUiComponentUid(1016L);
        PageContentCommand.GroupSubsection command = mock(PageContentCommand.GroupSubsection.class);
        when(command.batches()).thenReturn(getUnValidBatchListWithNonQuestionElements());
        WaUiMetadata questionBatch1 = createBatch(101l, 1008l);
        WaUiMetadata questionBatch2 = createBatch(102l, 1008l);
        WaUiMetadata nonQuestionBatch = createBatch(103l, 1014l);
        List<WaUiMetadata> uiMetadata = Arrays.asList(subsection, questionBatch1, questionBatch2, nonQuestionBatch);
        WaTemplate waTemplate = new WaTemplate();
        waTemplate.setUiMetadata(uiMetadata);

        UpdateSubSectionException exception = assertThrows(UpdateSubSectionException.class,
                () -> waTemplate.groupSubSection(command, questionNbsUiComponentUids));
        assertEquals("Can only group the question elements", exception.getMessage());
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
        List<WaUiMetadata> uiMetadata = Arrays.asList(subsection, questionBatch1, questionBatch2);
        WaTemplate waTemplate = new WaTemplate();
        waTemplate.setUiMetadata(uiMetadata);
        waTemplate.unGroupSubSection(command, questionNbsUiComponentUids);
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
        waTemplate.unGroupSubSection(command, questionNbsUiComponentUids);
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
        assertEquals("BlockName", waUiMetadata.getBlockNm());
        assertEquals('Y', waUiMetadata.getBatchTableAppearIndCd());
        assertEquals("TableHeader", waUiMetadata.getBatchTableHeader());
        assertEquals(50, waUiMetadata.getBatchTableColumnWidth());
    }


    @Test
    void testUpdateGroup() {
        PageContentCommand.GroupSubsection command = mock(PageContentCommand.GroupSubsection.class);
        when(command.blockName()).thenReturn("BlockName");
        WaUiMetadata waUiMetadata = getInitialWaUiMetadata();
        waUiMetadata.update(command, 2);
        assertEquals("BlockName", waUiMetadata.getBlockNm());
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
