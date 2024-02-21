package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.EntityLocatorParticipation;
import org.springframework.stereotype.Component;

import javax.persistence.PreUpdate;

@Component
public class PatientAddressEntityLocatorListener {
    private final PatientAddressEntityLocatorHistoryCreator creator;

    public PatientAddressEntityLocatorListener(PatientAddressEntityLocatorHistoryCreator entityLocatorHistoryCreator, PatientAddressPostalLocatorHistoryCreator postalLocatorHistoryCreator) {
        this.creator = entityLocatorHistoryCreator;
    }

    @PreUpdate
    void preUpdate(final EntityLocatorParticipation entityLocatorParticipation) {
        int version = entityLocatorParticipation.getVersionCtrlNbr() - 1;
        this.creator.createEntityLocatorHistory(entityLocatorParticipation.getId().getEntityUid(), entityLocatorParticipation.getId().getLocatorUid(), version);
    }
}
