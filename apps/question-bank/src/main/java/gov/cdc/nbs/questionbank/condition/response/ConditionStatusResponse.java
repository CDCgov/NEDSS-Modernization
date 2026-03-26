package gov.cdc.nbs.questionbank.condition.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConditionStatusResponse {
  private String id;
  private Character statusCd;
}
