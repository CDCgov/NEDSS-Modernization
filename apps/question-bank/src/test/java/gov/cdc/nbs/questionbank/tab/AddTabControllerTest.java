package gov.cdc.nbs.questionbank.tab;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.tab.controller.TabController;
import gov.cdc.nbs.questionbank.tab.exceptions.AddTabException;
import gov.cdc.nbs.questionbank.tab.model.CreateTabRequest;
import gov.cdc.nbs.questionbank.tab.model.CreateTabResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AddTabControllerTest {

    @Mock
    private TabService tabService;

    @Mock
    private UserDetailsProvider userDetailsProvider;

    @Test
    void createTabTest() throws AddTabException {

        TabController addTabController = new TabController(tabService,
                userDetailsProvider);

        CreateTabRequest createTabRequest = new CreateTabRequest(1000376L, "Local", "T");
        NbsUserDetails nbsUserDetails =
                NbsUserDetails.builder().id(123L).firstName("test user").lastName("test").build();
        Mockito.when(tabService.createTab(123L, createTabRequest))
                .thenReturn(new CreateTabResponse(123L, "Add Tab Created Successfully"));

        Mockito.when(userDetailsProvider.getCurrentUserDetails()).thenReturn(nbsUserDetails);

        CreateTabResponse createTabResponse = addTabController.createTab(createTabRequest);
        assertEquals(123L, createTabResponse.uid());
    }
}
