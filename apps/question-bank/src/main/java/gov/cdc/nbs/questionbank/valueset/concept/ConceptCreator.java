package gov.cdc.nbs.questionbank.valueset.concept;

import gov.cdc.nbs.questionbank.entity.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.entity.CodesetId;
import gov.cdc.nbs.questionbank.valueset.command.ConceptCommand;
import gov.cdc.nbs.questionbank.valueset.exception.ConceptCreationException;
import gov.cdc.nbs.questionbank.valueset.exception.ConceptNotFoundException;
import gov.cdc.nbs.questionbank.valueset.exception.DuplicateConceptException;
import gov.cdc.nbs.questionbank.valueset.model.Concept;
import gov.cdc.nbs.questionbank.valueset.model.Concept.Status;
import gov.cdc.nbs.questionbank.valueset.request.CreateConceptRequest;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class ConceptCreator {

  private final EntityManager entityManager;
  private final ConceptFinder finder;

  public ConceptCreator(final EntityManager entityManager, final ConceptFinder finder) {
    this.entityManager = entityManager;
    this.finder = finder;
  }

  private Concept findCodeSystem(String codeSystemName) {
    return finder
        .find("CODE_SYSTEM", codeSystemName)
        .orElseThrow(
            () ->
                new ConceptNotFoundException(
                    "Failed to find code system with code: " + codeSystemName));
  }

  /**
   * Adds a new concept to an existing value set
   *
   * @param valueSet
   * @param request
   * @param userId
   * @return
   */
  public Concept create(String valueSet, CreateConceptRequest request, Long userId) {
    // Ensure valueset exists
    Codeset found =
        this.entityManager.find(Codeset.class, new CodesetId("code_value_general", valueSet));
    if (found == null) {
      throw new ConceptCreationException("Failed to find Codeset: " + valueSet);
    }

    // Check if concept already exists for the valueset
    Optional<Concept> existingConcept = finder.find(valueSet, request.localCode());
    if (existingConcept.isPresent()) {
      throw new DuplicateConceptException(valueSet, request.localCode());
    }

    // Find the specified code system entry, for some reason it doesn't just add the code but the
    // codeDescTxt and codeShortDescTxt
    Concept codeSystem = findCodeSystem(request.codeSystem());

    // Seems to me that we should pull the typeCd from the codeSystem's typeCd but in Classic
    // ValueSetConcept.jsp #145,
    // only concepts with 'L' or "NBS_CDC" code will be set to 'LOCAL'
    String codeSystemType = "PHIN";
    if (codeSystem.localCode().equals("L") || codeSystem.localCode().equals("NBS_CDC")) {
      codeSystemType = "LOCAL";
    }

    ConceptCommand.AddConcept command =
        asAdd(
            valueSet, request, userId, codeSystemType, codeSystem.display(), codeSystem.longName());

    entityManager.persist(new CodeValueGeneral(command));
    return finder
        .find(valueSet, request.localCode())
        .orElseThrow(() -> new ConceptCreationException("Failed to create concept"));
  }

  private ConceptCommand.AddConcept asAdd(
      String codeset,
      CreateConceptRequest request,
      Long userId,
      String conceptType,
      String codeSystemDescription,
      String codeSystemId) {
    return new ConceptCommand.AddConcept(
        codeset,
        request.localCode(),
        request.longName(),
        request.display(),
        request.effectiveFromTime(),
        request.effectiveToTime(),
        Status.ACTIVE.equals(request.status()) ? 'A' : 'I',
        request.adminComments(),
        conceptType,
        request.conceptCode(),
        request.conceptName(),
        request.preferredConceptName(),
        codeSystemDescription,
        codeSystemId,
        userId,
        Instant.now());
  }
}
