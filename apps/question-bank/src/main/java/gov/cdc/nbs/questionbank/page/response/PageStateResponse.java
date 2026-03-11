package gov.cdc.nbs.questionbank.page.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageStateResponse {
  private Long templateId;
  private String message;
}
