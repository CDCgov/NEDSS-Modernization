package gov.cdc.nbs.investigation;

import gov.cdc.nbs.entity.odse.PublicHealthCase;
import gov.cdc.nbs.patient.TestPatients;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Transactional
public class InvestigationSteps {

  @Autowired
  TestPatients patients;

  @Autowired
  TestInvestigations investigations;

  @Autowired
  InvestigationMother mother;

  @Autowired
  EntityManager entityManager;

  @Before
  public void clean() {
    mother.reset();
  }

  @Given("the patient is a subject of an investigation")
  public void the_patient_is_a_subject_of_an_investigation() {
    mother.investigation(patients.one());

  }

  @Given("the investigation has been closed")
  public void the_patient_is_a_subject_of_closed_investigation() {
    PublicHealthCase investigation = this.entityManager.find(PublicHealthCase.class, investigations.one());
    investigation.setInvestigationStatusCd("C");

  }
}
