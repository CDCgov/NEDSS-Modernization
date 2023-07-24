package gov.cdc.nbs.questionbank.addtab;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.addtab.controller.AddTabController;
import gov.cdc.nbs.questionbank.addtab.exceptions.AddTabException;
import gov.cdc.nbs.questionbank.addtab.model.CreateTabRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class AddTabControllerTest {

    @InjectMocks
    AddTabController addTabController;

    @Mock
    private CreateTabService createTabService;

    @Mock
    private UserDetailsProvider userDetailsProvider;

    @Test
    public void createTabTest() throws AddTabException {

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
