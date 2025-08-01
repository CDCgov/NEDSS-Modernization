package gov.cdc.nbs.patient.file.events.contact;

import gov.cdc.nbs.authorization.permission.scope.PermissionScope;

import java.util.List;

public interface PatientFileContactFinder {
  List<PatientFileContacts> find(
      long patient,
      PermissionScope scope,
      PermissionScope associatedScope
  );
}
