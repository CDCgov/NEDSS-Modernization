package gov.cdc.nbs.questionbank.page.content.subsection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import gov.cdc.nbs.questionbank.entity.WaRdbMetadata;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.UpdateSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.request.GroupSubSectionRequest;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SubsectionGrouperTest {

  @Mock private EntityManager entityManager;

  @Mock SubSectionValidator subSectionValidator;

  @InjectMocks private SubSectionGrouper grouper;

  @Test
  void should_group_subsection() {
    WaTemplate page = mock(WaTemplate.class);
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
    WaUiMetadata waUiMetadata = new WaUiMetadata();
    GroupSubSectionRequest request =
        new GroupSubSectionRequest("BLOCK_X", getValidBatchList(), (int) 2);
    waUiMetadata.setId(100l);
    waUiMetadata.setBlockNm(request.blockName());
    doNothing().when(page).groupSubSection(any());

    Long userId = 456L;
    grouper.group(1l, 100l, request, userId);
    verify(page).groupSubSection(any());
  }

  @Test
  void should_not_update_null_blockName() {
    Long userId = 456L;
    GroupSubSectionRequest nullBlockNameRequest =
        new GroupSubSectionRequest(null, getValidBatchList(), 2);
    UpdateSubSectionException exception =
        assertThrows(
            UpdateSubSectionException.class,
            () -> grouper.group(1l, 100l, nullBlockNameRequest, userId));
    assertEquals(
        "SubSection Block Name is required and only allows alphanumeric or underscore",
        exception.getMessage());

    GroupSubSectionRequest emptyBlockNameRequest =
        new GroupSubSectionRequest(" ", getValidBatchList(), 2);
    UpdateSubSectionException exception2 =
        assertThrows(
            UpdateSubSectionException.class,
            () -> grouper.group(1l, 100l, emptyBlockNameRequest, userId));
    assertEquals(
        "SubSection Block Name is required and only allows alphanumeric or underscore",
        exception2.getMessage());
  }

  @Test
  void should_not_update_invalid_repeatingNbr() {
    Long userId = 456L;
    GroupSubSectionRequest request = new GroupSubSectionRequest("test", getValidBatchList(), 6);
    UpdateSubSectionException exception =
        assertThrows(
            UpdateSubSectionException.class, () -> grouper.group(1l, 100l, request, userId));
    assertEquals("Valid repeat Number values include 0-5", exception.getMessage());
  }

  @Test
  void should_not_update_invalid_repeatingNbr_negative() {
    Long userId = 456L;
    GroupSubSectionRequest request = new GroupSubSectionRequest("test", getValidBatchList(), -1);
    UpdateSubSectionException exception =
        assertThrows(
            UpdateSubSectionException.class, () -> grouper.group(1l, 100l, request, userId));
    assertEquals("Valid repeat Number values include 0-5", exception.getMessage());
  }

  @Test
  void should_not_update_null_columWith() {
    GroupSubSectionRequest request =
        new GroupSubSectionRequest("BLOCK_X", getUnValidBatchColumnWidth(), 2);
    Long userId = 456L;
    UpdateSubSectionException exception =
        assertThrows(
            UpdateSubSectionException.class, () -> grouper.group(1l, 100l, request, userId));
    assertEquals("Batch TableColumnWidth is required", exception.getMessage());
  }

  @Test
  void should_update_with_no_appears_entries() {
    List<GroupSubSectionRequest.Batch> batchList = new ArrayList<>();
    batchList.add(new GroupSubSectionRequest.Batch(101l, true, "header1", 25));
    batchList.add(new GroupSubSectionRequest.Batch(102l, true, "header2", 75));
    batchList.add(new GroupSubSectionRequest.Batch(103l, false, null, 0));
    WaTemplate page = mock(WaTemplate.class);
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
    WaUiMetadata waUiMetadata = new WaUiMetadata();
    GroupSubSectionRequest request = new GroupSubSectionRequest("BLOCK_X", batchList, (int) 2);
    waUiMetadata.setId(100l);
    waUiMetadata.setBlockNm(request.blockName());
    doNothing().when(page).groupSubSection(any());

    Long userId = 456L;
    grouper.group(1l, 100l, request, userId);
    verify(page).groupSubSection(any());
  }

  @Test
  void should_not_update_empty_label() {
    List<GroupSubSectionRequest.Batch> batchList = new ArrayList<>();
    batchList.add(new GroupSubSectionRequest.Batch(101l, true, " ", 25));
    GroupSubSectionRequest request = new GroupSubSectionRequest("BLOCK_X", batchList, 2);
    Long userId = 456L;
    UpdateSubSectionException exception =
        assertThrows(
            UpdateSubSectionException.class, () -> grouper.group(1l, 100l, request, userId));
    assertEquals("Label in table is required", exception.getMessage());
  }

  @Test
  void should_not_update_columnWith_lessThan_100() {
    GroupSubSectionRequest request =
        new GroupSubSectionRequest("BLOCK_X", getUnValidBatchColumnWidthTotal(), 2);
    Long userId = 456L;
    UpdateSubSectionException exception =
        assertThrows(
            UpdateSubSectionException.class, () -> grouper.group(1l, 100l, request, userId));
    assertEquals(
        "The total of batch TableColumnWidth must calculate to 100", exception.getMessage());
  }

  @Test
  void should_unGroup_subsection() {
    long subsectionId = 100l;
    WaTemplate page = mock(WaTemplate.class);
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
    WaUiMetadata waUiMetadata = new WaUiMetadata();
    waUiMetadata.setId(subsectionId);
    doNothing().when(page).unGroupSubSection(any());
    grouper.unGroup(1l, subsectionId, 456L);
    verify(page).unGroupSubSection(any());
  }

  @Test
  void testGroupSubSection() {
    WaUiMetadata subsection = mock(WaUiMetadata.class);
    when(subsection.getId()).thenReturn(1L);
    when(subsection.getNbsUiComponentUid()).thenReturn(1016L);
    when(subsection.getOrderNbr()).thenReturn(3);
    PageContentCommand.GroupSubsection command =
        new PageContentCommand.GroupSubsection(1l, "BlockName", getValidBatchList(), 3, 0, null);
    WaUiMetadata questionBatch1 = createBatch(101l, 1008l);
    questionBatch1.setOrderNbr(4);
    WaUiMetadata questionBatch2 = createBatch(102l, 1008l);
    questionBatch2.setOrderNbr(5);
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
    subsection.setOrderNbr(3);
    PageContentCommand.UnGroupSubsection command =
        new PageContentCommand.UnGroupSubsection(1l, 0, null);
    WaUiMetadata questionBatch1 = createBatch(2L, 1008l);
    questionBatch1.setOrderNbr(4);
    WaUiMetadata questionBatch2 = createBatch(3L, 1008l);
    questionBatch2.setOrderNbr(5);
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
    subsection.setOrderNbr(3);
    subsection.setNbsUiComponentUid(1016L);
    PageContentCommand.UnGroupSubsection command =
        new PageContentCommand.UnGroupSubsection(1l, 0, null);
    WaUiMetadata questionBatch1 = createBatch(2L, 1008l);
    questionBatch1.setOrderNbr(4);
    WaUiMetadata questionBatch2 = createBatch(3L, 1008l);
    questionBatch2.setOrderNbr(5);
    WaUiMetadata nonQuestionBatch = createBatch(103l, 1014l);
    nonQuestionBatch.setOrderNbr(6);
    List<WaUiMetadata> uiMetadata =
        Arrays.asList(subsection, questionBatch1, questionBatch2, nonQuestionBatch);
    WaTemplate waTemplate = new WaTemplate();
    waTemplate.setUiMetadata(uiMetadata);
    waTemplate.unGroupSubSection(command);
    Assertions.assertNull(subsection.getBlockNm());
  }

  @Test
  void testUpdateGroupQuestionBatch() {
    GroupSubSectionRequest.Batch batch =
        new GroupSubSectionRequest.Batch(123l, true, "TableHeader", 50);
    PageContentCommand.GroupSubsection command =
        new PageContentCommand.GroupSubsection(0, "BlockName", Arrays.asList(batch), null, 0, null);
    WaUiMetadata waUiMetadata = getInitialWaUiMetadata();
    waUiMetadata.setId(123l);

    waUiMetadata.updateQuestionBatch(command, 10);
    assertEquals("BLOCKNAME", waUiMetadata.getBlockNm());
    assertEquals('Y', waUiMetadata.getBatchTableAppearIndCd());
    assertEquals("TableHeader", waUiMetadata.getBatchTableHeader());
    assertEquals(50, waUiMetadata.getBatchTableColumnWidth());
  }

  @Test
  void testUpdateGroup() {
    PageContentCommand.GroupSubsection command =
        new PageContentCommand.GroupSubsection(0, "BlockName", null, null, 0, null);
    WaUiMetadata waUiMetadata = getInitialWaUiMetadata();
    waUiMetadata.update(command, 2);
    assertEquals("BLOCKNAME", waUiMetadata.getBlockNm());
  }

  @Test
  void testUpdateUnGroup() {
    PageContentCommand.UnGroupSubsection command =
        new PageContentCommand.UnGroupSubsection(0, 0, null);
    WaUiMetadata waUiMetadata = getInitialWaUiMetadata();
    waUiMetadata.update(command);
    assertEquals(null, waUiMetadata.getBlockNm());
  }

  @Test
  void testUpdateUnGroupQuestionBatch() {
    PageContentCommand.UnGroupSubsection command =
        new PageContentCommand.UnGroupSubsection(0, 0, null);
    WaUiMetadata waUiMetadata = getInitialWaUiMetadata();
    waUiMetadata.ungroup(command);
    assertEquals(null, waUiMetadata.getBlockNm());
    assertEquals(null, waUiMetadata.getBatchTableAppearIndCd());
    assertEquals(null, waUiMetadata.getBatchTableHeader());
    assertEquals(null, waUiMetadata.getBatchTableColumnWidth());
  }

  List<GroupSubSectionRequest.Batch> getValidBatchList() {
    List<GroupSubSectionRequest.Batch> batchList = new ArrayList<>();
    batchList.add(new GroupSubSectionRequest.Batch(101l, true, "header1", 25));
    batchList.add(new GroupSubSectionRequest.Batch(102l, true, "header2", 75));
    return batchList;
  }

  List<GroupSubSectionRequest.Batch> getUnValidBatchListWithNonQuestionElements() {
    List<GroupSubSectionRequest.Batch> batchList = new ArrayList<>();
    batchList.add(new GroupSubSectionRequest.Batch(101l, true, "header1", 25));
    batchList.add(new GroupSubSectionRequest.Batch(102l, true, "header2", 50));
    batchList.add(new GroupSubSectionRequest.Batch(103l, true, "header2", 25));
    return batchList;
  }

  List<GroupSubSectionRequest.Batch> getUnValidBatchColumnWidth() {
    List<GroupSubSectionRequest.Batch> batchList = new ArrayList<>();
    batchList.add(new GroupSubSectionRequest.Batch(101l, true, "header1", 0));
    batchList.add(new GroupSubSectionRequest.Batch(102l, true, "header2", 75));
    return batchList;
  }

  List<GroupSubSectionRequest.Batch> getUnValidBatchColumnWidthTotal() {
    List<GroupSubSectionRequest.Batch> batchList = new ArrayList<>();
    batchList.add(new GroupSubSectionRequest.Batch(101l, true, "header1", 30));
    batchList.add(new GroupSubSectionRequest.Batch(102l, true, "header2", 40));
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
