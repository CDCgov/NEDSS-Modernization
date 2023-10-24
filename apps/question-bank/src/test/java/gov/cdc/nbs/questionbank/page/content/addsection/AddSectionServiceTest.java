package gov.cdc.nbs.questionbank.page.content.addsection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.page.content.section.SectionCreator;
import gov.cdc.nbs.questionbank.page.content.section.exception.AddSectionException;
import gov.cdc.nbs.questionbank.page.content.section.request.CreateSectionRequest;
import gov.cdc.nbs.questionbank.page.content.section.response.CreateSectionResponse;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;

@ExtendWith(MockitoExtension.class)
class AddSectionServiceTest {

    @InjectMocks
    private SectionCreator createSectionService;

    @Mock
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @Mock
    private EntityManager entityManager;

    @Test
    void createSectionServiceTest() {

        CreateSectionRequest createSectionRequest =
                new CreateSectionRequest(10L, "Local", true);

        CreateSectionResponse createSectionResponse =
                createSectionService.createSection(10L, 123L, createSectionRequest);
        assertEquals("Section Created Successfully", createSectionResponse.message());
    }

    @Test
    void createSectionServiceTestException() {
        assertThrows(AddSectionException.class, () -> createSectionService.createSection(10L, 123L, null));

    }
}
