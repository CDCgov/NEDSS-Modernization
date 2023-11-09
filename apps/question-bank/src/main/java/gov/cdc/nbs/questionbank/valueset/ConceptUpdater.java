package gov.cdc.nbs.questionbank.valueset;

import static gov.cdc.nbs.questionbank.util.PageBuilderUtil.requireNonNull;
import static gov.cdc.nbs.questionbank.util.PageBuilderUtil.requireNotEmpty;
import java.time.Instant;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralRepository;
import gov.cdc.nbs.questionbank.valueset.command.ConceptCommand;
import gov.cdc.nbs.questionbank.valueset.exception.ConceptNotFoundException;
import gov.cdc.nbs.questionbank.valueset.request.UpdateConceptRequest;
import gov.cdc.nbs.questionbank.valueset.response.Concept;

@Component
public class ConceptUpdater {

    private final CodeValueGeneralRepository repository;
    private final ConceptMapper conceptMapper;

    public ConceptUpdater(
            final CodeValueGeneralRepository repository,
            final ConceptMapper conceptMapper) {
        this.repository = repository;
        this.conceptMapper = conceptMapper;
    }

    /**
     * Allows updating the values for a concept. The 'Concept Code' cannot be updated
     * 
     * @param codeSetNm
     * @param conceptCode
     * @param request
     * @return
     */
    public Concept update(String codeSetNm, String conceptCode, UpdateConceptRequest request, Long userId) {
        CodeValueGeneral concept = repository.findByIdCodeSetNmAndIdCode(codeSetNm, conceptCode)
                .orElseThrow(() -> new ConceptNotFoundException(codeSetNm, conceptCode));

        if (request.conceptMessagingInfo() != null) {
            requireNotEmpty(request.conceptMessagingInfo().codeSystem(), "conceptMessagingInfo.codeSystem");
            CodeValueGeneral codeSystem = findCodeSystem(request.conceptMessagingInfo().codeSystem());

            concept.updatValueGeneral(updateConcept(
                    requireNotEmpty(
                            request.longName(), "longName"),
                    requireNotEmpty(request.displayName(), "displayName"),
                    requireNonNull(request.effectiveFromTime(), "effectiveFromTime"),
                    request.effectiveToTime(),
                    requireNonNull(request.active(), "active"),
                    request.adminComments(),
                    requireNotEmpty(
                            request.conceptMessagingInfo().conceptCode(),
                            "conceptMessagingInfo.conceptCode"),
                    requireNotEmpty(
                            request.conceptMessagingInfo().conceptName(),
                            "conceptMessagingInfo.conceptName"),
                    requireNotEmpty(
                            request.conceptMessagingInfo().preferredConceptName(),
                            "conceptMessagingInfo.preferredConceptName"),
                    codeSystem.getCodeShortDescTxt(),
                    codeSystem.getCodeSystemCd(),
                    userId));
        } else {
            concept.updatValueGeneral(updateConcept(requireNotEmpty(
                    request.longName(), "longName"),
                    requireNotEmpty(request.displayName(), "displayName"),
                    requireNonNull(request.effectiveFromTime(), "effectiveFromTime"),
                    request.effectiveToTime(),
                    requireNonNull(request.active(), "active"),
                    request.adminComments(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    userId));
        }

        concept = repository.save(concept);

        return conceptMapper.toConcept(concept);
    }

    private ConceptCommand.UpdateConcept updateConcept(
            String longName,
            String displayName,
            Instant effectiveFromTime,
            Instant effectiveToTime,
            boolean active,
            String adminComments,
            String conceptCode,
            String conceptName,
            String preferredConceptName,
            String codeSystem,
            String codeSystemCd,
            long userId) {
        return new ConceptCommand.UpdateConcept(longName,
                displayName,
                effectiveFromTime,
                effectiveToTime,
                active,
                adminComments,
                Instant.now(),
                conceptCode,
                conceptName,
                preferredConceptName,
                codeSystem,
                codeSystemCd,
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
