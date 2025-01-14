package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.PatientHistoryCreator;
import gov.cdc.nbs.patient.PatientNotFoundException;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class PatientProfileService {

  private final EntityManager entityManager;
  private final PatientHistoryCreator historyCreator;

  PatientProfileService(
      final EntityManager entityManager,
      final PatientHistoryCreator historyCreator
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
      long before = PatientChangeHash.compute(patient);
      consumer.andThen(resolveHistory(before)).accept(patient);
    } else {
      throw new PatientNotFoundException(identifier);
    }
  }

  private Consumer<Person> resolveHistory(final long before) {
    return updated -> maybeRecordHistory(before, updated);
  }

  private void maybeRecordHistory(final long before, final Person patient) {
    long after = PatientChangeHash.compute(patient);

    if (before != after) {
      int version = patient.getVersionCtrlNbr() - 1;
      this.historyCreator.create(patient.getId(), version);

    }
  }

  @Transactional
  public <M> Optional<M> with(final long identifier, final Function<Person, M> fn) {
    Person patient = this.entityManager.find(Person.class, identifier);
    return patient != null
        ? with(patient, fn)
        : Optional.empty();
  }

  private <M> Optional<M> with(final Person patient, final Function<Person, M> fn) {
    long before = PatientChangeHash.compute(patient);

    M mapped = fn.apply(patient);

    maybeRecordHistory(before, patient);

    return Optional.ofNullable(mapped);
  }
}
