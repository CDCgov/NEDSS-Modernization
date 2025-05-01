package gov.cdc.nbs.patient.file.delete;

import gov.cdc.nbs.patient.PatientAssociationCountFinder;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientException;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.profile.PatientProfileService;
import org.springframework.stereotype.Component;

@Component
class PatientDeleter {

  private final PatientAssociationCountFinder finder;
  private final PatientProfileService service;

  PatientDeleter(
      final PatientAssociationCountFinder finder,
      final PatientProfileService service
  ) {
    this.finder = finder;
    this.service = service;
  }

  void delete(
      final RequestContext context,
      final long patient
  ) throws PatientException {

    this.service.using(
        patient,
        found -> found.delete(
            new PatientCommand.Delete(
                patient,
                context.requestedBy(),
                context.requestedAt()
            ),
            finder
        )
    );

  }



}
