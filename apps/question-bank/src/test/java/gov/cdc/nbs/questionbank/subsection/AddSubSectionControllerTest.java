package gov.cdc.nbs.questionbank.subsection;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.subsection.controller.SubSectionController;
import gov.cdc.nbs.questionbank.subsection.model.CreateSubSectionResponse;
import gov.cdc.nbs.questionbank.subsection.model.CreateSubSectionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AddSubSectionControllerTest {

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
}
