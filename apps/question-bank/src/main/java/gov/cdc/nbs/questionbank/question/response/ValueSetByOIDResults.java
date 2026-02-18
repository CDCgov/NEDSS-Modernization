package gov.cdc.nbs.questionbank.question.response;

import java.util.List;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ValueSetByOIDResults {

  private String valueSetType;
  private String valueSetCode;
  private String valueSetName;
  private String valueSetDesc;
  private String status;

  private List<ValueSetConcept> valueSetConcepts;
}
