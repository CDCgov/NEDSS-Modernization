package gov.cdc.nbs.questionbank.valueset;

import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneral;
import gov.cdc.nbs.questionbank.valueset.model.Concept;

@Component
public class ConceptMapper {

  public Concept toConcept(CodeValueGeneral codeValueGeneral) {
    return new Concept(
        codeValueGeneral.getId().getCode(),
        codeValueGeneral.getId().getCodeSetNm(),
        codeValueGeneral.getCodeShortDescTxt(),
        codeValueGeneral.getCodeDescTxt(),
        codeValueGeneral.getConceptCode(),
        codeValueGeneral.getConceptNm(),
        codeValueGeneral.getCodeSystemDescTxt(),
        codeValueGeneral.getConceptStatusCd(),
        codeValueGeneral.getEffectiveFromTime(),
        codeValueGeneral.getEffectiveToTime());
  }
}
