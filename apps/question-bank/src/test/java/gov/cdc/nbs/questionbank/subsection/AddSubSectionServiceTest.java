package gov.cdc.nbs.questionbank.subsection;

import gov.cdc.nbs.questionbank.subsection.exception.AddSubSectionException;
import gov.cdc.nbs.questionbank.subsection.model.CreateSubSectionResponse;
import gov.cdc.nbs.questionbank.subsection.model.CreateSubSectionRequest;
import gov.cdc.nbs.questionbank.addtab.repository.WaUiMetaDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AddSubSectionServiceTest {

    @InjectMocks
    private SubSectionService createSubSectionService;

    @Mock
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @Test
    void createSubSectionServiceTest() {

        CreateSubSectionRequest createSubSectionRequest =
                new CreateSubSectionRequest(10L, 10L, "Local", "T");

        CreateSubSectionResponse createSubSectionResponse =
                createSubSectionService.createSubSection(123L, createSubSectionRequest);
        assertEquals("SubSection Created Successfully", createSubSectionResponse.message());
    }

    @Test
    void createSectionServiceTestException() {
        assertThrows(AddSubSectionException.class, () -> createSubSectionService.createSubSection(123L, null));

    }
}
