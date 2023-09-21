package gov.cdc.nbs.questionbank.page.content.addsection;

import gov.cdc.nbs.questionbank.page.content.section.SectionCreator;
import gov.cdc.nbs.questionbank.page.content.section.exception.AddSectionException;
import gov.cdc.nbs.questionbank.page.content.section.exception.OrderSectionException;
import gov.cdc.nbs.questionbank.page.content.section.request.CreateSectionRequest;
import gov.cdc.nbs.questionbank.page.content.section.request.OrderSectionRequest;
import gov.cdc.nbs.questionbank.page.content.section.request.UpdateSectionRequest;
import gov.cdc.nbs.questionbank.page.content.section.response.CreateSectionResponse;
import gov.cdc.nbs.questionbank.page.content.section.response.DeleteSectionResponse;
import gov.cdc.nbs.questionbank.page.content.section.response.OrderSectionResponse;
import gov.cdc.nbs.questionbank.page.content.section.response.UpdateSectionResponse;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddSectionServiceTest {

    @InjectMocks
    private SectionCreator createSectionService;

    @Mock
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @Mock
    private EntityManager entityManager;

    @ParameterizedTest
    @ValueSource(longs = {10L, 20L, 30L})
    void createSectionServiceTest(long sectionId) {
        CreateSectionRequest createSectionRequest = new CreateSectionRequest(sectionId, "Local", true);

        CreateSectionResponse createSectionResponse = createSectionService.createSection(sectionId, 123L, createSectionRequest);

        assertEquals("Section Created Successfully", createSectionResponse.message());
    }

    @ParameterizedTest
    @MethodSource("updateSectionRequestProvider")
    void updateSectionServiceTest(UpdateSectionRequest updateSectionRequest) {
        UpdateSectionResponse updateSectionResponse = createSectionService.updateSection(123L, updateSectionRequest);
        assertEquals("Section updated successfully", updateSectionResponse.message());
    }

    static Stream<UpdateSectionRequest> updateSectionRequestProvider() {
        // Provide various UpdateSectionRequest instances here
        return Stream.of(
                new UpdateSectionRequest( "Local", "T"),
                new UpdateSectionRequest( "Global", "F")
        );
    }

    @ParameterizedTest
    @ValueSource(longs = {123L, 456L, 789L})
    void deleteSectionTest(long sectionId) {

        Mockito.when(waUiMetaDataRepository.getOrderNumber(sectionId))
                .thenReturn(1);

        DeleteSectionResponse deleteSectionResponse = createSectionService.deleteSection(123L, sectionId);
        assertEquals("Section deleted successfully", deleteSectionResponse.message());
    }

    @Test
    void createSectionServiceTestException() {
        assertThrows(AddSectionException.class, () -> createSectionService.createSection(10L, 123L, null));
    }

    @ParameterizedTest
    @MethodSource("orderSectionRequestProvider")
    void orderSectionForwardOrdering(OrderSectionRequest orderSectionRequest, String expectedMessage) throws OrderSectionException {
        List<Integer> orderNumberList = new ArrayList<>();
        orderNumberList.add(3);
        orderNumberList.add(58);
        orderNumberList.add(116);
        orderNumberList.add(157);

        Mockito.when(waUiMetaDataRepository.getOrderNumberList(100L))
                .thenReturn(orderNumberList);

        Mockito.when(waUiMetaDataRepository.getSectionOrderNumberList(100L, 58, 116))
                .thenReturn(orderNumberList);

        Mockito.when(waUiMetaDataRepository.getMaxOrderNumber(100L))
                .thenReturn(250);

        OrderSectionResponse orderSectionResponse = createSectionService.orderSection(100L, orderSectionRequest);

        assertEquals(expectedMessage, orderSectionResponse.message());
    }

    static Stream<Arguments> orderSectionRequestProvider() {
        return Stream.of(
                Arguments.of(new OrderSectionRequest(123L, 2, 1, 3), "The section is moved from 1 to 3 successfully"),
                Arguments.of(new OrderSectionRequest(456L, 2, 1, 2), "The section is moved from 1 to 2 successfully"),
                Arguments.of(new OrderSectionRequest(789L, 2, 2, 1), "The section is moved from 2 to 1 successfully")
        );
    }
}
