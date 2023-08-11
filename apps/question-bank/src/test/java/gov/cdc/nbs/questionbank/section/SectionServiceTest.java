package gov.cdc.nbs.questionbank.section;

import gov.cdc.nbs.questionbank.section.exception.AddSectionException;
import gov.cdc.nbs.questionbank.section.exception.DeleteSectionException;
import gov.cdc.nbs.questionbank.section.exception.UpdateSectionException;
import gov.cdc.nbs.questionbank.section.model.*;
import gov.cdc.nbs.questionbank.addtab.repository.WaUiMetaDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class SectionServiceTest {

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
    void updateSectionServiceTest() {

        UpdateSectionRequest updateSectionRequest =
                new UpdateSectionRequest(123L,  "Local", "T");

        UpdateSectionResponse updateSectionResponse =
                createSectionService.updateSection( updateSectionRequest);
        assertEquals("Section Updated Successfully", updateSectionResponse.message());
    }

    @Test
    void updateSectionServiceOnlyQuestionLabelTest() {

        UpdateSectionRequest updateSectionRequest =
                new UpdateSectionRequest(123L,  "Local", null);

        UpdateSectionResponse updateSectionResponse =
                createSectionService.updateSection( updateSectionRequest);
        assertEquals("Section Updated Successfully", updateSectionResponse.message());
    }

    @Test
    void updateSectionServiceOnlyVisibilityTest() {

        UpdateSectionRequest updateSectionRequest =
                new UpdateSectionRequest(123L,  null, "T");

        UpdateSectionResponse updateSectionResponse =
                createSectionService.updateSection( updateSectionRequest);
        assertEquals("Section Updated Successfully", updateSectionResponse.message());
    }

    @Test
    void updateSectionServiceNoLabelOrVisibilityTest() {

        UpdateSectionRequest updateSectionRequest =
                new UpdateSectionRequest(123L,  null, null);

        UpdateSectionResponse updateSectionResponse =
                createSectionService.updateSection( updateSectionRequest);
        assertEquals("questionLabel or Visible is required to update section", updateSectionResponse.message());
    }

    @Test
    void deleteSectionTest() {

        DeleteSectionRequest deleteSectionRequest =
                new DeleteSectionRequest(123L);

        DeleteSectionResponse deleteSectionResponse =
                createSectionService.deleteSection( deleteSectionRequest);
        assertEquals("Section Deleted Successfully", deleteSectionResponse.message());
    }

    @Test
    void createSectionServiceTestException() {
        assertThrows(AddSectionException.class, () -> createSectionService.createSection(123L, null));

    }

    @Test
    void updateSectionServiceTestException() {
        assertThrows(UpdateSectionException.class, () -> createSectionService.updateSection(null));

    }
    @Test
    void deleteSectionServiceTestException() {
        assertThrows(DeleteSectionException.class, () -> createSectionService.deleteSection( null));

    }
}
