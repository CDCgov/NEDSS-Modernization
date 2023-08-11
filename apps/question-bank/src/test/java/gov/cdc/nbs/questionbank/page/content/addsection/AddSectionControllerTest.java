package gov.cdc.nbs.questionbank.page.content.addsection;


import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.page.content.section.SectionController;
import gov.cdc.nbs.questionbank.page.content.section.SectionCreator;
import gov.cdc.nbs.questionbank.page.content.section.request.CreateSectionRequest;
import gov.cdc.nbs.questionbank.page.content.section.response.CreateSectionResponse;
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
}
