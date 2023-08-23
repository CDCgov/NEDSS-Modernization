package gov.cdc.nbs.questionbank.page.content.addsubsection;

import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.subsection.SubSectionCreator;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.AddSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.DeleteSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.UpdateSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.request.CreateSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.request.DeleteSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.request.UpdateSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.response.CreateSubSectionResponse;
import gov.cdc.nbs.questionbank.page.content.subsection.response.DeleteSubSectionResponse;
import gov.cdc.nbs.questionbank.page.content.subsection.response.UpdateSubSectionResponse;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.Optional;
import javax.persistence.EntityManager;

@ExtendWith(MockitoExtension.class)
class AddSubSectionServiceTest {

    @InjectMocks
    private SubSectionCreator createSubSectionService;

    @Mock
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @Mock
    private EntityManager entityManager;

    @Test
    void createSubSectionServiceTest() {
        WaUiMetadata section = new WaUiMetadata();
        section.setOrderNbr(1);
        when(waUiMetaDataRepository.findById(10L)).thenReturn(Optional.of(section));
        CreateSubSectionRequest createSubSectionRequest = new CreateSubSectionRequest(10L, "Local", true);

        CreateSubSectionResponse createSubSectionResponse =
                createSubSectionService.createSubSection(10L, 123L, createSubSectionRequest);
        assertEquals("SubSection Created Successfully", createSubSectionResponse.message());
    }


    @Test
    void updateSubSectionServiceTest() {

        UpdateSubSectionRequest updateSubSectionRequest =
                new UpdateSubSectionRequest(123L,  "Local", "T");

        UpdateSubSectionResponse updateSubSectionResponse =
                createSubSectionService.updateSubSection( updateSubSectionRequest);
        assertEquals("SubSection Updated Successfully", updateSubSectionResponse.message());
    }


    @Test
    void updateSubSectionServiceNoLabelOrVisibilityTest() {

        UpdateSubSectionRequest updateSubSectionRequest =
                new UpdateSubSectionRequest(123L,  null, null);


        assertThrows(UpdateSubSectionException.class, () ->createSubSectionService.updateSubSection(updateSubSectionRequest));

    }

    @Test
    void deleteSubSectionTest() {

        DeleteSubSectionRequest deleteSubSectionRequest =
                new DeleteSubSectionRequest(123L);


        Mockito.when(waUiMetaDataRepository.getOrderNumber(123L))
                .thenReturn(1);

        Mockito.when(waUiMetaDataRepository.findPageNumber( 123L))
                .thenReturn(1234L);

        Mockito.when(waUiMetaDataRepository.findNextNbsUiComponentUid( 2, 1234L))
                .thenReturn(1015L);

        DeleteSubSectionResponse deleteSubSectionResponse =
                createSubSectionService.deleteSubSection( deleteSubSectionRequest);
        assertEquals("Sub Section Deleted Successfully", deleteSubSectionResponse.message());
    }


    @Test
    void deleteSubSectionTestExceptionInElse() {

        DeleteSubSectionRequest deleteSubSectionRequest =
                new DeleteSubSectionRequest(123L);


        Mockito.when(waUiMetaDataRepository.getOrderNumber(123L))
                .thenReturn(1);

        Mockito.when(waUiMetaDataRepository.findPageNumber( 123L))
                .thenReturn(1234L);

        Mockito.when(waUiMetaDataRepository.findNextNbsUiComponentUid( 2, 1234L))
                .thenReturn(10000L);

        assertThrows(DeleteSubSectionException.class, () -> createSubSectionService.deleteSubSection( deleteSubSectionRequest));

    }

    @Test
    void updateSubSectionServiceTestException() {
        assertThrows(UpdateSubSectionException.class, () -> createSubSectionService.updateSubSection(null));

    }
    @Test
    void deleteSubSectionServiceTestException() {
        assertThrows(DeleteSubSectionException.class, () -> createSubSectionService.deleteSubSection( null));

    }

    @Test
    void createSectionServiceTestException() {
        assertThrows(AddSubSectionException.class, () -> createSubSectionService.createSubSection(10L, 123L, null));
    }
}
