package gov.cdc.nbs.questionbank.page.content.addsubsection;

import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.subsection.SubSectionCreator;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.AddSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.DeleteSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.OrderSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.UpdateSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.request.CreateSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.request.OrderSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.request.UpdateSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.response.CreateSubSectionResponse;
import gov.cdc.nbs.questionbank.page.content.subsection.response.DeleteSubSectionResponse;
import gov.cdc.nbs.questionbank.page.content.subsection.response.OrderSubSectionResponse;
import gov.cdc.nbs.questionbank.page.content.subsection.response.UpdateSubSectionResponse;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;

import org.junit.jupiter.api.Test;
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
        UpdateSubSectionResponse updateSubSectionResponse = createSubSectionService.updateSubSection(123L, updateSubSectionRequest);
        assertEquals(expectedMessage, updateSubSectionResponse.message());
    }

    static Stream<Arguments> updateSubSectionTestParams() {
        return Stream.of(
                Arguments.of(new UpdateSubSectionRequest( "Local", "T"), "SubSection Updated Successfully"),
                Arguments.of(new UpdateSubSectionRequest( "Global", "F"), "SubSection Updated Successfully")
        );
    }

    @ParameterizedTest
    @MethodSource("updateSubSectionNoLabelOrVisibilityTestParams")
    void updateSubSectionServiceNoLabelOrVisibilityTest(UpdateSubSectionRequest updateSubSectionRequest) {
        assertThrows(UpdateSubSectionException.class, () -> createSubSectionService.updateSubSection(123L,updateSubSectionRequest));
    }

    static Stream<UpdateSubSectionRequest> updateSubSectionNoLabelOrVisibilityTestParams() {
        return Stream.of(
                new UpdateSubSectionRequest( null, null),
                new UpdateSubSectionRequest( null, "T"),
                new UpdateSubSectionRequest( "Local", null)
        );
    }

    @ParameterizedTest
    @MethodSource("deleteSubSectionTestParams")
    void deleteSubSectionTest(long sectionId, String expectedMessage) {

        Mockito.when(waUiMetaDataRepository.getOrderNumber(sectionId))
                .thenReturn(1);

        DeleteSubSectionResponse deleteSubSectionResponse = createSubSectionService.deleteSubSection(123L, 123L);
        assertEquals(expectedMessage, deleteSubSectionResponse.message());
    }

    static Stream<Arguments> deleteSubSectionTestParams() {
        return Stream.of(
                Arguments.of(123L, "SubSection Deleted Successfully"));
    }

    @ParameterizedTest
    @MethodSource("deleteSubSectionTestExceptionInElseParams")
    void deleteSubSectionTestExceptionInElse(long sectionId) {

        Mockito.when(waUiMetaDataRepository.getOrderNumber(sectionId))
                .thenReturn(1);

        assertThrows(DeleteSubSectionException.class, () -> createSubSectionService.deleteSubSection(123L, 123L));
    }

    static Stream<Long> deleteSubSectionTestExceptionInElseParams() {
        return Stream.of( 456L, 789L);
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


    @Test
    void orderSubSectionSamePosition() throws OrderSubSectionException {

        OrderSubSectionRequest orderSubSectionRequest = new OrderSubSectionRequest(100L, 1, 1, 2, 2);
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

        assertThrows(OrderSubSectionException.class, () -> createSubSectionService.orderSubSection(
                100L, orderSubSectionRequest));

    }

    @Test
    void createSubSectionServiceTestException() {
        assertThrows(AddSubSectionException.class, () -> createSubSectionService.createSubSection(10L, 123L, null));
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

    @Test
    void deleteSubSectionWithNbsUidTest() {

        Mockito.when(waUiMetaDataRepository.getOrderNumber(123L))
                .thenReturn(1);

        Mockito.when(waUiMetaDataRepository.findNextNbsUiComponentUid(2, 123L))
                .thenReturn(Optional.of(1010L));

        DeleteSubSectionResponse deleteSubSectionResponse = createSubSectionService.deleteSubSection(123L, 123L);
        assertEquals("SubSection Deleted Successfully", deleteSubSectionResponse.message());


    }

    @Test
    void deleteSubSectionExceptionTest() {

        Mockito.when(waUiMetaDataRepository.getOrderNumber(123L))
                .thenReturn(1);

        Mockito.when(waUiMetaDataRepository.findNextNbsUiComponentUid(2, 123L))
                .thenReturn(Optional.of(100L));


        assertThrows(DeleteSubSectionException.class, () -> createSubSectionService.deleteSubSection(123L, 123L));

    }
}
