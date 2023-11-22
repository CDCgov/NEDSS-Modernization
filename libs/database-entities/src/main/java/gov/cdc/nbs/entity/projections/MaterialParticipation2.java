package gov.cdc.nbs.entity.projections;

import java.time.Instant;

public interface MaterialParticipation2 {
    Long getActUid();

    String getTypeCd();

    Long getSubjectEntityId();

    String getSubjectClassCd();

    String getRecordStatus();

    String getTypeDescTxt();

    Instant getLastChgTime();

    String getCd();

    String getCdDescTxt();
}
