package gov.cdc.nbs.testing.patient;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import org.springframework.stereotype.Component;

@Component
public class RevisionMother {
  private final RevisionPatientCreator revisionCreator;

  RevisionMother(final RevisionPatientCreator revisionCreator) {
    this.revisionCreator = revisionCreator;
  }

  public PatientIdentifier revise(final PatientIdentifier identifier) {
    return this.revisionCreator.revise(identifier);
  }
}
