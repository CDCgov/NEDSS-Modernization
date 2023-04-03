package gov.cdc.nbs.investigation;

import gov.cdc.nbs.patient.TestPatients;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class InvestigationSteps {

  @Autowired
  TestPatients patients;

  @Autowired
  InvestigationMother mother;

  @Before
  public void clean() {
    mother.reset();
  }

  @Given("the patient is a subject of an investigation")
  public void the_patient_is_a_subject_of_an_investigation() {
    mother.investigation(patients.one());

  }

}
