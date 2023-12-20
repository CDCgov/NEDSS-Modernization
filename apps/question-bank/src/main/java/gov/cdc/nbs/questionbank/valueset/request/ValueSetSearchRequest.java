package gov.cdc.nbs.questionbank.valueset.request;

import lombok.Data;

@Data
public class ValueSetSearchRequest {
  private String valueSetNm;
  private String valueSetCode;
  private String valueSetDescription;

}
