package gov.cdc.nbs.questionbank.page.content.addtab;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.DeleteTabException;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.UpdateTabException;
import gov.cdc.nbs.questionbank.page.content.tab.request.DeleteTabRequest;
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
                new UpdateTabRequest(123L,  "Local", "T");

        UpdateTabResponse updateTabResponse =
                createTabService.updateTab( updateTabRequest);
        assertEquals(" Tab Updated Successfully", updateTabResponse.message());
    }

    @Test
    void updateTabNoLabelOrVisibilityTest() {

        UpdateTabRequest updateTabRequest =
                new UpdateTabRequest(123L,  null, null);


        assertThrows(UpdateTabException.class, () ->createTabService.updateTab(updateTabRequest));

    }

    @Test
    void deleteTabTest() {

        DeleteTabRequest deleteTabRequest =
                new DeleteTabRequest(123L);


        Mockito.when(waUiMetaDataRepository.getOrderNumber(123L))
                .thenReturn(1);


        WaTemplate waTemplate = new WaTemplate();
        waTemplate.setId(1234L);

        Mockito.when(waUiMetaDataRepository.findPageNumber( 123L))
                .thenReturn(waTemplate);

        Mockito.when(waUiMetaDataRepository.findNextNbsUiComponentUid( 2, 1234L))
                .thenReturn(Optional.of(1010L));

        DeleteTabResponse deleteTabResponse =
                createTabService.deleteTab( deleteTabRequest);
        assertEquals("Tab Deleted Successfully", deleteTabResponse.message());
    }

    @Test
    void deleteTabTestExceptionInElse() {

        DeleteTabRequest deleteTabRequest =
                new DeleteTabRequest(123L);


        Mockito.when(waUiMetaDataRepository.getOrderNumber(123L))
                .thenReturn(1);

        WaTemplate waTemplate = new WaTemplate();
        waTemplate.setId(1234L);

        Mockito.when(waUiMetaDataRepository.findPageNumber( 123L))
                .thenReturn(waTemplate);

        Mockito.when(waUiMetaDataRepository.findNextNbsUiComponentUid( 2, 1234L))
                .thenReturn(Optional.of(10L));
        assertThrows(DeleteTabException.class, () -> createTabService.deleteTab( deleteTabRequest));

    }


    @Test
    void updateTabServiceTestException() {
        assertThrows(UpdateTabException.class, () -> createTabService.updateTab(null));

    }
    @Test
    void deleteTabServiceTestException() {
        assertThrows(DeleteTabException.class, () -> createTabService.deleteTab(null));

    }


    @Test
    void createTabServiceTestException() throws AddTabException {
        assertThrows(AddTabException.class, () -> createTabService.createTab(10L, 123L, null));
    }
}
