package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.Identifiable;
import gov.cdc.nbs.entity.odse.PersonEthnicGroupId;
import org.springframework.stereotype.Component;

import jakarta.persistence.PreRemove;

@Component
public class PatientEthnicityHistoryListener {

    private final PatientEthnicityHistoryRecorder creator;

    PatientEthnicityHistoryListener(final PatientEthnicityHistoryRecorder creator) {
        this.creator = creator;
    }

    @PreRemove
    void preRemove(final Identifiable<PersonEthnicGroupId> removed) {
        this.creator.snapshot(removed.identifier());

    }


}
