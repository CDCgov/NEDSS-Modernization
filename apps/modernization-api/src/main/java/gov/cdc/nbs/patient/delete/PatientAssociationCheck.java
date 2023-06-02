package gov.cdc.nbs.patient.delete;

import org.springframework.stereotype.Component;

@Component
class PatientAssociationCheck {

    private final PatientIsDeletableResolver resolver;

    PatientAssociationCheck(final PatientIsDeletableResolver resolver) {
        this.resolver = resolver;
    }

    void check(final long patient) throws PatientHasAssociatedEventsException {
        if (!this.resolver.canDelete(patient)) {
            throw new PatientHasAssociatedEventsException(patient);
        }
    }
}
