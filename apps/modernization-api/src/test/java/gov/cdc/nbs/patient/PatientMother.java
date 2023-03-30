package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.identity.TestUniqueIdGenerator;
import gov.cdc.nbs.support.PersonMother;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class PatientMother {

  private final MotherSettings settings;
  private final TestUniqueIdGenerator idGenerator;
  private final EntityManager entityManager;
  private final TestPatients patients;
  private final TestPatientCleaner cleaner;

  public PatientMother(
      final MotherSettings settings,
      final TestUniqueIdGenerator idGenerator,
      final EntityManager entityManager,
      final TestPatients patients,
      final TestPatientCleaner cleaner
  ) {
    this.settings = settings;
    this.idGenerator = idGenerator;
    this.entityManager = entityManager;
    this.patients = patients;
    this.cleaner = cleaner;
  }

  void reset() {
    this.cleaner.clean(settings.starting());
    this.patients.reset();
  }

  public Person patient() {
    Person patient = PersonMother.generateRandomPerson(idGenerator.next());

    this.entityManager.persist(patient);

    patients.available(patient.getId());
    return patient;
  }
}
