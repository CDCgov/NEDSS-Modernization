package gov.cdc.nbs.questionbank.tab;

import gov.cdc.nbs.questionbank.tab.exceptions.AddTabException;
import gov.cdc.nbs.questionbank.tab.model.CreateTabRequest;
import gov.cdc.nbs.questionbank.tab.model.CreateTabResponse;
import gov.cdc.nbs.questionbank.tab.repository.WaUiMetaDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AddTabServiceTest {

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
    void createTabServiceTestException() throws AddTabException {

        AddTabException exception =
                assertThrows(AddTabException.class, () -> tabService.createTab(123L, null));

        assertEquals(
                "gov.cdc.nbs.questionbank.addtab.exceptions.AddTabException: Add tab exception",
                exception.toString());
    }
}
