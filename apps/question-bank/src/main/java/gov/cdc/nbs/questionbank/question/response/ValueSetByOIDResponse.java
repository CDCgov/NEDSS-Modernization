package gov.cdc.nbs.questionbank.question.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ValueSetByOIDResponse {
  @JsonInclude(value = Include.NON_EMPTY, content = Include.NON_EMPTY)
  private Status status;

  @JsonInclude(value = Include.NON_EMPTY, content = Include.NON_EMPTY)
  private ValueSetByOIDResults data;
}
