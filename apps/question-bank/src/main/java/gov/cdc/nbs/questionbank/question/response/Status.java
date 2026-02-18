package gov.cdc.nbs.questionbank.question.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Status {

  private String code;
  private String type;
  private String message;
  private String description;
}
