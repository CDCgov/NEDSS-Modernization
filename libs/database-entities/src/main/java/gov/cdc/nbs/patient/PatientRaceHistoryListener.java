package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.Identifiable;
import gov.cdc.nbs.entity.odse.PatientRaceId;
import jakarta.persistence.PreRemove;
import org.springframework.stereotype.Component;

@Component
public class PatientRaceHistoryListener {
  private final PatientRaceHistoryRecorder creator;

  PatientRaceHistoryListener(final PatientRaceHistoryRecorder creator) {
    this.creator = creator;
  }

  @PreRemove
  void preRemove(final Identifiable<PatientRaceId> removed) {
    this.creator.snapshot(removed.identifier());
  }
}
