package gov.cdc.nbs.questionbank.template.request;

import java.util.List;





public record TemplateSearchRequest (
	Long id,
	String templateNm,
	List<String> templateType,
	String conditionCd,
	String dataMartNm,
	String recordStatusCd
	
	) {}


