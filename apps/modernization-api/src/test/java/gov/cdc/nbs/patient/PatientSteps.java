package gov.cdc.nbs.patient;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.TestActive;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class PatientSteps {

    @Autowired
    TestActive<PatientIdentifier> patient;

    @Autowired
    PatientMother mother;

    //  Make sure that patients are cleaned up after everything else
    @Before(order = 15000)
    public void clean() {
        mother.reset();
    }

    @Given("I have a patient")
    @Given("I have another patient")
    public void i_have_a_patient() {
        mother.create();
    }

    @Given("the patient is inactive")
    public void the_patient_is_inactive() {
        mother.deleted(patient.active());
    }

}
