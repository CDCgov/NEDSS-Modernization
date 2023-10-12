package gov.cdc.nbs.patient;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.Available;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TestPatients {

    private final Available<PatientIdentifier> patients;

    public TestPatients(final Available<PatientIdentifier> patients) {
        this.patients = patients;
    }

    public Optional<Long> maybeOne() {
        return this.patients.maybeOne().map(PatientIdentifier::id);
    }

    public long one() {
        return maybeOne().orElseThrow(() -> new IllegalStateException("there is no patient"));
    }

}
