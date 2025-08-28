package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.Identifiable;
import gov.cdc.nbs.entity.odse.PersonNameId;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;

@Component
public class PatientNameHistoryListener {

  private final PatientNameHistoryRecorder creator;

  PatientNameHistoryListener(final PatientNameHistoryRecorder creator) {
    this.creator = creator;
  }

  @PreUpdate
  void preUpdate(final Identifiable<PersonNameId> updated) {
    this.creator.snapshot(updated.identifier());
  }

}
