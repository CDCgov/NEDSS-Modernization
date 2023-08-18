package gov.cdc.nbs.questionbank.valueset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralId;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralRepository;
import gov.cdc.nbs.questionbank.valueset.exception.DuplicateConceptException;
import gov.cdc.nbs.questionbank.valueset.request.AddConceptRequest;
import gov.cdc.nbs.questionbank.valueset.request.AddConceptRequest.StatusCode;

@ExtendWith(MockitoExtension.class)
class ConceptManagerTest {

    @Mock
    private CodeValueGeneralRepository codeValueGeneralRepository;

    @Mock
    private ConceptMapper mapper;

    @InjectMocks
    private ConceptManager manager;

    @Test
    void should_throw_duplicate_exception() {
        // Given a concept already exists for the value set
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("codeset", "code"))
            .thenReturn(Optional.of(new CodeValueGeneral()));

        // When attempting to add the concept
        // Then an exception is thrown
        var request = new AddConceptRequest(
                "code",
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        assertThrows(DuplicateConceptException.class, () -> manager.addConcept("codeset", request, 1L));
    }

    @Test
    void should_set_local_type_L() {
        // Given a concept doesn't already exist
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("codeset", "code"))
            .thenReturn(Optional.empty());

        // And a code system that exists
        var codeSystem = new CodeValueGeneral();
        codeSystem.setId(new CodeValueGeneralId("CODE_SYSTEM", "L"));
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("CODE_SYSTEM", "L"))
        .thenReturn(Optional.of(codeSystem));

        // When attempting to add the concept with code system of "L"
        ArgumentCaptor<CodeValueGeneral> captor = ArgumentCaptor.forClass(CodeValueGeneral.class);
        var request = new AddConceptRequest(
                "code",
                null,
                null,
                null,
                null,
                StatusCode.A,
                null,
                new AddConceptRequest.MessagingInfo(null, null, null, "L"));

        when(codeValueGeneralRepository.save(captor.capture())).thenAnswer(a -> a.getArguments()[0]);
        manager.addConcept("codeset", request, 1L);

        // Then the codeSystemType is set to 'LOCAL'
        assertEquals("LOCAL", captor.getValue().getConceptTypeCd());
    }

    @Test
    void should_set_local_type_NBS_CDC() {
        // Given a concept doesn't already exist
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("codeset", "code"))
            .thenReturn(Optional.empty());

        // And a code system that exists
        var codeSystem = new CodeValueGeneral();
        codeSystem.setId(new CodeValueGeneralId("CODE_SYSTEM", "NBS_CDC"));
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("CODE_SYSTEM", "NBS_CDC"))
        .thenReturn(Optional.of(codeSystem));

        // When attempting to add the concept with code system of "L"
        ArgumentCaptor<CodeValueGeneral> captor = ArgumentCaptor.forClass(CodeValueGeneral.class);
        var request = new AddConceptRequest(
                "code",
                null,
                null,
                null,
                null,
                StatusCode.A,
                null,
                new AddConceptRequest.MessagingInfo(null, null, null, "L"));

        when(codeValueGeneralRepository.save(captor.capture())).thenAnswer(a -> a.getArguments()[0]);
        manager.addConcept("codeset", request, 1L);

        // Then the codeSystemType is set to 'LOCAL'
        assertEquals("LOCAL", captor.getValue().getConceptTypeCd());
    }
}
