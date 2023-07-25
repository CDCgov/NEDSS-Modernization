package gov.cdc.nbs.questionbank.addtab;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.addtab.controller.AddTabController;
import gov.cdc.nbs.questionbank.addtab.exceptions.AddTabException;
import gov.cdc.nbs.questionbank.addtab.model.CreateTabRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AddTabControllerTest {

    @Mock
    private CreateTabService createTabService;

    @Mock
    private UserDetailsProvider userDetailsProvider;

    @Test
    void createTabTest() throws AddTabException {

        AddTabController addTabController = new AddTabController(createTabService,
                userDetailsProvider);

        CreateTabRequest createTabRequest = new CreateTabRequest(1000376L, "Local", "T");
        NbsUserDetails nbsUserDetails =
                NbsUserDetails.builder().id(123L).firstName("test user").lastName("test").build();
        Mockito.when(createTabService.createTab(123L, createTabRequest))
                .thenReturn(new CreateUiResponse(123L, "Add Tab Created Successfully"));

        Mockito.when(userDetailsProvider.getCurrentUserDetails()).thenReturn(nbsUserDetails);

        CreateUiResponse createUiResponse = addTabController.createTab(createTabRequest);
        assertEquals(123L, createUiResponse.uid());
    }
}
