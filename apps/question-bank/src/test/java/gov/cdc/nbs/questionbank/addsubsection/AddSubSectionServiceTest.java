package gov.cdc.nbs.questionbank.addsubsection;

import gov.cdc.nbs.questionbank.addsubsection.CreateSubSectionResponse;
import gov.cdc.nbs.questionbank.addsubsection.CreateSubSectionService;
import gov.cdc.nbs.questionbank.addsubsection.exception.AddSubSectionException;
import gov.cdc.nbs.questionbank.addsubsection.model.CreateSubSectionRequest;
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
    private CreateSubSectionService createSubSectionService;

    @Mock
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @Test
    void createSubSectionServiceTest() throws AddSubSectionException {

        CreateSubSectionRequest createSubSectionRequest =
                new CreateSubSectionRequest(10L, 10L, "Local", "T");

        CreateSubSectionResponse createSubSectionResponse =
                createSubSectionService.createSubSection(123L, createSubSectionRequest);
        assertEquals("SubSection Created Successfully", createSubSectionResponse.message());
    }

    @Test
    void createSectionServiceTestException() throws AddSubSectionException {

        AddSubSectionException exception =
                assertThrows(AddSubSectionException.class, () -> createSubSectionService.createSubSection(123L, null));

        assertEquals(
                "gov.cdc.nbs.questionbank.addsubsection.exception.AddSubSectionException: Add SubSection exception",
                exception.toString());
    }
}
