package gov.cdc.nbs.questionbank.valueset.response;

import java.time.Instant;

public record ValueSet(
        String classCd,
        String name,
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
        Long addUserId) {
}
