package gov.cdc.nbs.questionbank.page.content.addsubsection;

import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.subsection.SubSectionCreator;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.DeleteSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.OrderSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.UpdateSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.request.CreateSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.request.DeleteSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.request.OrderSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.request.UpdateSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.response.CreateSubSectionResponse;
import gov.cdc.nbs.questionbank.page.content.subsection.response.DeleteSubSectionResponse;
import gov.cdc.nbs.questionbank.page.content.subsection.response.OrderSubSectionResponse;
import gov.cdc.nbs.questionbank.page.content.subsection.response.UpdateSubSectionResponse;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddSubSectionServiceTest {

    @InjectMocks
    private SubSectionCreator createSubSectionService;

    @Mock
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @Mock
    private EntityManager entityManager;

    @ParameterizedTest
    @MethodSource("createSubSectionTestParams")
    void createSubSectionServiceTest(long sectionId, String label, boolean visibility, String expectedMessage) {
        WaUiMetadata section = new WaUiMetadata();
        section.setOrderNbr(1);
        when(waUiMetaDataRepository.findById(sectionId)).thenReturn(Optional.of(section));

        CreateSubSectionRequest createSubSectionRequest = new CreateSubSectionRequest(sectionId, label, visibility);

        CreateSubSectionResponse createSubSectionResponse = createSubSectionService.createSubSection(sectionId, 123L, createSubSectionRequest);

        assertEquals(expectedMessage, createSubSectionResponse.message());
    }

    static Stream<Arguments> createSubSectionTestParams() {
        return Stream.of(
                Arguments.of(10L, "Local", true, "SubSection Created Successfully"),
                Arguments.of(20L, "Global", false, "SubSection Created Successfully"),
                Arguments.of(30L, "Test", true, "SubSection Created Successfully")
        );
    }

    @ParameterizedTest
    @MethodSource("updateSubSectionTestParams")
    void updateSubSectionServiceTest(UpdateSubSectionRequest updateSubSectionRequest, String expectedMessage) {
        UpdateSubSectionResponse updateSubSectionResponse = createSubSectionService.updateSubSection(updateSubSectionRequest);
        assertEquals(expectedMessage, updateSubSectionResponse.message());
    }

    static Stream<Arguments> updateSubSectionTestParams() {
        return Stream.of(
                Arguments.of(new UpdateSubSectionRequest(123L, "Local", "T"), "SubSection Updated Successfully"),
                Arguments.of(new UpdateSubSectionRequest(456L, "Global", "F"), "SubSection Updated Successfully")
        );
    }

    @ParameterizedTest
    @MethodSource("updateSubSectionNoLabelOrVisibilityTestParams")
    void updateSubSectionServiceNoLabelOrVisibilityTest(UpdateSubSectionRequest updateSubSectionRequest) {
        assertThrows(UpdateSubSectionException.class, () -> createSubSectionService.updateSubSection(updateSubSectionRequest));
    }

    static Stream<UpdateSubSectionRequest> updateSubSectionNoLabelOrVisibilityTestParams() {
        return Stream.of(
                new UpdateSubSectionRequest(123L, null, null),
                new UpdateSubSectionRequest(456L, null, "T"),
                new UpdateSubSectionRequest(789L, "Local", null)
        );
    }

    @ParameterizedTest
    @MethodSource("deleteSubSectionTestParams")
    void deleteSubSectionTest(long sectionId, String expectedMessage) {
        DeleteSubSectionRequest deleteSubSectionRequest = new DeleteSubSectionRequest(sectionId);

        Mockito.when(waUiMetaDataRepository.getOrderNumber(sectionId))
                .thenReturn(1);

        Mockito.when(waUiMetaDataRepository.findPageNumber(sectionId))
                .thenReturn(1234L);

        Mockito.when(waUiMetaDataRepository.findNextNbsUiComponentUid(2, 1234L))
                .thenReturn(1015L);

        DeleteSubSectionResponse deleteSubSectionResponse = createSubSectionService.deleteSubSection(deleteSubSectionRequest);
        assertEquals(expectedMessage, deleteSubSectionResponse.message());
    }

    static Stream<Arguments> deleteSubSectionTestParams() {
        return Stream.of(
                Arguments.of(123L, "Sub Section Deleted Successfully"),
                Arguments.of(456L, "Sub Section Deleted Successfully"),
                Arguments.of(789L, "Sub Section Deleted Successfully")
        );
    }

    @ParameterizedTest
    @MethodSource("deleteSubSectionTestExceptionInElseParams")
    void deleteSubSectionTestExceptionInElse(long sectionId) {
        DeleteSubSectionRequest deleteSubSectionRequest = new DeleteSubSectionRequest(sectionId);

        Mockito.when(waUiMetaDataRepository.getOrderNumber(sectionId))
                .thenReturn(1);

        Mockito.when(waUiMetaDataRepository.findPageNumber(sectionId))
                .thenReturn(1234L);

        Mockito.when(waUiMetaDataRepository.findNextNbsUiComponentUid(2, 1234L))
                .thenReturn(10000L);

        assertThrows(DeleteSubSectionException.class, () -> createSubSectionService.deleteSubSection(deleteSubSectionRequest));
    }

    static Stream<Long> deleteSubSectionTestExceptionInElseParams() {
        return Stream.of(123L, 456L, 789L);
    }

    @ParameterizedTest
    @MethodSource("orderSubSectionTestParams")
    void orderSubSectionForwardOrdering(OrderSubSectionRequest orderSubSectionRequest, String expectedMessage) throws OrderSubSectionException {
        List<Integer> orderNumberList = new ArrayList<>();
        orderNumberList.add(3);
        orderNumberList.add(58);
        orderNumberList.add(116);
        orderNumberList.add(157);

        Mockito.when(waUiMetaDataRepository.getOrderNumberList(100L))
                .thenReturn(orderNumberList);

        Mockito.when(waUiMetaDataRepository.getSectionOrderNumberList(100L, 58, 116))
                .thenReturn(orderNumberList);

        Mockito.when(waUiMetaDataRepository.getSubSectionOrderNumberList(100L, 3, 58))
                .thenReturn(orderNumberList);

        Mockito.when(waUiMetaDataRepository.getMaxOrderNumber(100L))
                .thenReturn(250);

        OrderSubSectionResponse orderSubSectionResponse = createSubSectionService.orderSubSection(100L, orderSubSectionRequest);

        assertEquals(expectedMessage, orderSubSectionResponse.message());
    }

    static Stream<Arguments> orderSubSectionTestParams() {
        return Stream.of(
                Arguments.of(new OrderSubSectionRequest(123L, 2, 1, 1, 3), "The sub section is moved from 1 to 3 successfully"),
                Arguments.of(new OrderSubSectionRequest(456L, 2, 1, 1, 2), "The sub section is moved from 1 to 2 successfully")
        );
    }

    @ParameterizedTest
    @MethodSource("orderSubSectionForwardOrderingLastPositionParams")
    void orderSubSectionForwardOrderingLastPosition(OrderSubSectionRequest orderSubSectionRequest, String expectedMessage) throws OrderSubSectionException {
        List<Integer> orderNumberList = new ArrayList<>();
        orderNumberList.add(3);
        orderNumberList.add(58);
        orderNumberList.add(116);
        orderNumberList.add(157);

        Mockito.when(waUiMetaDataRepository.getOrderNumberList(100L))
                .thenReturn(orderNumberList);

        Mockito.when(waUiMetaDataRepository.getSectionOrderNumberList(100L, 58, 116))
                .thenReturn(orderNumberList);

        Mockito.when(waUiMetaDataRepository.getSubSectionOrderNumberList(100L, 3, 58))
                .thenReturn(orderNumberList);

        Mockito.when(waUiMetaDataRepository.getMaxOrderNumber(100L))
                .thenReturn(250);

        OrderSubSectionResponse orderSubSectionResponse = createSubSectionService.orderSubSection(100L, orderSubSectionRequest);

        assertEquals(expectedMessage, orderSubSectionResponse.message());
    }

    static Stream<Arguments> orderSubSectionForwardOrderingLastPositionParams() {
        return Stream.of(
                Arguments.of(new OrderSubSectionRequest(123L, 2, 1, 1, 3), "The sub section is moved from 1 to 3 successfully"),
                Arguments.of(new OrderSubSectionRequest(456L, 2, 1, 1, 2), "The sub section is moved from 1 to 2 successfully")
                );
    }

    @ParameterizedTest
    @MethodSource("orderSubSectionReverseOrderingParams")
    void orderSubSectionReverseOrdering(OrderSubSectionRequest orderSubSectionRequest, String expectedMessage) throws OrderSubSectionException {
        List<Integer> orderNumberList = new ArrayList<>();
        orderNumberList.add(3);
        orderNumberList.add(58);
        orderNumberList.add(116);
        orderNumberList.add(157);

        Mockito.when(waUiMetaDataRepository.getOrderNumberList(100L))
                .thenReturn(orderNumberList);

        Mockito.when(waUiMetaDataRepository.getSectionOrderNumberList(100L, 58, 116))
                .thenReturn(orderNumberList);

        Mockito.when(waUiMetaDataRepository.getSubSectionOrderNumberList(100L, 3, 58))
                .thenReturn(orderNumberList);

        Mockito.when(waUiMetaDataRepository.getMaxOrderNumber(100L))
                .thenReturn(250);

        OrderSubSectionResponse orderSubSectionResponse = createSubSectionService.orderSubSection(100L, orderSubSectionRequest);

        assertEquals(expectedMessage, orderSubSectionResponse.message());
    }

    static Stream<Arguments> orderSubSectionReverseOrderingParams() {
        return Stream.of(
                Arguments.of(new OrderSubSectionRequest(123L, 2, 1, 4, 2), "The sub section is moved from 4 to 2 successfully"),
                Arguments.of(new OrderSubSectionRequest(456L, 2, 1, 3, 1), "The sub section is moved from 3 to 1 successfully"),
                Arguments.of(new OrderSubSectionRequest(789L, 2, 1, 4, 1), "The sub section is moved from 4 to 1 successfully")
        );
    }

    @ParameterizedTest
    @MethodSource("orderSubSectionReverseOrderingFirstPositionParams")
    void orderSubSectionReverseOrderingFirstPosition(OrderSubSectionRequest orderSubSectionRequest, String expectedMessage) throws OrderSubSectionException {
        List<Integer> orderNumberList = new ArrayList<>();
        orderNumberList.add(3);
        orderNumberList.add(58);
        orderNumberList.add(116);
        orderNumberList.add(157);

        Mockito.when(waUiMetaDataRepository.getOrderNumberList(100L))
                .thenReturn(orderNumberList);

        Mockito.when(waUiMetaDataRepository.getSectionOrderNumberList(100L, 58, 116))
                .thenReturn(orderNumberList);

        Mockito.when(waUiMetaDataRepository.getSubSectionOrderNumberList(100L, 3, 58))
                .thenReturn(orderNumberList);

        Mockito.when(waUiMetaDataRepository.getMaxOrderNumber(100L))
                .thenReturn(250);

        OrderSubSectionResponse orderSubSectionResponse = createSubSectionService.orderSubSection(100L, orderSubSectionRequest);

        assertEquals(expectedMessage, orderSubSectionResponse.message());
    }

    static Stream<Arguments> orderSubSectionReverseOrderingFirstPositionParams() {
        return Stream.of(
                Arguments.of(new OrderSubSectionRequest(123L, 2, 1, 4, 1), "The sub section is moved from 4 to 1 successfully"),
                Arguments.of(new OrderSubSectionRequest(456L, 2, 1, 3, 1), "The sub section is moved from 3 to 1 successfully"),
                Arguments.of(new OrderSubSectionRequest(789L, 2, 1, 4, 1), "The sub section is moved from 4 to 1 successfully")
        );
    }
}
