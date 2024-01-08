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
import gov.cdc.nbs.questionbank.valueset.ConceptMapper;
import gov.cdc.nbs.questionbank.valueset.ConceptUpdater;
import gov.cdc.nbs.questionbank.valueset.request.UpdateConceptRequest;
import gov.cdc.nbs.questionbank.valueset.response.Concept;

@ExtendWith(MockitoExtension.class)
class ConceptUpdaterTest {

  @Mock
  private CodeValueGeneralRepository codeValueGeneralRepository;

  @Mock
  private ConceptMapper mapper;

  @InjectMocks
  private ConceptUpdater conceptUpdater;

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
        "displayName",
        "concept code",
        null,
        true,
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
        Instant.parse("2024-01-01T00:00:00Z"),
        null);

    ArgumentCaptor<CodeValueGeneral> captor = ArgumentCaptor.forClass(CodeValueGeneral.class);
    when(codeValueGeneralRepository.save(captor.capture())).thenAnswer(q -> q.getArgument(0));
    when(mapper.toConcept(concept)).thenReturn(newConcept);
    Concept result = conceptUpdater.update("concept", "something", request, 1L);
    assertEquals("displayName", result.display());
    assertThat(result.effectiveFromTime()).isEqualTo("2024-01-01T00:00:00Z");
    assertEquals("concept code", result.conceptCode());
    assertEquals("pref name", result.messagingConceptName());
    assertEquals("anything", result.codeSystem());
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
        null,
        "concept code",
        null,
        true,
        new UpdateConceptRequest.ConceptMessagingInfo(
            "concept code",
            "concept name",
            "pref name",
            "anything"));

    assertThrows(NullObjectException.class, () -> conceptUpdater.update("concept", "something", request, 1L));
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
        "displayName",
        null,
        null,
        true,
        new UpdateConceptRequest.ConceptMessagingInfo(
            null,
            "concept name",
            "pref name",
            "anything"));

    assertThrows(NullObjectException.class, () -> conceptUpdater.update("concept", "something", request, 1L));
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
        "displayName",
        "concept code",
        null,
        true,
        new UpdateConceptRequest.ConceptMessagingInfo(
            "concept code",
            null,
            "pref name",
            "anything"));
    assertThrows(NullObjectException.class, () -> conceptUpdater.update("concept", "something", request, 1L));
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
        "displayName",
        "concept code",
        null,
        true,
        new UpdateConceptRequest.ConceptMessagingInfo(
            "concept code",
            "concept name",
            null,
            "anything"));

    assertThrows(NullObjectException.class, () -> conceptUpdater.update("concept", "something", request, 1L));
  }

  @Test
  void should_throw_exception_if_code_system_not_found() {
    var concept = new CodeValueGeneral();
    when(codeValueGeneralRepository.findByIdCodeSetNmAndIdCode("concept", "anything"))
        .thenReturn(Optional.of(concept));

    var request = new UpdateConceptRequest(
        "displayName",
        "concept code",
        null,
        true,
        new UpdateConceptRequest.ConceptMessagingInfo(
            "concept code",
            "concept name",
            "pref name",
            null));

    assertThrows(NullObjectException.class, () -> conceptUpdater.update("concept", "anything", request, 1L));
  }

}
