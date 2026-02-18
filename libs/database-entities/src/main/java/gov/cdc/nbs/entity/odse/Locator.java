package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.audit.RecordStatus;
import gov.cdc.nbs.patient.PatientCommand;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Locator {

  @Embedded private Audit audit;

  @Embedded private RecordStatus recordStatus;

  protected Locator() {}

  protected Locator(final PatientCommand command) {
    this.audit = new Audit(command.requester(), command.requestedOn());
    this.recordStatus = new RecordStatus(command.requestedOn());
  }

  protected void changed(final PatientCommand command) {
    if (this.audit == null) {
      this.audit = new Audit(command.requester(), command.requestedOn());
    }

    this.audit.changed(command.requester(), command.requestedOn());
  }

  public Audit audit() {
    return audit;
  }

  public RecordStatus recordStatus() {
    return recordStatus;
  }
}
