package gov.cdc.nbs.entity.projections;

import java.time.Instant;

public interface LabReport2 {
    String getId();

    Long getObservationUid();

    String getClassCd();

    String getMoodCd();

    Instant getLastChange();

    Instant getObservationLastChgTime();

    String getCdDescTxt();

    String getRecordStatusCd();

    String getProgramAreaCd();

    Long getJurisdictionCd();

    String getJurisdictionCodeDescTxt();

    String getPregnantIndCd();

    String getLocalId();

    Instant getActivityToTime();

    Instant getEffectiveFromTime();

    Instant getRptToStateTime();

    Instant getAddTime();

    String getElectronicInd();

    Long getVersionCtrlNbr();

    Long getAddUserId();

    Long getLastChgUserId();
}
