package gov.cdc.nbs.questionbank.valueset.request;


public record ValueSetUpdateRequest(
		String valueSetCode,
		String valueSetNm,
		String codeSetDescTxt

) {
}
