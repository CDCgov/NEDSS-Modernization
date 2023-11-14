package gov.cdc.nbs.questionbank.valueset;

import java.time.Instant;
import java.util.Optional;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralRepository;
import gov.cdc.nbs.questionbank.valueset.command.ConceptCommand;
import gov.cdc.nbs.questionbank.valueset.exception.ConceptNotFoundException;
import gov.cdc.nbs.questionbank.valueset.exception.DuplicateConceptException;
import gov.cdc.nbs.questionbank.valueset.request.AddConceptRequest;
import gov.cdc.nbs.questionbank.valueset.response.Concept;

@Component
public class ConceptCreator {

        private final CodeValueGeneralRepository repository;
        private final ConceptMapper conceptMapper;

        public ConceptCreator(
                        final CodeValueGeneralRepository repository,
                        final ConceptMapper conceptMapper) {
                this.repository = repository;
                this.conceptMapper = conceptMapper;
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
                Optional<CodeValueGeneral> existingConcept =
                                repository.findByIdCodeSetNmAndIdCode(valueSet, request.code());
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
