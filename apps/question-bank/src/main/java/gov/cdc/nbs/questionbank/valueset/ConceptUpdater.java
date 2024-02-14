package gov.cdc.nbs.questionbank.valueset;

import static gov.cdc.nbs.questionbank.util.PageBuilderUtil.requireNonNull;
import static gov.cdc.nbs.questionbank.util.PageBuilderUtil.requireNotEmpty;

import java.time.Instant;

import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralRepository;
import gov.cdc.nbs.questionbank.valueset.command.ConceptCommand;
import gov.cdc.nbs.questionbank.valueset.exception.ConceptNotFoundException;
import gov.cdc.nbs.questionbank.valueset.model.Concept;
import gov.cdc.nbs.questionbank.valueset.request.UpdateConceptRequest;

@Component
public class ConceptUpdater {

  private final CodeValueGeneralRepository repository;

  public ConceptUpdater(
      final CodeValueGeneralRepository repository) {
    this.repository = repository;
  }

  /**
   * Allows updating the values for a concept. The 'Local Code' cannot be updated
   *
   * @param codeSetNm
   * @param localCode
   * @param request
   * @return
   */
  public Concept update(String codeSetNm, String localCode, UpdateConceptRequest request, Long userId) {
    CodeValueGeneral concept = repository.findByIdCodeSetNmAndIdCode(codeSetNm, localCode)
        .orElseThrow(() -> new ConceptNotFoundException(codeSetNm, localCode));

    requireNotEmpty(request.conceptMessagingInfo().codeSystem(), "conceptMessagingInfo.codeSystem");
    CodeValueGeneral codeSystem = findCodeSystem(request.conceptMessagingInfo().codeSystem());

    concept.updateValueGeneral(updateConcept(
        requireNotEmpty(request.displayName(), "displayName"),
        requireNotEmpty(request.conceptCode(), "conceptCode"),
        request.effectiveToTime(),
        requireNonNull(request.active(), "active"),
        requireNotEmpty(request.conceptMessagingInfo().conceptName(), "conceptMessagingInfo.conceptName"),
        requireNotEmpty(request.conceptMessagingInfo().preferredConceptName(),
            "conceptMessagingInfo.preferredConceptName"),
        codeSystem.getCodeSystemCd(),
        userId));
    concept = repository.save(concept);
    return null; // TODO
    // return conceptMapper.toConcept(concept);
  }

  @SuppressWarnings("squid:S107")
  private ConceptCommand.UpdateConcept updateConcept(
      String displayName,
      String conceptCode,
      Instant effectiveToTime,
      boolean active,
      String conceptName,
      String preferredConceptName,
      String codeSystem,
      long userId) {
    return new ConceptCommand.UpdateConcept(
        displayName,
        conceptCode,
        effectiveToTime,
        active,
        conceptName,
        preferredConceptName,
        codeSystem,
        userId,
        Instant.now());
  }

  private CodeValueGeneral findCodeSystem(String codeSystemName) {
    return repository
        .findByIdCodeSetNmAndIdCode("CODE_SYSTEM", codeSystemName)
        .orElseThrow(() -> new ConceptNotFoundException(
            "Failed to find code system with code: " + codeSystemName));
  }

}
