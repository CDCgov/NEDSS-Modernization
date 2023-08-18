package gov.cdc.nbs.questionbank.page.content.addsection;

import gov.cdc.nbs.questionbank.page.content.section.SectionCreator;
import gov.cdc.nbs.questionbank.page.content.section.exception.AddSectionException;
import gov.cdc.nbs.questionbank.page.content.section.exception.DeleteSectionException;
import gov.cdc.nbs.questionbank.page.content.section.exception.UpdateSectionException;
import gov.cdc.nbs.questionbank.page.content.section.request.CreateSectionRequest;
import gov.cdc.nbs.questionbank.page.content.section.request.DeleteSectionRequest;
import gov.cdc.nbs.questionbank.page.content.section.request.UpdateSectionRequest;
import gov.cdc.nbs.questionbank.page.content.section.response.CreateSectionResponse;
import gov.cdc.nbs.questionbank.page.content.section.response.DeleteSectionResponse;
import gov.cdc.nbs.questionbank.page.content.section.response.UpdateSectionResponse;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import javax.persistence.EntityManager;

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
        assertThrows(AddSectionException.class, () -> createSectionService.createSection(10L, 123L, null));

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
