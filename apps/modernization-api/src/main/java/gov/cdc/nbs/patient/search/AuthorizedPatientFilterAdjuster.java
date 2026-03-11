package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.entity.enums.RecordStatus;
import java.util.Collection;
import java.util.Objects;

class AuthorizedPatientFilterAdjuster {

  private final Permission permission;

  AuthorizedPatientFilterAdjuster(final Permission permission) {
    this.permission = Objects.requireNonNull(permission, "A Permission is required");
  }

  PatientSearchCriteria adjusted(final NbsUserDetails user, final PatientSearchCriteria filter) {
    return requiresAdjustment(user, filter.getRecordStatus())
        ? filter.adjustStatuses(withoutInactiveStatus(filter.getRecordStatus()))
        : filter;
  }

  private boolean requiresAdjustment(
      final NbsUserDetails user, final Collection<RecordStatus> selected) {
    // If LOG_DEL or SUPERCEDED are specified, user must have FINDINACTIVE-PATIENT
    // authority
    return (selected.contains(RecordStatus.SUPERCEDED) || selected.contains(RecordStatus.LOG_DEL))
        && (!user.hasPermission(permission));
  }

  private Collection<RecordStatus> withoutInactiveStatus(final Collection<RecordStatus> selected) {
    return selected.stream()
        .filter(s -> !s.equals(RecordStatus.SUPERCEDED) && !s.equals(RecordStatus.LOG_DEL))
        .toList();
  }
}
