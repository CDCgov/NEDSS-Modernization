package gov.cdc.nbs.questionbank.valueset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;
import java.time.Instant;
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
import gov.cdc.nbs.questionbank.exception.NullObjectException;
import gov.cdc.nbs.questionbank.valueset.exception.ConceptNotFoundException;
import gov.cdc.nbs.questionbank.valueset.exception.DuplicateConceptException;
import gov.cdc.nbs.questionbank.valueset.request.AddConceptRequest;
import gov.cdc.nbs.questionbank.valueset.request.UpdateConceptRequest;
import gov.cdc.nbs.questionbank.valueset.request.AddConceptRequest.StatusCode;
import gov.cdc.nbs.questionbank.valueset.response.Concept;


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

        // When attempting to add the concept with code system of "NBS_CDC"
        ArgumentCaptor<CodeValueGeneral> captor = ArgumentCaptor.forClass(CodeValueGeneral.class);
        var request = new AddConceptRequest(
                "code",
                null,
                null,
                null,
                null,
                StatusCode.A,
                null,
                new AddConceptRequest.MessagingInfo(null, null, null, "NBS_CDC"));

        when(codeValueGeneralRepository.save(captor.capture())).thenAnswer(a -> a.getArguments()[0]);
        manager.addConcept("codeset", request, 1L);

        // Then the codeSystemType is set to 'LOCAL'
        assertEquals("LOCAL", captor.getValue().getConceptTypeCd());
    }

    @Test
    void should_set_PHIN() {
        // Given a concept doesn't already exist
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("codeset", "code"))
            .thenReturn(Optional.empty());

        // And a code system that exists
        var codeSystem = new CodeValueGeneral();
        codeSystem.setId(new CodeValueGeneralId("CODE_SYSTEM", "anything"));
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("CODE_SYSTEM", "anything"))
        .thenReturn(Optional.of(codeSystem));

        // When attempting to add the concept with code system of "anything"
        ArgumentCaptor<CodeValueGeneral> captor = ArgumentCaptor.forClass(CodeValueGeneral.class);
        var request = new AddConceptRequest(
                "code",
                null,
                null,
                null,
                null,
                StatusCode.A,
                null,
                new AddConceptRequest.MessagingInfo(null, null, null, "anything"));

        when(codeValueGeneralRepository.save(captor.capture())).thenAnswer(a -> a.getArguments()[0]);
        manager.addConcept("codeset", request, 1L);

        // Then the codeSystemType is set to 'PHIN'
        assertEquals("PHIN", captor.getValue().getConceptTypeCd());
    }

    @Test
    void should_throw_exception_if_code_system_not_found() {
        // Given a concept doesn't already exist
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("codeset", "code"))
            .thenReturn(Optional.empty());

        // And a code system that doesnt exist
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("CODE_SYSTEM", "anything"))
            .thenReturn(Optional.empty());

        // When attempting to add a concept with invalid code system
        var request = new AddConceptRequest(
                "code",
                null,
                null,
                null,
                null,
                StatusCode.A,
                null,
                new AddConceptRequest.MessagingInfo(null, null, null, "anything"));
        // Then an exception is thrown
        assertThrows(ConceptNotFoundException.class, () -> manager.addConcept("codeset", request, 1L));
    }

    @Test
    void should_update_concept() {
        var concept = new CodeValueGeneral();
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("concept", "something"))
                .thenReturn(Optional.of(concept));

        var codeSystem = new CodeValueGeneral();
        codeSystem.setCodeShortDescTxt("CODE_SYSTEM");
        codeSystem.setCodeSystemCd("anything");
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("CODE_SYSTEM", "anything"))
                .thenReturn(Optional.of(codeSystem));


        var request = new UpdateConceptRequest(
                "longName",
                "displayName",
                Instant.parse("2023-01-01T00:00:00Z"),
                null,
                true,
                null,
                new UpdateConceptRequest.ConceptMessagingInfo(
                        "concept code",
                        "concept name",
                        "pref name",
                        "anything"));

        Concept newConcept = new Concept(
                "something",
                "CODE_SYSTEM",
                "displayName",
                "longName",
                "concept code",
                "pref name",
                "anything",
                "active",
                Instant.parse("2023-01-01T00:00:00Z"),
                null);


        ArgumentCaptor<CodeValueGeneral> captor = ArgumentCaptor.forClass(CodeValueGeneral.class);
        when(codeValueGeneralRepository.save(captor.capture())).thenAnswer(q -> q.getArgument(0));

        when(mapper.toConcept(concept)).thenReturn(newConcept);

        Concept result = manager.update("concept", "something", request, 1L);

        assertEquals("longName", result.longName());
        assertEquals("displayName", result.display());
        assertThat(result.effectiveFromTime()).isEqualTo("2023-01-01T00:00:00Z");
        assertEquals("concept code", result.conceptCode());
        assertEquals("pref name", result.messagingConceptName());
        assertEquals("anything", result.codeSystem());
    }

    @Test
    void should_throw_exception_if_long_name_not_found() {
        var concept = new CodeValueGeneral();
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("concept", "something"))
                .thenReturn(Optional.of(concept));

        var codeSystem = new CodeValueGeneral();
        codeSystem.setCodeShortDescTxt("CODE_SYSTEM");
        codeSystem.setCodeSystemCd("anything");
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("CODE_SYSTEM", "anything"))
                .thenReturn(Optional.of(codeSystem));


        var request = new UpdateConceptRequest(
                null,
                "displayName",
                Instant.parse("2023-01-01T00:00:00Z"),
                null,
                true,
                null,
                new UpdateConceptRequest.ConceptMessagingInfo(
                        "concept code",
                        "concept name",
                        "pref name",
                        "anything"));

        assertThrows(NullObjectException.class, () -> manager.update("concept", "something", request, 1L));
    }

    @Test
    void should_throw_exception_if_display_name_not_found() {
        var concept = new CodeValueGeneral();
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("concept", "something"))
                .thenReturn(Optional.of(concept));

        var codeSystem = new CodeValueGeneral();
        codeSystem.setCodeShortDescTxt("CODE_SYSTEM");
        codeSystem.setCodeSystemCd("anything");
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("CODE_SYSTEM", "anything"))
                .thenReturn(Optional.of(codeSystem));


        var request = new UpdateConceptRequest(
                "something",
                null,
                Instant.parse("2023-01-01T00:00:00Z"),
                null,
                true,
                null,
                new UpdateConceptRequest.ConceptMessagingInfo(
                        "concept code",
                        "concept name",
                        "pref name",
                        "anything"));

        assertThrows(NullObjectException.class, () -> manager.update("concept", "something", request, 1L));
    }

    @Test
    void should_throw_exception_if_effective_from_time_not_found() {
        var concept = new CodeValueGeneral();
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("concept", "something"))
                .thenReturn(Optional.of(concept));

        var codeSystem = new CodeValueGeneral();
        codeSystem.setCodeShortDescTxt("CODE_SYSTEM");
        codeSystem.setCodeSystemCd("anything");
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("CODE_SYSTEM", "anything"))
                .thenReturn(Optional.of(codeSystem));


        var request = new UpdateConceptRequest(
                "something",
                "display",
                null,
                null,
                true,
                null,
                new UpdateConceptRequest.ConceptMessagingInfo(
                        "concept code",
                        "concept name",
                        "pref name",
                        "anything"));

        assertThrows(NullObjectException.class, () -> manager.update("concept", "something", request, 1L));
    }

    @Test
    void should_throw_exception_if_concept_code_not_found() {
        var concept = new CodeValueGeneral();
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("concept", "something"))
                .thenReturn(Optional.of(concept));

        var codeSystem = new CodeValueGeneral();
        codeSystem.setCodeShortDescTxt("CODE_SYSTEM");
        codeSystem.setCodeSystemCd("anything");
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("CODE_SYSTEM", "anything"))
                .thenReturn(Optional.of(codeSystem));


        var request = new UpdateConceptRequest(
                "something",
                "display",
                Instant.parse("2023-01-01T00:00:00Z"),
                null,
                false,
                null,
                new UpdateConceptRequest.ConceptMessagingInfo(
                        null,
                        "concept name",
                        "pref name",
                        "anything"));

        assertThrows(NullObjectException.class, () -> manager.update("concept", "something", request, 1L));
    }

    @Test
    void should_throw_exception_if_concept_name_not_found() {
        var concept = new CodeValueGeneral();
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("concept", "something"))
                .thenReturn(Optional.of(concept));

        var codeSystem = new CodeValueGeneral();
        codeSystem.setCodeShortDescTxt("CODE_SYSTEM");
        codeSystem.setCodeSystemCd("anything");
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("CODE_SYSTEM", "anything"))
                .thenReturn(Optional.of(codeSystem));


        var request = new UpdateConceptRequest(
                "something",
                "display",
                Instant.parse("2023-01-01T00:00:00Z"),
                null,
                false,
                null,
                new UpdateConceptRequest.ConceptMessagingInfo(
                        "concept code",
                        null,
                        "pref name",
                        "anything"));

        assertThrows(NullObjectException.class, () -> manager.update("concept", "something", request, 1L));
    }

    @Test
    void should_throw_exception_if_pref_name_not_found() {
        var concept = new CodeValueGeneral();
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("concept", "something"))
                .thenReturn(Optional.of(concept));

        var codeSystem = new CodeValueGeneral();
        codeSystem.setCodeShortDescTxt("CODE_SYSTEM");
        codeSystem.setCodeSystemCd("anything");
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("CODE_SYSTEM", "anything"))
                .thenReturn(Optional.of(codeSystem));


        var request = new UpdateConceptRequest(
                "something",
                "display",
                Instant.parse("2023-01-01T00:00:00Z"),
                null,
                false,
                null,
                new UpdateConceptRequest.ConceptMessagingInfo(
                        "concept code",
                        "concept name",
                        null,
                        "anything"));

        assertThrows(NullObjectException.class, () -> manager.update("concept", "something", request, 1L));
    }

    @Test
    void should_throw_exception_if_code_system_update_not_found() {
        var concept = new CodeValueGeneral();
        when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("concept", "something"))
                .thenReturn(Optional.of(concept));

        var request = new UpdateConceptRequest(
                "something",
                "display",
                Instant.parse("2023-01-01T00:00:00Z"),
                null,
                false,
                null,
                new UpdateConceptRequest.ConceptMessagingInfo(
                        "concept code",
                        "concept Name",
                        "pref name",
                        null));

        assertThrows(NullObjectException.class, () -> manager.update("concept", "something", request, 1L));
    }

}
