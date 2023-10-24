package gov.cdc.nbs.questionbank.page.content.addsection;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.page.content.section.SectionController;
import gov.cdc.nbs.questionbank.page.content.section.SectionCreator;
import gov.cdc.nbs.questionbank.page.content.section.SectionDeleter;
import gov.cdc.nbs.questionbank.page.content.section.SectionUpdater;
import gov.cdc.nbs.questionbank.page.content.section.request.CreateSectionRequest;
import gov.cdc.nbs.questionbank.page.content.section.request.UpdateSectionRequest;
import gov.cdc.nbs.questionbank.page.content.section.response.CreateSectionResponse;

@ExtendWith(MockitoExtension.class)
class AddSectionControllerTest {

    @Mock
    private SectionCreator createSectionService;

    @Mock
    private SectionUpdater sectionUpdater;

    @Mock
    private SectionDeleter sectionDeleter;

    @Mock
    private UserDetailsProvider userDetailsProvider;

    @InjectMocks
    private SectionController sectionController;

    @Test
    void createSectionTest() {

        CreateSectionRequest createSectionRequest = new CreateSectionRequest(1000L, "Local", true);
        NbsUserDetails nbsUserDetails =
                NbsUserDetails.builder().id(123L).firstName("test user").lastName("test").build();
        Mockito.when(createSectionService.createSection(1000376L, 123L, createSectionRequest))
                .thenReturn(new CreateSectionResponse(123L, "Add Section Created Successfully"));

        Mockito.when(userDetailsProvider.getCurrentUserDetails()).thenReturn(nbsUserDetails);

        CreateSectionResponse createSectionResponse =
                sectionController.createSection(1000376L, createSectionRequest);
        assertEquals(123L, createSectionResponse.uid());
    }

    @Test
    void updateSectionTest() {
        UpdateSectionRequest request = new UpdateSectionRequest("test", false);
        when(userDetailsProvider.getCurrentUserDetails()).thenReturn(userDetails());
        sectionController.updateSection(1l, 2l, request);
        verify(sectionUpdater).update(1l, 2L, 3l, request);
    }

    @Test
    void deleteSectionTest() {
        sectionController.deleteSection(100L, 123L);
        verify(sectionDeleter).deleteSection(100l, 123L);
    }

    private NbsUserDetails userDetails() {
        return NbsUserDetails.builder().id(3l).build();
    }
}
