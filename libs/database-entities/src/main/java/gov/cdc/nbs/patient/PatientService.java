package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.Person;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Component
public class PatientService {

  private final EntityManager entityManager;
  private final PatientHistoryRecorder historyCreator;

  PatientService(
      final EntityManager entityManager,
      final PatientHistoryRecorder historyCreator
  ) {
    this.entityManager = entityManager;
    this.historyCreator = historyCreator;
  }

  /**
   * Runs the {@code consumer} if an entity can be uniquely identified by the given {@code identifier}.
   *
   * @param identifier The unique identifier of the Page
   * @param consumer   A consumer that will run on the found Page
   */
  @Transactional
  public void using(final long identifier, final Consumer<Person> consumer) {
    Person patient = this.entityManager.find(Person.class, identifier);
    if (patient != null) {
      safely(patient, consumer);
    } else {
      throw new PatientNotFoundException(identifier);
    }
  }

  private void safely(final Person patient, final Consumer<Person> consumer) {
    try {
      long before = patient.signature();
      consumer.andThen(resolveHistory(before)).accept(patient);
    } catch (Throwable throwable) {
      throw new PatientException(patient.id(), "Unable to apply changes to patient %d".formatted(patient.id()));
    }
  }

  private Consumer<Person> resolveHistory(final long before) {
    return updated -> maybeRecordHistory(before, updated);
  }

  private void maybeRecordHistory(final long before, final Person patient) {
    long after = patient.signature();

    if (before != after) {
      this.historyCreator.snapshot(patient.getId());
    }
  }

}
