package gov.cdc.nbs.questionbank.valueset.update;

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
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralRepository;
import gov.cdc.nbs.questionbank.exception.NullObjectException;
import gov.cdc.nbs.questionbank.valueset.ConceptManager;
import gov.cdc.nbs.questionbank.valueset.ConceptMapper;
import gov.cdc.nbs.questionbank.valueset.request.UpdateConceptRequest;
import gov.cdc.nbs.questionbank.valueset.response.Concept;

@ExtendWith(MockitoExtension.class)
class ConceptUpdaterTest {

        @Mock
        private CodeValueGeneralRepository codeValueGeneralRepository;

        @Mock
        private ConceptMapper mapper;

        @InjectMocks
        private ConceptManager manager;

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
        void should_update_without_concept_messaging() {
                var concept = new CodeValueGeneral();
                when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("concept", "something"))
                                .thenReturn(Optional.of(concept));

                var request = new UpdateConceptRequest(
                                "longName",
                                "displayName",
                                Instant.parse("2023-01-01T00:00:00Z"),
                                null,
                                true,
                                null,
                                null);

                Concept newConcept = new Concept(
                                "something",
                                "CODE_SYSTEM",
                                "displayName",
                                "longName",
                                null,
                                null,
                                null,
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
