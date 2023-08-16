package gov.cdc.nbs.entity.projections;

import java.time.Instant;

public interface OrganizationParticipation2 {
    Long getActUid();

    String getTypeCd();

    Long getSubjectEntityUid();

    String getSubjectClassCd();

    String getRecordStatus();

    String getTypeDescTxt();

    Instant getLastChgTime();

    String getDisplayNm();

    Instant getOrgLastChgTime();
}
