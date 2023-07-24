package gov.cdc.nbs.questionbank.addtab;

import gov.cdc.nbs.questionbank.addtab.exceptions.AddTabException;
import gov.cdc.nbs.questionbank.addtab.model.CreateTabRequest;
import gov.cdc.nbs.questionbank.addtab.repository.WaUiMetaDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringJUnit4ClassRunner.class)
public class AddTabServiceTest {

    @InjectMocks
    private CreateTabService createTabService;

    @Mock
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @Test
    public void createTabServiceTest() throws AddTabException {

        CreateTabRequest createTabRequest = new CreateTabRequest(10L, "Local", "T");

        CreateUiResponse createUiResponse = createTabService.createTab(123L, createTabRequest);
        assertEquals("Tab Created Successfully", createUiResponse.message());
    }

    @Test
    public void createTabServiceTestException() throws AddTabException {

        AddTabException exception =
                assertThrows(AddTabException.class, () -> createTabService.createTab(123L, null));

        assertEquals(
                "gov.cdc.nbs.questionbank.addtab.exceptions.AddTabException: Add tab exception",
                exception.toString());
    }
}
