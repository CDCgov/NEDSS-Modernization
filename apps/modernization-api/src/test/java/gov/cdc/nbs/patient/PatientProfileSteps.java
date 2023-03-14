package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.identity.TestUniqueIdGenerator;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.support.PersonMother;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class PatientProfileSteps {

  @Autowired
  PersonRepository repository;

  @Autowired
  TestPatients testPatients;

  @Autowired
  TestUniqueIdGenerator idGenerator;

  @Autowired
  TestPatientCleaner cleaner;

  @Before
  public void clean() {
    cleaner.clean(idGenerator.starting());
    testPatients.reset();
  }

  @Given("I have a patient")
  public void i_have_a_patient() {
    Person patient = PersonMother.generateRandomPerson(idGenerator.next());
    repository.save(patient);
    testPatients.available(patient.getId());
  }

}
