package gov.cdc.nbs.questionbank.valueset;

import java.time.Instant;
import java.util.Optional;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralRepository;
import static gov.cdc.nbs.questionbank.util.PageBuilderUtil.requireNotEmpty;
import static gov.cdc.nbs.questionbank.util.PageBuilderUtil.requireNonNull;
import gov.cdc.nbs.questionbank.valueset.command.ConceptCommand;
import gov.cdc.nbs.questionbank.valueset.exception.ConceptNotFoundException;
import gov.cdc.nbs.questionbank.valueset.exception.DuplicateConceptException;
import gov.cdc.nbs.questionbank.valueset.request.AddConceptRequest;
import gov.cdc.nbs.questionbank.valueset.request.UpdateConceptRequest;
import gov.cdc.nbs.questionbank.valueset.response.Concept;

@Component
public class ConceptManager {

    private final CodeValueGeneralRepository repository;
    private final ConceptMapper conceptMapper;

    public ConceptManager(
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

        // Character newStatus = request.active() ? 'A' : 'I';

        // if (!newStatus.equals(concept.getStatusCd())) {
        //     // There is also a `concept_status_cd` field that doesn't seem to be updated
        //     concept.setStatusCd(newStatus);
        //     concept.setStatusTime(Instant.now());
        // }
        CodeValueGeneral codeSystem = findCodeSystem(request.conceptMessagingInfo().codeSystem());


        if (request.conceptMessagingInfo() != null) {



            concept.updatValueGeneral(updateConcept(requireNotEmpty(
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
                    codeSystem.getCodeShortDescTxt(),
                    codeSystem.getCodeSystemCd(),
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

    /**
     * Adds a new concept to an existing value set
     * 
     * @param valueSet
     * @param request
     * @param userId
     * @return
     */
    public Concept addConcept(String valueSet, AddConceptRequest request, Long userId) {
        Optional<CodeValueGeneral> existingConcept = repository.findByIdCodeSetNmAndIdCode(valueSet, request.code());
        if (existingConcept.isPresent()) {
            throw new DuplicateConceptException(valueSet, request.code());
        }

        CodeValueGeneral codeSystem = findCodeSystem(request.messagingInfo().codeSystem());
        String codeSystemType;

        // Seems to me that we should pull the typeCd from the codeSystem's typeCd but in Classic ValueSetConcept.jsp #145,
        // only concepts with 'L' or "NBS_CDC" code will be set to 'LOCAL'
        if (codeSystem.getId().getCode().equals("L") || codeSystem.getId().getCode().equals("NBS_CDC")) {
            codeSystemType = "LOCAL";
        } else {
            codeSystemType = "PHIN";
        }
        ConceptCommand.AddConcept command = asAdd(
                valueSet,
                request,
                userId,
                codeSystemType,
                codeSystem.getCodeShortDescTxt(),
                codeSystem.getCodeDescTxt());

        CodeValueGeneral newConcept = new CodeValueGeneral(command);
        newConcept = repository.save(newConcept);
        return conceptMapper.toConcept(newConcept);
    }

    private ConceptCommand.AddConcept asAdd(
            String codeset,
            AddConceptRequest request,
            Long userId,
            String conceptType,
            String codeSystemDescription,
            String codeSystemId) {
        return new ConceptCommand.AddConcept(
                codeset,
                request.code(),
                request.displayName(),
                request.shortDisplayName(),
                request.effectiveFromTime(),
                request.effectiveToTime(),
                request.statusCode(),
                request.adminComments(),
                conceptType,
                request.messagingInfo().conceptCode(),
                request.messagingInfo().conceptName(),
                request.messagingInfo().preferredConceptName(),
                codeSystemDescription,
                codeSystemId,
                userId,
                Instant.now());
    }
}
