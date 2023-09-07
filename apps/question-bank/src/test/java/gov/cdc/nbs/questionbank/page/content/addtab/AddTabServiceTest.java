package gov.cdc.nbs.questionbank.page.content.addtab;

import gov.cdc.nbs.questionbank.page.content.tab.exceptions.DeleteTabException;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.UpdateTabException;
import gov.cdc.nbs.questionbank.page.content.tab.request.UpdateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.DeleteTabResponse;
import gov.cdc.nbs.questionbank.page.content.tab.response.UpdateTabResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.page.content.tab.TabCreator;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.AddTabException;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.page.content.tab.request.CreateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.CreateTabResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import javax.persistence.EntityManager;
import java.util.Optional;

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
    void updateTabServiceTest() {

        UpdateTabRequest updateTabRequest =
                new UpdateTabRequest(  "Local", "T");

        UpdateTabResponse updateTabResponse =
                createTabService.updateTab(123L, updateTabRequest);
        assertEquals(" Tab Updated Successfully", updateTabResponse.message());
    }

    @Test
    void updateTabNoLabelOrVisibilityTest() {

        UpdateTabRequest updateTabRequest =
                new UpdateTabRequest(  null, null);


        assertThrows(UpdateTabException.class, () ->createTabService.updateTab(123L, updateTabRequest));

    }

    @Test
    void deleteTabTest() {

        Mockito.when(waUiMetaDataRepository.getOrderNumber(123L))
                .thenReturn(1);

        Mockito.when(waUiMetaDataRepository.findNextNbsUiComponentUid( 2, 1234L))
                .thenReturn(Optional.of(1010L));

        DeleteTabResponse deleteTabResponse =
                createTabService.deleteTab(1234L, 123L);
        assertEquals("Tab Deleted Successfully", deleteTabResponse.message());
    }

    @Test
    void deleteTabTestExceptionInElse() {

        Mockito.when(waUiMetaDataRepository.getOrderNumber(123L))
                .thenReturn(1);

        Mockito.when(waUiMetaDataRepository.findNextNbsUiComponentUid( 2, 1234L))
                .thenReturn(Optional.of(10L));
        assertThrows(DeleteTabException.class, () -> createTabService.deleteTab(1234L, 123L));

    }

    @Test
    void deleteTabTestInElse() {

        Mockito.when(waUiMetaDataRepository.getOrderNumber(123L))
                .thenReturn(1);

        Mockito.when(waUiMetaDataRepository.findNextNbsUiComponentUid( 2, 1234L))
                .thenReturn(Optional.empty());
        DeleteTabResponse deleteTabResponse =
                createTabService.deleteTab(1234L, 123L);
        assertEquals("Tab Deleted Successfully", deleteTabResponse.message());
    }


    @Test
    void updateTabServiceTestException() {
        assertThrows(UpdateTabException.class, () -> createTabService.updateTab(123L, null));

    }
    @Test
    void deleteTabServiceTestException() {
        Mockito.when(waUiMetaDataRepository.findNextNbsUiComponentUid( 2, 1234L))
                .thenThrow(new DeleteTabException(""));
        assertThrows(DeleteTabException.class, () -> createTabService.deleteTab(1234L,123L));

    }


    @Test
    void createTabServiceTestException() throws AddTabException {
        assertThrows(AddTabException.class, () -> createTabService.createTab(10L, 123L, null));
    }
}
