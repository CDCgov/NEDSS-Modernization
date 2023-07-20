package gov.cdc.nbs.questionbank.template.response;

import java.time.Instant;

public record Template(
	Long id,
	String templateNm,
	String templateType,
	String recordStatusCd,
	Instant lastChgTime,
	Long lastChgUserId,
	String descTxt,
	Long parentTemplateUid,
	String sourceNm
		
		) {
	

}
