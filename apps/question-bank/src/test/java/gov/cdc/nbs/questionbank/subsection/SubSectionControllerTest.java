package gov.cdc.nbs.questionbank.subsection;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.section.controller.SectionController;
import gov.cdc.nbs.questionbank.section.model.DeleteSectionRequest;
import gov.cdc.nbs.questionbank.section.model.DeleteSectionResponse;
import gov.cdc.nbs.questionbank.section.model.UpdateSectionRequest;
import gov.cdc.nbs.questionbank.section.model.UpdateSectionResponse;
import gov.cdc.nbs.questionbank.subsection.controller.SubSectionController;
import gov.cdc.nbs.questionbank.subsection.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SubSectionControllerTest {

    @Mock
    private SubSectionService createSubSectionService;

    @Mock
    private UserDetailsProvider userDetailsProvider;

    @Test
    void createSubSectionTest() {

        SubSectionController addSubSectionController = new SubSectionController(createSubSectionService,
                userDetailsProvider);

        CreateSubSectionRequest createSubSectionRequest = new CreateSubSectionRequest(1000L, 1000376L, "Local", "T");
        NbsUserDetails nbsUserDetails =
                NbsUserDetails.builder().id(123L).firstName("test user").lastName("test").build();
        Mockito.when(createSubSectionService.createSubSection(123L, createSubSectionRequest))
                .thenReturn(new CreateSubSectionResponse(123L, "Add SubSection Created Successfully"));

        Mockito.when(userDetailsProvider.getCurrentUserDetails()).thenReturn(nbsUserDetails);

        CreateSubSectionResponse createSubSectionResponse = addSubSectionController.createSubSection(createSubSectionRequest);
        assertEquals(123L, createSubSectionResponse.uid());
    }


    @Test
    void updateSubSectionTest() {

        SubSectionController subSectionController = new SubSectionController(createSubSectionService,
                userDetailsProvider);

        UpdateSubSectionRequest updateSubSectionRequest = new UpdateSubSectionRequest(123L, "Question Label", "T");
        Mockito.when(createSubSectionService.updateSubSection( updateSubSectionRequest))
                .thenReturn(new UpdateSubSectionResponse(123L, "SubSection Updated Successfully"));

        UpdateSubSectionResponse updateSubSectionResponse = subSectionController.updateSubSection(updateSubSectionRequest);
        assertEquals(123L, updateSubSectionResponse.uid());
    }


    @Test
    void deleteSubSectionTest() {

        SubSectionController subSectionController = new SubSectionController(createSubSectionService,
                userDetailsProvider);

        DeleteSubSectionRequest deleteSubSectionRequest = new DeleteSubSectionRequest(123L);
        Mockito.when(createSubSectionService.deleteSubSection( deleteSubSectionRequest))
                .thenReturn(new DeleteSubSectionResponse(123L, "Sub Section Deleted Successfully"));

        DeleteSubSectionResponse deleteSubSectionResponse = subSectionController.deleteSubSection(deleteSubSectionRequest);
        assertEquals(123L, deleteSubSectionResponse.uid());
    }
}

