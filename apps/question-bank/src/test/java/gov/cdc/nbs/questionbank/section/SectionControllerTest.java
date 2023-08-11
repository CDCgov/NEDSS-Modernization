package gov.cdc.nbs.questionbank.section;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.section.controller.SectionController;
import gov.cdc.nbs.questionbank.section.model.*;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SectionControllerTest {

    @Mock
    private SectionService createSectionService;

    @Mock
    private UserDetailsProvider userDetailsProvider;

    @Test
    void createSectionTest() {

        SectionController addSectionController = new SectionController(createSectionService,
                userDetailsProvider);

        CreateSectionRequest createSectionRequest = new CreateSectionRequest(1000L, 1000376L, "Local", "T");
        NbsUserDetails nbsUserDetails =
                NbsUserDetails.builder().id(123L).firstName("test user").lastName("test").build();
        Mockito.when(createSectionService.createSection(123L, createSectionRequest))
                .thenReturn(new CreateSectionResponse(123L, "Add Section Created Successfully"));

        Mockito.when(userDetailsProvider.getCurrentUserDetails()).thenReturn(nbsUserDetails);

        CreateSectionResponse createSectionResponse = addSectionController.createSection(createSectionRequest);
        assertEquals(123L, createSectionResponse.uid());
    }

    @Test
    void updateSectionTest() {

        SectionController sectionController = new SectionController(createSectionService,
                userDetailsProvider);

        UpdateSectionRequest updateSectionRequest = new UpdateSectionRequest(123L, "Question Label", "T");
        Mockito.when(createSectionService.updateSection( updateSectionRequest))
                .thenReturn(new UpdateSectionResponse(123L, "Section Updated Successfully"));

        UpdateSectionResponse updateSectionResponse = sectionController.updateSection(updateSectionRequest);
        assertEquals(123L, updateSectionResponse.uid());
    }


    @Test
    void deleteSectionTest() {

        SectionController sectionController = new SectionController(createSectionService,
                userDetailsProvider);

        DeleteSectionRequest deleteSectionRequest = new DeleteSectionRequest(123L);
        Mockito.when(createSectionService.deleteSection( deleteSectionRequest))
                .thenReturn(new DeleteSectionResponse(123L, "Section Deleted Successfully"));

        DeleteSectionResponse deleteSectionResponse = sectionController.deleteSection(deleteSectionRequest);
        assertEquals(123L, deleteSectionResponse.uid());
    }
}
