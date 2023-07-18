package gov.cdc.nbs.questionbank.valueset.request;


public record ValueSetUpdateRequest (
	String codeSetName,
	String valueSetNm,
	String codeSetDescTxt

) {}
