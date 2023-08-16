package gov.cdc.nbs.entity.projections;

import java.time.Instant;

public interface ActId2 {
    Long getActUid();

    String getAddReasonCd();

    Instant getAddTime();

    Long getAddUserId();

    String getAssigningAuthorityCd();

    String getAssigningAuthorityDescTxt();

    String getDurationAmt();

    String getDurationUnitCd();

    String getLastChgReasonCd();

    Instant getLastChgTime();

    Long getLastChgUserId();

    String getRecordStatusCd();

    Instant getRecordStatusTime();

    String getRootExtensionTxt();

    Character getStatusCd();

    Instant getStatusTime();

    String getTypeCd();

    String getTypeDescTxt();

    String getUserAffiliationTxt();

    Instant getValidFromTime();

    Instant getValidToTime();
}
