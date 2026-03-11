package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.exception.QueryException;
import java.util.List;

class PatientFilterValidator {

  static PatientSearchCriteria validate(final PatientSearchCriteria filter) {
    List<RecordStatus> selected = filter.getRecordStatus();
    if (selected == null || selected.isEmpty()) {
      throw new QueryException("At least one Status is required");
    }

    if (filter.adjustedStatus().isEmpty()) {
      // User selected either SUPERCEDED or LOG_DEL and lacks the permission.
      throw new QueryException("User does not have permission to search for Inactive Patients");
    }

    return filter;
  }

  private PatientFilterValidator() {}
}
