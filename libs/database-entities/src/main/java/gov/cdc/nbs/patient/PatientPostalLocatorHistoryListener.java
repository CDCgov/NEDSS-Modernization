package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.EntityLocatorParticipationId;
import gov.cdc.nbs.entity.odse.Identifiable;
import org.springframework.stereotype.Component;

import jakarta.persistence.PreUpdate;

@Component
public class PatientPostalLocatorHistoryListener {
    private final PatientPostalLocatorHistoryRecorder creator;

    PatientPostalLocatorHistoryListener(final PatientPostalLocatorHistoryRecorder creator) {
        this.creator = creator;
    }

    @PreUpdate
    void preUpdate(final Identifiable<EntityLocatorParticipationId> updated) {
       this.creator.snapshot(updated.identifier());
    }


}
