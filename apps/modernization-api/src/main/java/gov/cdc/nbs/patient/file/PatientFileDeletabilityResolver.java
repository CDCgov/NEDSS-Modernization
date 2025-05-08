package gov.cdc.nbs.patient.file;

import gov.cdc.nbs.patient.PatientAssociationCountFinder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
class PatientFileDeletabilityResolver {
  private static final String INACTIVE_STATUS = "INACTIVE";
  private final PatientAssociationCountFinder associationFinder;


  PatientFileDeletabilityResolver(final PatientAssociationCountFinder associationFinder) {
    this.associationFinder = associationFinder;
  }

  PatientDeletability resolve(final PatientFile file) {
    if (Objects.equals(file.status(), INACTIVE_STATUS)) {
      return PatientDeletability.IS_INACTIVE;
    }

    return associationFinder.count(file.id()) == 0
        ? PatientDeletability.DELETABLE
        : PatientDeletability.HAS_ASSOCIATIONS;
  }
}
