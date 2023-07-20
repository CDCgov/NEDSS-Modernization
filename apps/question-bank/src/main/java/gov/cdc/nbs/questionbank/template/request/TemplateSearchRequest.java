package gov.cdc.nbs.questionbank.template.request;

import java.util.List;


import lombok.Data;


public record TemplateSearchRequest (
	Long id,
	String templateNm,
	List<String> templateType,
	String conditionCd,
	String dataMartNm,
	String recordStatusCd
	
	) {}


