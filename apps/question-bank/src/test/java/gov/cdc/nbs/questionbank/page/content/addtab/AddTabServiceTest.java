package gov.cdc.nbs.questionbank.page.content.addtab;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.page.content.tab.TabCreator;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.AddTabException;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.page.content.tab.request.CreateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.CreateTabResponse;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.OrderTabException;
import gov.cdc.nbs.questionbank.page.content.tab.request.OrderTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.OrderTabResponse;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddTabServiceTest {

    @InjectMocks
    private TabCreator createTabService;

    @Mock
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @Mock
    private EntityManager entityManager;

    @Test
    void createTabServiceTest() throws AddTabException {
        CreateTabRequest createTabRequest = new CreateTabRequest("Local", true);
        CreateTabResponse createUiResponse = createTabService.createTab(10L, 123L, createTabRequest);
        assertEquals("Tab Created Successfully", createUiResponse.message());
    }

    @Test
    void createTabServiceTestException() {
        assertThrows(AddTabException.class, () -> createTabService.createTab(10L, 123L, null));
    }

    @ParameterizedTest
    @MethodSource("orderTabInvalidPositionParams")
    void orderTabInvalidPosition(OrderTabRequest orderTabRequest) {
        assertThrows(OrderTabException.class, () -> createTabService.orderTab(10L, orderTabRequest));
    }

    static Stream<OrderTabRequest> orderTabInvalidPositionParams() {
        return Stream.of(
                new OrderTabRequest(123L, -1, 3),
                new OrderTabRequest(456L, -2, 1),
                new OrderTabRequest(789L, -3, 2)
        );
    }

    @ParameterizedTest
    @MethodSource("orderTabForwardOrderingParams")
    void orderTabForwardOrdering(OrderTabRequest orderTabRequest, String expectedMessage) throws OrderTabException {
        List<Integer> orderNumberList = new ArrayList<>();
        orderNumberList.add(3);
        orderNumberList.add(58);
        orderNumberList.add(116);
        orderNumberList.add(157);

        Mockito.when(waUiMetaDataRepository.getOrderNumberList(100L))
                .thenReturn(orderNumberList);

        Mockito.when(waUiMetaDataRepository.getMaxOrderNumber(100L))
                .thenReturn(250);

        OrderTabResponse orderTabResponse = createTabService.orderTab(100L, orderTabRequest);

        assertEquals(expectedMessage, orderTabResponse.message());
    }

    static Stream<Arguments> orderTabForwardOrderingParams() {
        return Stream.of(
                Arguments.of(new OrderTabRequest(123L, 1, 3), "The tab is moved from 1 to 3 successfully"),
                Arguments.of(new OrderTabRequest(456L, 2, 1), "The tab is moved from 2 to 1 successfully"),
                Arguments.of(new OrderTabRequest(789L, 3, 2), "The tab is moved from 3 to 2 successfully")
        );
    }

    @ParameterizedTest
    @MethodSource("orderTabForwardOrderingLastPositionParams")
    void orderTabForwardOrderingLastPosition(OrderTabRequest orderTabRequest, String expectedMessage) throws OrderTabException {
        List<Integer> orderNumberList = new ArrayList<>();
        orderNumberList.add(3);
        orderNumberList.add(58);
        orderNumberList.add(116);
        orderNumberList.add(157);

        Mockito.when(waUiMetaDataRepository.getOrderNumberList(100L))
                .thenReturn(orderNumberList);

        Mockito.when(waUiMetaDataRepository.getMaxOrderNumber(100L))
                .thenReturn(250);

        OrderTabResponse orderTabResponse = createTabService.orderTab(100L, orderTabRequest);

        assertEquals(expectedMessage, orderTabResponse.message());
    }

    static Stream<Arguments> orderTabForwardOrderingLastPositionParams() {
        return Stream.of(
                Arguments.of(new OrderTabRequest(123L, 1, 4), "The tab is moved from 1 to 4 successfully"),
                Arguments.of(new OrderTabRequest(456L, 2, 4), "The tab is moved from 2 to 4 successfully"),
                Arguments.of(new OrderTabRequest(789L, 3, 4), "The tab is moved from 3 to 4 successfully")
        );
    }

    @ParameterizedTest
    @MethodSource("orderTabReverseOrderingParams")
    void orderTabReverseOrdering(OrderTabRequest orderTabRequest, String expectedMessage) throws OrderTabException {
        List<Integer> orderNumberList = new ArrayList<>();
        orderNumberList.add(3);
        orderNumberList.add(58);
        orderNumberList.add(116);
        orderNumberList.add(157);

        Mockito.when(waUiMetaDataRepository.getOrderNumberList(100L))
                .thenReturn(orderNumberList);

        Mockito.when(waUiMetaDataRepository.getMaxOrderNumber(100L))
                .thenReturn(250);

        OrderTabResponse orderTabResponse = createTabService.orderTab(100L, orderTabRequest);

        assertEquals(expectedMessage, orderTabResponse.message());
    }

    static Stream<Arguments> orderTabReverseOrderingParams() {
        return Stream.of(
                Arguments.of(new OrderTabRequest(123L, 4, 2), "The tab is moved from 4 to 2 successfully"),
                Arguments.of(new OrderTabRequest(456L, 4, 1), "The tab is moved from 4 to 1 successfully"),
                Arguments.of(new OrderTabRequest(789L, 2, 1), "The tab is moved from 2 to 1 successfully")
        );
    }

    @ParameterizedTest
    @MethodSource("orderTabReverseOrderingFirstPositionParams")
    void orderTabReverseOrderingFirstPosition(OrderTabRequest orderTabRequest, String expectedMessage) throws OrderTabException {
        List<Integer> orderNumberList = new ArrayList<>();
        orderNumberList.add(3);
        orderNumberList.add(58);
        orderNumberList.add(116);
        orderNumberList.add(157);

        Mockito.when(waUiMetaDataRepository.getOrderNumberList(100L))
                .thenReturn(orderNumberList);

        Mockito.when(waUiMetaDataRepository.getMaxOrderNumber(100L))
                .thenReturn(250);

        OrderTabResponse orderTabResponse = createTabService.orderTab(100L, orderTabRequest);

        assertEquals(expectedMessage, orderTabResponse.message());
    }

    static Stream<Arguments> orderTabReverseOrderingFirstPositionParams() {
        return Stream.of(
                Arguments.of(new OrderTabRequest(123L, 4, 1), "The tab is moved from 4 to 1 successfully"),
                Arguments.of(new OrderTabRequest(456L, 2, 1), "The tab is moved from 2 to 1 successfully"),
                Arguments.of(new OrderTabRequest(789L, 3, 1), "The tab is moved from 3 to 1 successfully")
        );
    }
}
