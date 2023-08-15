package gov.cdc.nbs.questionbank.subsection;
import gov.cdc.nbs.questionbank.subsection.exception.AddSubSectionException;
import gov.cdc.nbs.questionbank.subsection.exception.DeleteSubSectionException;
import gov.cdc.nbs.questionbank.subsection.exception.UpdateSubSectionException;
import gov.cdc.nbs.questionbank.subsection.model.*;
import gov.cdc.nbs.questionbank.tab.repository.WaUiMetaDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class SubSectionServiceTest {

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
    void updateSubSectionServiceTest() {

        UpdateSubSectionRequest updateSubSectionRequest =
                new UpdateSubSectionRequest(123L,  "Local", "T");

        UpdateSubSectionResponse updateSubSectionResponse =
                createSubSectionService.updateSubSection( updateSubSectionRequest);
        assertEquals("Sub Section Updated Successfully", updateSubSectionResponse.message());
    }

    @Test
    void updateSubSectionServiceOnlyQuestionLabelTest() {

        UpdateSubSectionRequest updateSubSectionRequest =
                new UpdateSubSectionRequest(123L,  "Local", null);

        UpdateSubSectionResponse updateSubSectionResponse =
                createSubSectionService.updateSubSection( updateSubSectionRequest);
        assertEquals("Sub Section Updated Successfully", updateSubSectionResponse.message());
    }

    @Test
    void updateSubSectionServiceOnlyVisibilityTest() {

        UpdateSubSectionRequest updateSubSectionRequest =
                new UpdateSubSectionRequest(123L,  null, "T");

        UpdateSubSectionResponse updateSubSectionResponse =
                createSubSectionService.updateSubSection( updateSubSectionRequest);
        assertEquals("Sub Section Updated Successfully", updateSubSectionResponse.message());
    }

    @Test
    void updateSubSectionServiceNoLabelOrVisibilityTest() {

        UpdateSubSectionRequest updateSubSectionRequest =
                new UpdateSubSectionRequest(123L,  null, null);

        UpdateSubSectionResponse updateSubSectionResponse =
                createSubSectionService.updateSubSection( updateSubSectionRequest);
        assertEquals("questionLabel or Visible is required to update sub section", updateSubSectionResponse.message());
    }

    @Test
    void deleteSubSectionTest() {

        DeleteSubSectionRequest deleteSubSectionRequest =
                new DeleteSubSectionRequest(123L);

        DeleteSubSectionResponse deleteSubSectionResponse =
                createSubSectionService.deleteSubSection( deleteSubSectionRequest);
        assertEquals("Sub Section Deleted Successfully", deleteSubSectionResponse.message());
    }

    @Test
    void updateSubSectionServiceTestException() {
        assertThrows(UpdateSubSectionException.class, () -> createSubSectionService.updateSubSection(null));

    }
    @Test
    void deleteSubSectionServiceTestException() {
        assertThrows(DeleteSubSectionException.class, () -> createSubSectionService.deleteSubSection( null));

    }

    @Test
    void createSubSectionServiceTestException() {
        assertThrows(AddSubSectionException.class, () -> createSubSectionService.createSubSection(123L, null));

    }
}
