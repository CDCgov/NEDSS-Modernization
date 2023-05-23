package gov.cdc.nbs.patientlistener.request.delete;

import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.patientlistener.request.PatientRequestException;
import org.springframework.stereotype.Component;

@Component
class PatientAssociationCheck {

    private final PatientAssociationCountFinder finder;

    PatientAssociationCheck(final PatientAssociationCountFinder finder) {
        this.finder = finder;
    }

    void check(final PatientRequest.Delete request) {
        // do not allow to delete if there are "Active Revisions"
        long associations = finder.count(request.patientId());
        if (associations > 0) {
            throw new PatientRequestException("Cannot delete patient with Active Revisions", request.requestId());
        }
    }
}
