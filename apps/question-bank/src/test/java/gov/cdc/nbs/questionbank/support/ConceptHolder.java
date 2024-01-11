package gov.cdc.nbs.questionbank.support;

import java.util.List;

import gov.cdc.nbs.questionbank.valueset.response.RaceConcept;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.valueset.response.Concept;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ConceptHolder {
    private List<Concept> concepts;
    private List<RaceConcept> raceConcepts;
}
