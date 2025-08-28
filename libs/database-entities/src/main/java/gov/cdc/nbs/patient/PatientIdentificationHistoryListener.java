package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.EntityIdId;
import gov.cdc.nbs.entity.odse.Identifiable;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;

@Component
public class PatientIdentificationHistoryListener {

  private final PatientIdentificationHistoryRecorder creator;

  PatientIdentificationHistoryListener(final PatientIdentificationHistoryRecorder creator) {
    this.creator = creator;
  }

  @PreUpdate
  void preUpdate(final Identifiable<EntityIdId> updated) {
    this.creator.snapshot(updated.identifier());
  }

}
