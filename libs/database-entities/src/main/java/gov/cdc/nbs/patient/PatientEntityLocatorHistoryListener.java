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
    @SuppressWarnings(
        //  The PatientEntityLocatorHistoryListener is an entity listener specifically for instances of EntityLocatorParticipation
        {"javaarchitecture:S7027", "javaarchitecture:S7091"}
    )
    void preUpdate(final EntityLocatorParticipation entityLocatorParticipation) {
        int version = entityLocatorParticipation.getVersionCtrlNbr() - 1;
        this.creator.createEntityLocatorHistory(entityLocatorParticipation.getId().getEntityUid(),
                entityLocatorParticipation.getId().getLocatorUid(), version);
    }
}
