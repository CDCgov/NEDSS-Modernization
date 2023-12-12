package gov.cdc.nbs.entity.projections;

import java.time.Instant;

public interface AssociatedInvestigation2 {
    Long getPublicHealthCaseUid();

    Instant getLastChgTime();

    String getCdDescTxt();

    String getLocalId();

    Instant getActRelationshipLastChgTime();
}
