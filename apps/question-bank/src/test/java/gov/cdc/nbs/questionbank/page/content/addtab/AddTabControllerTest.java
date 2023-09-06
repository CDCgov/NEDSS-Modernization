package gov.cdc.nbs.questionbank.page.content.addtab;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.page.content.tab.TabController;
import gov.cdc.nbs.questionbank.page.content.tab.TabCreator;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.AddTabException;
import gov.cdc.nbs.questionbank.page.content.tab.request.CreateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.request.DeleteTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.request.UpdateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.CreateTabResponse;
import gov.cdc.nbs.questionbank.page.content.tab.response.DeleteTabResponse;
import gov.cdc.nbs.questionbank.page.content.tab.response.UpdateTabResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AddTabControllerTest {

    @Mock
    private TabCreator createTabService;

    @Mock
    private UserDetailsProvider userDetailsProvider;

    @Test
    void createTabTest() throws AddTabException {

        TabController addTabController = new TabController(createTabService,
                userDetailsProvider);

        CreateTabRequest createTabRequest = new CreateTabRequest("Local", true);
        NbsUserDetails nbsUserDetails =
                NbsUserDetails.builder().id(123L).firstName("test user").lastName("test").build();
        Mockito.when(createTabService.createTab(1000376L, 123L, createTabRequest))
                .thenReturn(new CreateTabResponse(123L, "Add Tab Created Successfully"));

        Mockito.when(userDetailsProvider.getCurrentUserDetails()).thenReturn(nbsUserDetails);

        CreateTabResponse createUiResponse = addTabController.createTab(1000376L, createTabRequest);
        assertEquals(123L, createUiResponse.uid());
    }

    @Test
    void updateTabTest() {

        TabController tabController = new TabController(createTabService,
                userDetailsProvider);

        UpdateTabRequest updateTabRequest = new UpdateTabRequest(123L, "Question Label", "T");
        Mockito.when(createTabService.updateTab(updateTabRequest))
                .thenReturn(new UpdateTabResponse(123L, "Tab Updated Successfully"));

        UpdateTabResponse updateTabResponse = tabController.updateTab(updateTabRequest);
        assertEquals(123L, updateTabResponse.uid());
    }


    @Test
    void deleteTabTest() {

        TabController tabController = new TabController(createTabService,
                userDetailsProvider);

        DeleteTabRequest deleteTabRequest = new DeleteTabRequest(123L);
        Mockito.when(createTabService.deleteTab(100L, deleteTabRequest))
                .thenReturn(new DeleteTabResponse(123L, "TabDeleted Successfully"));

        DeleteTabResponse deleteTabResponse = tabController.deleteTab(100L, deleteTabRequest);
        assertEquals(123L, deleteTabResponse.uid());
    }
}
