package gov.cdc.nbs.questionbank.support;

import gov.cdc.nbs.questionbank.valueset.model.Concept;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ConceptHolder {
  private List<Concept> concepts;
}
