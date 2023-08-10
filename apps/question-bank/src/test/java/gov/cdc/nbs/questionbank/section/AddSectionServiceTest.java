package gov.cdc.nbs.questionbank.section;

import gov.cdc.nbs.questionbank.section.exception.AddSectionException;
import gov.cdc.nbs.questionbank.section.model.CreateSectionRequest;
import gov.cdc.nbs.questionbank.addtab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.section.model.CreateSectionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AddSectionServiceTest {

    @InjectMocks
    private SectionService createSectionService;

    @Mock
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @Test
    void createSectionServiceTest() {

        CreateSectionRequest createSectionRequest =
                new CreateSectionRequest(10L, 10L, "Local", "T");

        CreateSectionResponse createSectionResponse =
                createSectionService.createSection(123L, createSectionRequest);
        assertEquals("Section Created Successfully", createSectionResponse.message());
    }

    @Test
    void createSectionServiceTestException() {
        assertThrows(AddSectionException.class, () -> createSectionService.createSection(123L, null));

    }
}
