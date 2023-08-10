package gov.cdc.nbs.questionbank.page.content.addtab;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.page.content.tab.TabCreator;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.AddTabException;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.page.content.tab.request.CreateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.CreateTabResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import javax.persistence.EntityManager;

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
    void createTabServiceTestException() throws AddTabException {
        assertThrows(AddTabException.class, () -> createTabService.createTab(10L, 123L, null));
    }
}
