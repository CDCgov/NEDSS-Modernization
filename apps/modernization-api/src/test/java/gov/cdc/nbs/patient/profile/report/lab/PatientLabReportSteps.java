package gov.cdc.nbs.patient.profile.report.lab;

import gov.cdc.nbs.patient.TestPatients;
import io.cucumber.java.Before;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class PatientLabReportSteps {

    @Autowired
    TestPatients patients;

    @Autowired
    LabReportMother mother;

    @Before
    public void clean() {
        mother.reset();
    }

    @When("the patient has a lab Report")
    public void the_patient_has_a_lab_report() {
        long patient =patients.one();

        mother.labReport(patient);
    }
}
