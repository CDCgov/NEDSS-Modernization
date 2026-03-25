package gov.cdc.nbs.questionbank.question.response;

import java.time.LocalDate;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ValueSetConcept {

  private String localCode;
  private String uiDisplayName;
  private String conceptCode;
  private String messagingConceptName;
  private String codeSystemName;
  private String status;
  private LocalDate effectiveFrom;
}
