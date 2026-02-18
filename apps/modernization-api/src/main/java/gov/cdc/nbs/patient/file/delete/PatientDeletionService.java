package gov.cdc.nbs.patient.file.delete;

import gov.cdc.nbs.patient.PatientAssociationCountFinder;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientException;
import gov.cdc.nbs.patient.PatientService;
import gov.cdc.nbs.patient.RequestContext;
import org.springframework.stereotype.Component;

@Component
class PatientDeletionService {

  private final PatientAssociationCountFinder finder;
  private final PatientService service;

  PatientDeletionService(final PatientAssociationCountFinder finder, final PatientService service) {
    this.finder = finder;
    this.service = service;
  }

  void delete(final RequestContext context, final long patient) throws PatientException {

    this.service.using(
        patient,
        found ->
            found.delete(
                new PatientCommand.Delete(patient, context.requestedBy(), context.requestedAt()),
                finder));
  }
}
