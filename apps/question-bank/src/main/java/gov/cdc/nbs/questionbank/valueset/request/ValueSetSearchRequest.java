package gov.cdc.nbs.questionbank.valueset.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValueSetSearchRequest {
  private String valueSetNm;
  private String valueSetCode;
  private String valueSetDescription;
}
