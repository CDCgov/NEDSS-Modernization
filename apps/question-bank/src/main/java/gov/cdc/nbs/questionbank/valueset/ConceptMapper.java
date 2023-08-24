package gov.cdc.nbs.questionbank.valueset;

import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneral;
import gov.cdc.nbs.questionbank.valueset.response.Concept;

@Component
public class ConceptMapper {

    public Concept toConcept(CodeValueGeneral codeValueGeneral) {
        return new Concept(
                codeValueGeneral.getId().getCode(),
                codeValueGeneral.getId().getCodeSetNm(),
                codeValueGeneral.getCodeShortDescTxt(),
                codeValueGeneral.getCodeDescTxt(),
                codeValueGeneral.getConceptCode(),
                codeValueGeneral.getConceptPreferredNm(),
                codeValueGeneral.getCodeSystemDescTxt(),
                codeValueGeneral.getConceptStatusCd(),
                codeValueGeneral.getEffectiveFromTime(),
                codeValueGeneral.getEffectiveToTime());
    }
}
