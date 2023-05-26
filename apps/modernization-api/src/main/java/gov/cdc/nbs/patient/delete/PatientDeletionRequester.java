package gov.cdc.nbs.patient.delete;

import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.model.PatientEventResponse;
import gov.cdc.nbs.patient.PatientEventRequester;
import gov.cdc.nbs.patient.PatientRequestIdGenerator;
import org.springframework.stereotype.Component;

@Component
class PatientDeletionRequester {


    private final PatientRequestIdGenerator generator;
    private final PatientEventRequester requester;

    PatientDeletionRequester(
        final PatientRequestIdGenerator generator,
        final PatientEventRequester requester
    ) {
        this.generator = generator;
        this.requester = requester;
    }

    PatientEventResponse request(final long patient) {
        var user = SecurityUtil.getUserDetails();
        String identifier = generator.generate("patient-delete");
        var deleteEvent = new PatientRequest.Delete(identifier, patient, user.getId());
        return requester.request(deleteEvent);
    }
}
