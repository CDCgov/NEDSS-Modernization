package gov.cdc.nbs.questionbank.addtab;

import gov.cdc.nbs.questionbank.addtab.exceptions.AddTabException;
import gov.cdc.nbs.questionbank.addtab.model.CreateTabRequest;
import gov.cdc.nbs.questionbank.addtab.repository.WaUiMetaDataRepository;
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
    private CreateTabService createTabService;

    @Mock
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @Test
    void createTabServiceTest() throws AddTabException {

        CreateTabRequest createTabRequest =
                new CreateTabRequest(10L, "Local", "T");

        CreateUiResponse createUiResponse =
                createTabService.createTab(123L, createTabRequest);
        assertEquals("Tab Created Successfully", createUiResponse.message());
    }

    @Test
    void createTabServiceTestException() throws AddTabException {

        AddTabException exception =
                assertThrows(AddTabException.class, () -> createTabService.createTab(123L, null));

        assertEquals(
                "gov.cdc.nbs.questionbank.addtab.exceptions.AddTabException: Add tab exception",
                exception.toString());
    }
}
