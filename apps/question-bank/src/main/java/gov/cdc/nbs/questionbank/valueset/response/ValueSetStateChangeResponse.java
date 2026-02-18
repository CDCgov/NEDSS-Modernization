package gov.cdc.nbs.questionbank.valueset.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class ValueSetStateChangeResponse {
  private String codeSetNm;
  private String statusCd;
  private String message;
  private HttpStatus status;
}
