package gov.cdc.nbs.entity.projections;

import java.time.Instant;

public interface OrganizationParticipation2 {
    Long getActUid();

    String getTypeCd();

    Long getEntityId();

    String getSubjectClassCd();

    String getTypeDescTxt();

    String getParticipationRecordStatus();

    Instant getParticipationLastChangeTime();

    String getName();

    Instant getOrganizationLastChangeTime();
}
