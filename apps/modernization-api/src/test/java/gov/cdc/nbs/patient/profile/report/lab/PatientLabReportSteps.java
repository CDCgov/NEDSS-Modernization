package gov.cdc.nbs.patient.profile.report.lab;

import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.TestPatientIdentifier;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import io.cucumber.java.Before;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class PatientLabReportSteps {

    @Autowired
    TestPatientIdentifier patients;

    @Autowired
    PatientMother patientMother;

    @Autowired
    LabReportMother mother;

    @Before
    public void clean() {
        mother.reset();
    }

    @When("the patient has a lab Report")
    public void the_patient_has_a_lab_report() {
        PatientIdentifier revision = patientMother.revise(patients.one());

        mother.labReport(revision.id());
    }
}
