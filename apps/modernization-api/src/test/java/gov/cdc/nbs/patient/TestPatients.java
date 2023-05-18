package gov.cdc.nbs.patient;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.TestAvailable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TestPatients {

    private final TestAvailable<PatientIdentifier> patients;

    public TestPatients(final TestAvailable<PatientIdentifier> patients) {
        this.patients = patients;
    }

    public Optional<Long> maybeOne() {
        return this.patients.maybeOne().map(PatientIdentifier::id);
    }

    public long one() {
        return maybeOne().orElseThrow(() -> new IllegalStateException("there is no patient"));
    }

}
