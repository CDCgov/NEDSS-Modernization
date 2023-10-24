package gov.cdc.nbs.questionbank.page.content.addsubsection;

import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.subsection.SubSectionCreator;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.AddSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.DeleteSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.UpdateSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.request.CreateSubSectionRequest;
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
                new UpdateSubSectionRequest(  "Local", "T");

        UpdateSubSectionResponse updateSubSectionResponse =
                createSubSectionService.updateSubSection(123L, updateSubSectionRequest);
        assertEquals("SubSection Updated Successfully", updateSubSectionResponse.message());
    }


    @Test
    void updateSubSectionServiceNoLabelOrVisibilityTest() {

        UpdateSubSectionRequest updateSubSectionRequest =
                new UpdateSubSectionRequest(  null, null);


        assertThrows(UpdateSubSectionException.class, () ->createSubSectionService.updateSubSection(123L, updateSubSectionRequest));

    }

    @Test
    void deleteSubSectionTest() {

        Mockito.when(waUiMetaDataRepository.getOrderNumber(123L))
                .thenReturn(Optional.of(1));

        Mockito.when(waUiMetaDataRepository.findNextNbsUiComponentUid( 2, 1234L))
                .thenReturn(Optional.of(1015L));

        DeleteSubSectionResponse deleteSubSectionResponse =
                createSubSectionService.deleteSubSection(1234L, 123L);
        assertEquals("SubSection Deleted Successfully", deleteSubSectionResponse.message());
    }


    @Test
    void deleteSubSectionTestExceptionInElse() {

        Mockito.when(waUiMetaDataRepository.getOrderNumber(123L))
                .thenReturn(Optional.of(1));

        Mockito.when(waUiMetaDataRepository.findNextNbsUiComponentUid( 2, 1234L))
                .thenReturn(Optional.of(10L));

        assertThrows(DeleteSubSectionException.class, () ->
                createSubSectionService.deleteSubSection(1234L, 123L));

    }

    @Test
    void deleteSubSectionTestInElse() {

        Mockito.when(waUiMetaDataRepository.getOrderNumber(123L))
                .thenReturn(Optional.of(1));

        Mockito.when(waUiMetaDataRepository.findNextNbsUiComponentUid( 2, 1234L))
                .thenReturn(Optional.empty());

        DeleteSubSectionResponse deleteSubSectionResponse =
                createSubSectionService.deleteSubSection(1234L, 123L);
        assertEquals("SubSection Deleted Successfully", deleteSubSectionResponse.message());

    }

    @Test
    void updateSubSectionServiceTestException() {
        assertThrows(UpdateSubSectionException.class, () -> createSubSectionService.updateSubSection(123L, null));

    }
    @Test
    void deleteSubSectionServiceTestException() {
        Mockito.when(waUiMetaDataRepository.findNextNbsUiComponentUid( 2, 1234L))
                .thenThrow(new DeleteSubSectionException(""));
        assertThrows(DeleteSubSectionException.class, () ->
                createSubSectionService.deleteSubSection(1234L, 123L));

    }

    @Test
    void createSectionServiceTestException() {
        assertThrows(AddSubSectionException.class, () -> createSubSectionService.createSubSection(10L, 123L, null));
    }
}
