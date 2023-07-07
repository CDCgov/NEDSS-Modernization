package gov.cdc.nbs.questionbank.valueset.request;

import lombok.Data;

@Data
public class ValueSetUpdateRequest {
	private String codeSetName;
	private String valueSetNm;
	private String codeSetDescTxt;

}
