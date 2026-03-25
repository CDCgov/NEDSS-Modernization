package gov.cdc.nbs.questionbank.valueset.concept;

import static gov.cdc.nbs.questionbank.util.PageBuilderUtil.requireNotEmpty;

import gov.cdc.nbs.questionbank.entity.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralId;
import gov.cdc.nbs.questionbank.valueset.command.ConceptCommand;
import gov.cdc.nbs.questionbank.valueset.exception.ConceptNotFoundException;
import gov.cdc.nbs.questionbank.valueset.exception.UpdateConceptException;
import gov.cdc.nbs.questionbank.valueset.model.Concept;
import gov.cdc.nbs.questionbank.valueset.model.Concept.Status;
import gov.cdc.nbs.questionbank.valueset.request.UpdateConceptRequest;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.Instant;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class ConceptUpdater {

  private final EntityManager entityManager;
  private final ConceptFinder finder;

  public ConceptUpdater(final EntityManager entityManager, final ConceptFinder finder) {
    this.entityManager = entityManager;
    this.finder = finder;
  }

  /**
   * Allows updating the values for a concept. The 'Local Code' cannot be updated
   *
   * @param codeSetNm
   * @param localCode
   * @param request
   * @return
   */
  public Concept update(
      String codeSetNm, String localCode, UpdateConceptRequest request, Long userId) {
    CodeValueGeneral concept =
        entityManager.find(CodeValueGeneral.class, new CodeValueGeneralId(codeSetNm, localCode));
    if (concept == null) {
      throw new ConceptNotFoundException(codeSetNm, localCode);
    }

    requireNotEmpty(request.codeSystem(), "conceptMessagingInfo.codeSystem");
    Concept codeSystem = findCodeSystem(request.codeSystem());

    requireNotEmpty(request.display(), "displayName");
    requireNotEmpty(request.conceptCode(), "conceptCode");
    requireNotEmpty(request.conceptName(), "conceptMessagingInfo.conceptName");
    requireNotEmpty(request.preferredConceptName(), "conceptMessagingInfo.preferredConceptName");

    concept.update(asUpdate(request, userId, codeSystem.display(), codeSystem.longName()));
    return finder
        .find(codeSetNm, localCode)
        .orElseThrow(() -> new UpdateConceptException("Failed to update concept"));
  }

  private ConceptCommand.UpdateConcept asUpdate(
      UpdateConceptRequest request,
      Long userId,
      String codeSystemDescription,
      String codeSystemId) {
    return new ConceptCommand.UpdateConcept(
        request.longName(),
        request.display(),
        request.effectiveFromTime(),
        request.effectiveToTime(),
        Status.ACTIVE.equals(request.status()) ? 'A' : 'I',
        request.adminComments(),
        request.conceptCode(),
        request.conceptName(),
        request.preferredConceptName(),
        codeSystemDescription,
        codeSystemId,
        userId,
        Instant.now());
  }

  private Concept findCodeSystem(String codeSystemName) {
    return finder
        .find("CODE_SYSTEM", codeSystemName)
        .orElseThrow(
            () ->
                new ConceptNotFoundException(
                    "Failed to find code system with code: " + codeSystemName));
  }
}
