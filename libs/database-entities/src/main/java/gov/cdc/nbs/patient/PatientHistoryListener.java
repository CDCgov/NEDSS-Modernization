package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.Person;
import org.springframework.stereotype.Component;

import javax.persistence.PreUpdate;

@Component
public class PatientHistoryListener {

  private final PatientHistoryCreator creator;

  public PatientHistoryListener(final PatientHistoryCreator creator) {
    this.creator = creator;
  }

  @PreUpdate
  void preUpdate(final Person person) {
    int version = person.getVersionCtrlNbr() - 1;
    this.creator.create(person.getId(), version);
  }
}
