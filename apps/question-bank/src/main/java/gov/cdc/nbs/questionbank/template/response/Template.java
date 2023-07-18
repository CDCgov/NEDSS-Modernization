package gov.cdc.nbs.questionbank.template.response;

import java.time.Instant;

public record Template(
	Long id,
	String templateNm,
	String templateType,
	String xmlPayload,
	Integer publishVersionNbr,
	String formCd,
	String conditionCd,
	String busObjType,
	String datamartNm,
	String recordStatusCd,
	Instant recordStatusTime,
	Instant lastChgTime,
	Long lastChgUserId,
	String localId,
	String descTxt,
	Character publishIndCd,
	Instant addTime,
	Long addUserId,
	String nndEntityIdentifier,
	Long parentTemplateUid,
	String sourceNm,
	Integer templateVersionNbr,
	String versionNote	
		
		) {
	

}
