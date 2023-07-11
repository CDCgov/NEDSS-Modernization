package gov.cdc.nbs.questionbank.support;

import java.util.List;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.valueset.response.Concept;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ConceptHolder {
    private List<Concept> concepts;
}
