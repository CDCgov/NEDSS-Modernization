package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.EntityLocatorParticipationId;
import gov.cdc.nbs.entity.odse.Identifiable;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;

@Component
public class PatientEntityLocatorHistoryListener {
  private final PatientEntityLocatorHistoryRecorder creator;

  PatientEntityLocatorHistoryListener(final PatientEntityLocatorHistoryRecorder entityLocatorHistoryCreator) {
    this.creator = entityLocatorHistoryCreator;
  }

  @PreUpdate
  void preUpdate(final Identifiable<EntityLocatorParticipationId> updated) {
    this.creator.snapshot(updated.identifier());
  }
}
