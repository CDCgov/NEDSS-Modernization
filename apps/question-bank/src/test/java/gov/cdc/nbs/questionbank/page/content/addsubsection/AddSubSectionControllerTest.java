package gov.cdc.nbs.questionbank.page.content.addsubsection;


import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.page.content.subsection.SubSectionController;
import gov.cdc.nbs.questionbank.page.content.subsection.request.CreateSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.request.DeleteSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.request.UpdateSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.response.CreateSubSectionResponse;
import gov.cdc.nbs.questionbank.page.content.subsection.SubSectionCreator;
import gov.cdc.nbs.questionbank.page.content.subsection.response.DeleteSubSectionResponse;
import gov.cdc.nbs.questionbank.page.content.subsection.response.UpdateSubSectionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AddSubSectionControllerTest {

    @Mock
    private SubSectionCreator createSubSectionService;

    @Mock
    private UserDetailsProvider userDetailsProvider;

    @Test
    void createSubSectionTest() {

        SubSectionController addSubSectionController = new SubSectionController(createSubSectionService,
                userDetailsProvider);

        CreateSubSectionRequest createSubSectionRequest = new CreateSubSectionRequest(1000376L, "Local", true);
        NbsUserDetails nbsUserDetails =
                NbsUserDetails.builder().id(123L).firstName("test user").lastName("test").build();
        Mockito.when(createSubSectionService.createSubSection(1000L, 123L, createSubSectionRequest))
                .thenReturn(new CreateSubSectionResponse(123L, "Add SubSection Created Successfully"));

        Mockito.when(userDetailsProvider.getCurrentUserDetails()).thenReturn(nbsUserDetails);

        CreateSubSectionResponse createSubSectionResponse =
                addSubSectionController.createSubSection(1000L, createSubSectionRequest);
        assertEquals(123L, createSubSectionResponse.uid());
    }

    @Test
    void updateSubSectionTest() {

        SubSectionController addsubSectionController = new SubSectionController(createSubSectionService,
                userDetailsProvider);

        UpdateSubSectionRequest updateSubSectionRequest = new UpdateSubSectionRequest(123L, "Question Label", "T");
        Mockito.when(createSubSectionService.updateSubSection( updateSubSectionRequest))
                .thenReturn(new UpdateSubSectionResponse(123L, "SubSection Updated Successfully"));

        UpdateSubSectionResponse updateSubSectionResponse = addsubSectionController.updateSubSection(updateSubSectionRequest);
        assertEquals(123L, updateSubSectionResponse.uid());
    }


    @Test
    void deleteSubSectionTest() {

        SubSectionController addsubSectionController = new SubSectionController(createSubSectionService,
                userDetailsProvider);

        DeleteSubSectionRequest deleteSubSectionRequest = new DeleteSubSectionRequest(123L);
        Mockito.when(createSubSectionService.deleteSubSection( deleteSubSectionRequest))
                .thenReturn(new DeleteSubSectionResponse(123L, "Sub Section Deleted Successfully"));

        DeleteSubSectionResponse deleteSubSectionResponse = addsubSectionController.deleteSubSection(deleteSubSectionRequest);
        assertEquals(123L, deleteSubSectionResponse.uid());
    }
}
