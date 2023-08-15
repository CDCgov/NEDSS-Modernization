package gov.cdc.nbs.questionbank.tab;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.subsection.controller.SubSectionController;
import gov.cdc.nbs.questionbank.subsection.model.DeleteSubSectionRequest;
import gov.cdc.nbs.questionbank.subsection.model.DeleteSubSectionResponse;
import gov.cdc.nbs.questionbank.subsection.model.UpdateSubSectionRequest;
import gov.cdc.nbs.questionbank.subsection.model.UpdateSubSectionResponse;
import gov.cdc.nbs.questionbank.tab.controller.TabController;
import gov.cdc.nbs.questionbank.tab.exceptions.AddTabException;
import gov.cdc.nbs.questionbank.tab.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TabControllerTest {

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

    @Test
    void updateTabTest() {

        TabController tabController = new TabController(tabService,
                userDetailsProvider);

        UpdateTabRequest updateTabRequest = new UpdateTabRequest(123L, "Question Label", "T");
        Mockito.when(tabService.updateTab( updateTabRequest))
                .thenReturn(new UpdateTabResponse(123L, "Tab Updated Successfully"));

        UpdateTabResponse updateTabResponse = tabController.updateTab(updateTabRequest);
        assertEquals(123L, updateTabResponse.uid());
    }


    @Test
    void deleteTabTest() {

        TabController tabController = new TabController(tabService,
                userDetailsProvider);

        DeleteTabRequest deleteTabRequest = new DeleteTabRequest(123L);
        Mockito.when(tabService.deleteTab( deleteTabRequest))
                .thenReturn(new DeleteTabResponse(123L, "TabDeleted Successfully"));

        DeleteTabResponse deleteTabResponse = tabController.deleteTab(deleteTabRequest);
        assertEquals(123L, deleteTabResponse.uid());
    }
}


