package gov.cdc.nbs.patient;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class PatientSteps {

    @Autowired
    PatientMother mother;

    //  Make sure that patients are cleaned up after everything else
    @Before(order = 15000)
    public void clean() {
        mother.reset();
    }

    @Given("I have a patient")
    public void i_have_a_patient() {
        mother.create();
    }

}
