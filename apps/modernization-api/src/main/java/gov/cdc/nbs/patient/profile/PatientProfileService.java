package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.PatientNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class PatientProfileService {

  private final EntityManager entityManager;

  public PatientProfileService(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  /**
   * Runs the {@code consumer} if an entity can be uniquely identified by the
   * given {@code identifier}.
   *
   * @param identifier The unique identifier of the Page
   * @param consumer   A consumer that will run on the found Page
   */
  @Transactional
  public void using(final long identifier, final Consumer<Person> consumer) {
    Person patient = this.entityManager.find(Person.class, identifier);
    if (patient != null) {
      consumer.accept(patient);
    } else {
      throw new PatientNotFoundException(identifier);
    }
  }

  public Person findPatientById(long patient) throws PatientNotFoundException {
    return this.entityManager.find(Person.class, patient);
  }

  @Transactional
  public <M> Optional<M> with(final long identifier, final Function<Person, M> fn) {
    Person patient = this.entityManager.find(Person.class, identifier);
    return patient != null
        ? Optional.of(fn.apply(patient))
        : Optional.empty();
  }
}
