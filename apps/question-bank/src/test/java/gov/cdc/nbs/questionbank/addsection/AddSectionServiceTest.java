package gov.cdc.nbs.questionbank.addsection;

import gov.cdc.nbs.questionbank.addsection.exception.AddSectionException;
import gov.cdc.nbs.questionbank.addsection.model.CreateSectionRequest;
import gov.cdc.nbs.questionbank.addtab.repository.WaUiMetaDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import javax.persistence.EntityManager;

@ExtendWith(MockitoExtension.class)
class AddSectionServiceTest {

    @InjectMocks
    private CreateSectionService createSectionService;

    @Mock
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @Mock
    private EntityManager entityManager;

    @Test
    void createSectionServiceTest() {

        CreateSectionRequest createSectionRequest =
                new CreateSectionRequest(10L, 10L, "Local", "T");

        CreateSectionResponse createSectionResponse =
                createSectionService.createSection(123L, createSectionRequest);
        assertEquals("Section Created Successfully", createSectionResponse.message());
    }

    @Test
    void createSectionServiceTestException() {
        assertThrows(AddSectionException.class, () -> createSectionService.createSection(123L, null));

    }
}
