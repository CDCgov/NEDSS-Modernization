package gov.cdc.nbs.questionbank.valueset.request;

import lombok.Data;

@Data
public class ValueSetSearchRequest {
	private String codeSetName;
	private String valueSetNm;
	private String valueSetCode;

}
