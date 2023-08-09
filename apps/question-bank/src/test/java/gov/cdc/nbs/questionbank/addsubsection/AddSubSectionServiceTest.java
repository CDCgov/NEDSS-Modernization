package gov.cdc.nbs.questionbank.addsubsection;

import gov.cdc.nbs.questionbank.addsubsection.exception.AddSubSectionException;
import gov.cdc.nbs.questionbank.addsubsection.model.CreateSubSectionRequest;
import gov.cdc.nbs.questionbank.addtab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.Optional;
import javax.persistence.EntityManager;

@ExtendWith(MockitoExtension.class)
class AddSubSectionServiceTest {

    @InjectMocks
    private CreateSubSectionService createSubSectionService;

    @Mock
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @Mock
    private EntityManager entityManager;

    @Test
    void createSubSectionServiceTest() {
        WaUiMetadata section = new WaUiMetadata();
        section.setOrderNbr(1);
        when(waUiMetaDataRepository.findById(10L)).thenReturn(Optional.of(section));
        CreateSubSectionRequest createSubSectionRequest =
                new CreateSubSectionRequest(10L, 10L, "Local", "T");

        CreateSubSectionResponse createSubSectionResponse =
                createSubSectionService.createSubSection(123L, createSubSectionRequest);
        assertEquals("SubSection Created Successfully", createSubSectionResponse.message());
    }

    @Test
    void createSectionServiceTestException() {
        assertThrows(AddSubSectionException.class, () -> createSubSectionService.createSubSection(123L, null));
    }
}
