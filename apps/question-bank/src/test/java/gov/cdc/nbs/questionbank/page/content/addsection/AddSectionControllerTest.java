package gov.cdc.nbs.questionbank.page.content.addsection;


import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.page.content.section.SectionController;
import gov.cdc.nbs.questionbank.page.content.section.SectionCreator;
import gov.cdc.nbs.questionbank.page.content.section.request.CreateSectionRequest;
import gov.cdc.nbs.questionbank.page.content.section.request.DeleteSectionRequest;
import gov.cdc.nbs.questionbank.page.content.section.request.OrderSectionRequest;
import gov.cdc.nbs.questionbank.page.content.section.request.UpdateSectionRequest;
import gov.cdc.nbs.questionbank.page.content.section.response.CreateSectionResponse;
import gov.cdc.nbs.questionbank.page.content.section.response.DeleteSectionResponse;
import gov.cdc.nbs.questionbank.page.content.section.response.OrderSectionResponse;
import gov.cdc.nbs.questionbank.page.content.section.response.UpdateSectionResponse;
import gov.cdc.nbs.questionbank.page.content.tab.TabController;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.OrderTabException;
import gov.cdc.nbs.questionbank.page.content.tab.request.OrderTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.OrderTabResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AddSectionControllerTest {

    @Mock
    private SectionCreator createSectionService;

    @Mock
    private UserDetailsProvider userDetailsProvider;

    @Test
    void createSectionTest() {

        SectionController addSectionController = new SectionController(createSectionService,
                userDetailsProvider);

        CreateSectionRequest createSectionRequest = new CreateSectionRequest(1000L, "Local", true);
        NbsUserDetails nbsUserDetails =
                NbsUserDetails.builder().id(123L).firstName("test user").lastName("test").build();
        Mockito.when(createSectionService.createSection(1000376L, 123L, createSectionRequest))
                .thenReturn(new CreateSectionResponse(123L, "Add Section Created Successfully"));

        Mockito.when(userDetailsProvider.getCurrentUserDetails()).thenReturn(nbsUserDetails);

        CreateSectionResponse createSectionResponse =
                addSectionController.createSection(1000376L, createSectionRequest);
        assertEquals(123L, createSectionResponse.uid());
    }

    @Test
    void updateSectionTest() {

        SectionController addsectionController = new SectionController(createSectionService,
                userDetailsProvider);

        UpdateSectionRequest updateSectionRequest = new UpdateSectionRequest(123L, "Question Label", "T");
        Mockito.when(createSectionService.updateSection( updateSectionRequest))
                .thenReturn(new UpdateSectionResponse(123L, "Section Updated Successfully"));

        UpdateSectionResponse updateSectionResponse = addsectionController.updateSection(updateSectionRequest);
        assertEquals(123L, updateSectionResponse.uid());
    }


    @Test
    void deleteSectionTest() {

        SectionController addsectionController = new SectionController(createSectionService,
                userDetailsProvider);

        DeleteSectionRequest deleteSectionRequest = new DeleteSectionRequest(123L);
        Mockito.when(createSectionService.deleteSection( deleteSectionRequest))
                .thenReturn(new DeleteSectionResponse(123L, "Section Deleted Successfully"));

        DeleteSectionResponse deleteSectionResponse = addsectionController.deleteSection(deleteSectionRequest);
        assertEquals(123L, deleteSectionResponse.uid());
    }

    @Test
    void orderSectionTest() throws OrderTabException {

        SectionController addsectionController = new SectionController(createSectionService,
                userDetailsProvider);

        OrderSectionRequest orderSectionRequest = new OrderSectionRequest(123L, 2,1, 3);

        Mockito.when(createSectionService.orderSection(100L, orderSectionRequest))
                .thenReturn(new OrderSectionResponse(123L, "The section is moved from 1 to 3 successfully"));

        OrderSectionResponse orderSectionResponse = addsectionController.orderSection(100L, orderSectionRequest);
        assertEquals(123L, orderSectionResponse.uid());
    }
}
