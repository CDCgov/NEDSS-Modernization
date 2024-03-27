package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.EntityLocatorParticipation;
import org.springframework.stereotype.Component;

import jakarta.persistence.PreUpdate;

@Component
public class PatientEntityLocatorHistoryListener {
    private final PatientEntityLocatorHistoryCreator creator;

    public PatientEntityLocatorHistoryListener(PatientEntityLocatorHistoryCreator entityLocatorHistoryCreator) {
        this.creator = entityLocatorHistoryCreator;
    }

    @PreUpdate
    void preUpdate(final EntityLocatorParticipation entityLocatorParticipation) {
        int version = entityLocatorParticipation.getVersionCtrlNbr() - 1;
        this.creator.createEntityLocatorHistory(entityLocatorParticipation.getId().getEntityUid(),
                entityLocatorParticipation.getId().getLocatorUid(), version);
    }
}
