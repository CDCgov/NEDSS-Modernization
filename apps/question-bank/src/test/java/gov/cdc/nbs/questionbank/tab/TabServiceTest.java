package gov.cdc.nbs.questionbank.tab;
import gov.cdc.nbs.questionbank.tab.exceptions.AddTabException;
import gov.cdc.nbs.questionbank.tab.exceptions.DeleteTabException;
import gov.cdc.nbs.questionbank.tab.exceptions.UpdateTabException;
import gov.cdc.nbs.questionbank.tab.model.*;
import gov.cdc.nbs.questionbank.tab.repository.WaUiMetaDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TabServiceTest {

    @InjectMocks
    private TabService tabService;

    @Mock
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @Test
    void createTabServiceTest() throws AddTabException {

        CreateTabRequest createTabRequest =
                new CreateTabRequest(10L, "Local", "T");

        CreateTabResponse createTabResponse =
                tabService.createTab(123L, createTabRequest);
        assertEquals("Tab Created Successfully", createTabResponse.message());
    }

    @Test
    void updateTabServiceTest() {

        UpdateTabRequest updateTabRequest =
                new UpdateTabRequest(123L,  "Local", "T");

        UpdateTabResponse updateTabResponse =
                tabService.updateTab( updateTabRequest);
        assertEquals("Tab Updated Successfully", updateTabResponse.message());
    }

    @Test
    void updateTabServiceOnlyQuestionLabelTest() {

        UpdateTabRequest updateTabRequest =
                new UpdateTabRequest(123L,  "Local", null);

        UpdateTabResponse updateTabResponse =
                tabService.updateTab( updateTabRequest);
        assertEquals("Tab Updated Successfully", updateTabResponse.message());
    }

    @Test
    void updateTabServiceOnlyVisibilityTest() {

        UpdateTabRequest updateTabRequest =
                new UpdateTabRequest(123L,  null, "T");

        UpdateTabResponse updateTabResponse =
                tabService.updateTab( updateTabRequest);
        assertEquals("Tab Updated Successfully", updateTabResponse.message());
    }

    @Test
    void updateTabServiceNoLabelOrVisibilityTest() {

        UpdateTabRequest updateTabRequest =
                new UpdateTabRequest(123L,  null, null);

        UpdateTabResponse updateTabResponse =
                tabService.updateTab( updateTabRequest);
        assertEquals("questionLabel or Visible is required to update tab", updateTabResponse.message());
    }

    @Test
    void deleteTabTest() {

        DeleteTabRequest deleteTabRequest =
                new DeleteTabRequest(123L);

        DeleteTabResponse deleteTabResponse =
                tabService.deleteTab( deleteTabRequest);
        assertEquals("Tab Deleted Successfully", deleteTabResponse.message());
    }

    @Test
    void tabServiceTestException() {
        assertThrows(AddTabException.class, () -> tabService.createTab(123L, null));

    }

    @Test
    void updateTabServiceTestException() {
        assertThrows(UpdateTabException.class, () -> tabService.updateTab(null));

    }
    @Test
    void deleteTabServiceTestException() {
        assertThrows(DeleteTabException.class, () -> tabService.deleteTab( null));

    }
}

