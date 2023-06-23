package gov.cdc.nbs.questionbank.valueset.command;

import java.time.Instant;

import gov.cdc.nbs.questionbank.entity.CodeSetGroupMetadatum;



public sealed interface ValueSetCommand {
	
	record AddValueSet (
	String assigningAuthorityCd,
	String assigningAuthorityDescTxt,
	String codeSetDescTxt,
	Instant effectiveFromTime,
	Instant effectiveToTime,
	Character isModifiableInd,
	Integer nbsUid,
	String sourceVersionTxt,
	String sourceDomainNm,
	String statusCd,
	Instant statusToTime,
	CodeSetGroupMetadatum codeSetGroup,
	String adminComments,
	String valueSetNm,
	Character ldfPicklistIndCd,
	String valueSetCode,
	String valueSetTypeCd,
	String valueSetOid,
	String valueSetStatusCd,
	Instant valueSetStatusTime,
	Long parentIsCd,
	Instant addTime,
	Long addUserId  
    ) implements ValueSetCommand {		
	}
	
	record GetValueSet (

	String classCd,
	String codeSetNm,
	String assigningAuthorityCd,
	String assigningAuthorityDescTxt,
	String codeSetDescTxt,
	Instant effectiveFromTime,
	Instant effectiveToTime,
	Character isModifiableInd,
	Integer nbsUid,
	String sourceVersionTxt,
	String sourceDomainNm,
	String statusCd,
	Instant statusToTime,
	Long codeSetGroupId,
	String adminComments,
	String valueSetNm,
	Character ldfPicklistIndCd,
	String valueSetCode,
	String valueSetTypeCd,
	String valueSetOid,
	String valueSetStatusCd,
	Instant valueSetStatusTime,
	Long parentIsCd,
	Instant addTime,
	Long addUserId
			
			
	) implements ValueSetCommand {
		
	}
	
	record AddCodesetGroupMetadatum (
	String codeSetNm,
	String vadsValueSetCode,
	String codeSetDescTxt,
	String codeSetShortDescTxt,
	Character ldfPicklistIndCd,
	Character phinStdValInd
	) {
		
	}

	
	

}
