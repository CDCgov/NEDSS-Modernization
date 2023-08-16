package gov.cdc.nbs.entity.projections;

import java.time.Instant;

public interface MaterialParticipation2 {
    Long getActUid();

    String getTypeCd();

    Long getSubjectEntityUid();

    String getSubjectClassCd();

    String getRecordStatus();

    String getTypeDescTxt();

    Instant getLastChgTime();

    String getMaterialCd();

    String getMaterialCdDescTxt();
}
