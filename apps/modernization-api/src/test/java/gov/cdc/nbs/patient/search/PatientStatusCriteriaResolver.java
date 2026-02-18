package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.entity.enums.RecordStatus;

class PatientStatusCriteriaResolver {

  static RecordStatus resolve(final String status) {
    return switch (status.toLowerCase()) {
      case "deleted", "inactive" -> RecordStatus.LOG_DEL;
      case "superseded", "superceded" -> RecordStatus.SUPERCEDED;
      default -> RecordStatus.ACTIVE;
    };
  }

  private PatientStatusCriteriaResolver() {}
}
