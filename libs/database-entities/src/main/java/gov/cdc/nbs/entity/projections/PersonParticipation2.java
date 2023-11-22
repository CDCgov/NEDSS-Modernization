package gov.cdc.nbs.entity.projections;

import java.time.Instant;

public interface PersonParticipation2 {
    Long getActUid();

    String getTypeCd();

    Long getEntityId();

    String getSubjectClassCd();

    String getParticipationRecordStatus();

    Instant getLastChgTime();

    String getTypeDescTxt();

    String getFirstName();

    String getLastName();

    String getLocalId();

    Instant getBirthTime();

    String getCurrSexCd();

    String getPersonCd();

    Long getPersonParentUid();

    String getPersonRecordStatus();

    Instant getPersonLastChgTime();
}
