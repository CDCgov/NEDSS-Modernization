package gov.cdc.nbs.questionbank.page.content.subsection;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.UpdateSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.request.GroupSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.request.UnGroupSubSectionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubsectionGrouperTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private SubSectionGrouper grouper;


    @Test
    void should_group_subsection() {
        WaTemplate page = mock(WaTemplate.class);
        when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
        doNothing().when(entityManager).flush();
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        GroupSubSectionRequest request = new GroupSubSectionRequest(100l, "BLOCK_X", getValidBatchList());
        waUiMetadata.setId(request.id());
        waUiMetadata.setBlockNm(request.blockName());
        when(page.groupSubSection(any())).thenReturn(waUiMetadata);
        Long userId = 456L;
        ResponseEntity<String> result = grouper.group(1l, request, userId);
        assertEquals("Subsection " + request.id() + " is  Grouped Successfully , Block Name is " + request.blockName(), result.getBody());
    }

    @Test
    void should_not_update_null_blockName() {
        GroupSubSectionRequest request = new GroupSubSectionRequest(100l, null, getValidBatchList());
        Long userId = 456L;
        UpdateSubSectionException exception = assertThrows(UpdateSubSectionException.class, () -> grouper.group(1l, request, userId));
        assertEquals("SubSection Block Name is required", exception.getMessage());
    }

    @Test
    void should_not_update_null_columWith() {
        GroupSubSectionRequest request = new GroupSubSectionRequest(100l, "BLOCK_X", getUnValidBatchColumnWidth());
        Long userId = 456L;
        UpdateSubSectionException exception = assertThrows(UpdateSubSectionException.class, () -> grouper.group(1l, request, userId));
        assertEquals("batchTableColumnWidth is required", exception.getMessage());
    }

    @Test
    void should_not_update_columnWith_lessThan_100() {
        GroupSubSectionRequest request = new GroupSubSectionRequest(100l, "BLOCK_X", getUnValidBatchColumnWidthTotal());
        Long userId = 456L;
        UpdateSubSectionException exception = assertThrows(UpdateSubSectionException.class, () -> grouper.group(1l, request, userId));
        assertEquals("the total of batchTableColumnWidth must calculate to 100", exception.getMessage());
    }

    List<GroupSubSectionRequest.Batch> getValidBatchList() {
        List<GroupSubSectionRequest.Batch> batchList = new ArrayList<>();
        batchList.add(new GroupSubSectionRequest.Batch(101l, 'Y', "header1", 25));
        batchList.add(new GroupSubSectionRequest.Batch(102l, 'Y', "header2", 75));
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


    @Test
    void should_unGroup_subsection() {
        WaTemplate page = mock(WaTemplate.class);
        when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
        doNothing().when(entityManager).flush();
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setId(100l);
        List<Long> batches = new ArrayList<>(Arrays.asList(101L, 102L));
        UnGroupSubSectionRequest request = new UnGroupSubSectionRequest(100l, batches);
        when(page.unGroupSubSection(any())).thenReturn(waUiMetadata);
        Long userId = 456L;
        ResponseEntity<String> result = grouper.unGroup(1l, request, userId);
        assertEquals("Subsection " + waUiMetadata.getId() + " is UnGrouped Successfully ", result.getBody());
    }

    @Test
    void testGroupSubSection() {
        // Create a mock for WaUiMetadata
        Long subSectionId = 1L;
        WaUiMetadata subsection = mock(WaUiMetadata.class);
        when(subsection.getId()).thenReturn(subSectionId);
        when(subsection.getNbsUiComponentUid()).thenReturn(1016l);

        PageContentCommand.GroupSubsection command = mock(PageContentCommand.GroupSubsection.class);

        // Create a mock for the list of batches
        GroupSubSectionRequest.Batch batch1 = mock(GroupSubSectionRequest.Batch.class);
        when(batch1.id()).thenReturn(2L);
        GroupSubSectionRequest.Batch batch2 = mock(GroupSubSectionRequest.Batch.class);
        when(batch2.id()).thenReturn(3L);
        List<GroupSubSectionRequest.Batch> batches = Arrays.asList(batch1, batch2);

        when(command.batches()).thenReturn(batches);
        when(command.subsection()).thenReturn(subSectionId);

        // Create a mock for WaUiMetadata for question batches
        WaUiMetadata questionBatch1 = mock(WaUiMetadata.class);
        when(questionBatch1.getId()).thenReturn(2L);
        when(questionBatch1.getNbsUiComponentUid()).thenReturn(1007l);
        WaUiMetadata questionBatch2 = mock(WaUiMetadata.class);
        when(questionBatch2.getId()).thenReturn(3L);
        when(questionBatch2.getNbsUiComponentUid()).thenReturn(1007l);

        List<WaUiMetadata> uiMetadata = Arrays.asList(subsection, questionBatch1, questionBatch2);

        WaTemplate waTemplate = new WaTemplate();
        waTemplate.setUiMetadata(uiMetadata);
        WaUiMetadata result = waTemplate.groupSubSection(command);
        assertEquals(subsection, result);
        verify(subsection).update(command);
        verify(questionBatch1).updateQuestionBatch(command);
        verify(questionBatch2).updateQuestionBatch(command);
    }

    @Test
    void testUnGroupSubSection() {
        // Create a mock for WaUiMetadata
        WaUiMetadata subsection = mock(WaUiMetadata.class);
        when(subsection.getId()).thenReturn(1L);
        when(subsection.getNbsUiComponentUid()).thenReturn(1016l);
        PageContentCommand.UnGroupSubsection command = mock(PageContentCommand.UnGroupSubsection.class);

        when(command.subsection()).thenReturn(1L);
        List<Long> batchIds = Arrays.asList(1L, 2L);
        when(command.batches()).thenReturn(batchIds);

        WaUiMetadata questionBatch1 = mock(WaUiMetadata.class);
        when(questionBatch1.getId()).thenReturn(1L);
        when(questionBatch1.getNbsUiComponentUid()).thenReturn(1007l);
        WaUiMetadata questionBatch2 = mock(WaUiMetadata.class);
        when(questionBatch2.getId()).thenReturn(2L);
        when(questionBatch2.getNbsUiComponentUid()).thenReturn(1007l);

        List<WaUiMetadata> uiMetadata = Arrays.asList(subsection, questionBatch1, questionBatch2);

        WaTemplate waTemplate = new WaTemplate();
        waTemplate.setUiMetadata(uiMetadata);
        WaUiMetadata result = waTemplate.unGroupSubSection(command);

        assertEquals(subsection, result);
        verify(subsection).update(command);
        verify(questionBatch1).updateQuestionBatch(command);
        verify(questionBatch2).updateQuestionBatch(command);
    }

    @Test
    void testUpdateGroupQuestionBatch() {
        PageContentCommand.GroupSubsection command = mock(PageContentCommand.GroupSubsection.class);

        GroupSubSectionRequest.Batch batch = mock(GroupSubSectionRequest.Batch.class);
        when(batch.id()).thenReturn(123l);
        when(batch.batchTableAppearIndCd()).thenReturn('Y');
        when(batch.batchTableHeader()).thenReturn("TableHeader");
        when(batch.batchTableColumnWidth()).thenReturn(50);

        List<GroupSubSectionRequest.Batch> batches = Arrays.asList(batch);
        when(command.batches()).thenReturn(batches);
        when(command.blockName()).thenReturn("BlockName");

        WaUiMetadata waUiMetadata = getInitialWaUiMetadata();
        waUiMetadata.setId(123l);

        waUiMetadata.updateQuestionBatch(command);

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
        waUiMetadata.update(command);
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


    WaUiMetadata getInitialWaUiMetadata() {
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setId(1L);
        waUiMetadata.setBlockNm("InitialBlockName");
        waUiMetadata.setBatchTableAppearIndCd('N');
        waUiMetadata.setBatchTableHeader("InitialTableHeader");
        waUiMetadata.setBatchTableColumnWidth(0);
        return waUiMetadata;
    }

}
