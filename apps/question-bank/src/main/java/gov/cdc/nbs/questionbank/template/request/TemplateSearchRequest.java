package gov.cdc.nbs.questionbank.template.request;

import java.util.List;


import lombok.Data;

@Data
public class TemplateSearchRequest {
	private Long id;
	private String templateNm;
	private List<String> templateType;
	private String conditionCd;
	private String dataMartNm;
	private String recordStatusCd;

}
