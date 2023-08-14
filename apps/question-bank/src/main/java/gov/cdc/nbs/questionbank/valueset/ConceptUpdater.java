package gov.cdc.nbs.questionbank.valueset;

import java.time.Instant;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralRepository;
import gov.cdc.nbs.questionbank.valueset.exception.ConceptNotFoundException;
import gov.cdc.nbs.questionbank.valueset.request.UpdateConceptRequest;
import gov.cdc.nbs.questionbank.valueset.response.Concept;

@Component
public class ConceptUpdater {

    private final CodeValueGeneralRepository repository;

    public ConceptUpdater(final CodeValueGeneralRepository repository) {
        this.repository = repository;
    }

    /**
     * Allows updating the values for a concept. The 'Concept Code' cannot be updated
     * 
     * @param codeSetNm
     * @param conceptCode
     * @param request
     * @return
     */
    public Concept update(String codeSetNm, String conceptCode, UpdateConceptRequest request) {
        CodeValueGeneral concept = repository.findByIdCodeSetNmAndIdCode(codeSetNm, conceptCode)
                .orElseThrow(() -> new ConceptNotFoundException(codeSetNm, conceptCode));
        concept.setCodeDescTxt(request.name());
        concept.setCodeShortDescTxt(request.displayName());
        concept.setEffectiveFromTime(request.effectiveFromTime());
        concept.setEffectiveToTime(request.effectiveToTime());
        Character newStatus = request.active() ? 'A' : 'I';
        if (!newStatus.equals(concept.getStatusCd())) {
            // There is also a `concept_status_cd` field that doesn't seem to be updated
            concept.setStatusCd(newStatus);
            concept.setStatusTime(Instant.now());
        }
        concept.setAdminComments(request.adminComments());
        concept = repository.save(concept);

        return new Concept(
                concept.getId().getCode(),
                concept.getId().getCodeSetNm(),
                concept.getCodeShortDescTxt(),
                concept.getCodeDescTxt(),
                concept.getConceptCode(),
                concept.getConceptPreferredNm(),
                concept.getCodeSystemDescTxt(),
                concept.getConceptStatusCd(),
                concept.getEffectiveFromTime(),
                concept.getEffectiveToTime());
    }

}
