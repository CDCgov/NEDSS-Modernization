package gov.cdc.nbs.questionbank.addsection;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.addsection.controller.AddSectionController;
import gov.cdc.nbs.questionbank.addsection.exception.AddSectionException;
import gov.cdc.nbs.questionbank.addsection.model.CreateSectionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AddSectionControllerTest {

    @Mock
    private CreateSectionService createSectionService;

    @Mock
    private UserDetailsProvider userDetailsProvider;

    @Test
    void createSectionTest() throws AddSectionException {

        AddSectionController addSectionController = new AddSectionController(createSectionService,
                userDetailsProvider);

        CreateSectionRequest createSectionRequest = new CreateSectionRequest(1000376L, "Local", "T");
        NbsUserDetails nbsUserDetails =
                NbsUserDetails.builder().id(123L).firstName("test user").lastName("test").build();
        Mockito.when(createSectionService.createSection(123L, createSectionRequest))
                .thenReturn(new CreateSectionResponse(123L, "Add Section Created Successfully"));

        Mockito.when(userDetailsProvider.getCurrentUserDetails()).thenReturn(nbsUserDetails);

        CreateSectionResponse createSectionResponse = addSectionController.createSection(createSectionRequest);
        assertEquals(123L, createSectionResponse.uid());
    }
}
