package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.EntityLocatorParticipationId;
import gov.cdc.nbs.entity.odse.Identifiable;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;

@Component
public class PatientPhoneLocatorHistoryListener {

  private final PatientPhoneLocatorHistoryRecorder creator;

  PatientPhoneLocatorHistoryListener(final PatientPhoneLocatorHistoryRecorder creator) {
    this.creator = creator;
  }

  @PreUpdate
  void preUpdate(final Identifiable<EntityLocatorParticipationId> updated) {
    this.creator.snapshot(updated.identifier());
  }
}
