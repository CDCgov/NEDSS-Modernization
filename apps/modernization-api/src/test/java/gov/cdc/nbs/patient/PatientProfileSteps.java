package gov.cdc.nbs.patient;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class PatientProfileSteps {

  @Autowired
  PatientMother mother;

  @Before
  public void clean() {
    mother.reset();
  }

  @Given("I have a patient")
  public void i_have_a_patient() {
    mother.patient();
  }

}
