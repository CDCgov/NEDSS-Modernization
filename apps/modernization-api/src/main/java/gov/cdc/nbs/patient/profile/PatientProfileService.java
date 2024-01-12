package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.PatientNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.function.Consumer;

@Component
public class PatientProfileService {

  private final EntityManager entityManager;

  public PatientProfileService(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  /**
   * Runs the {@code consumer} if an entity can be uniquely identified by the given {@code identifier}.
   *
   * @param identifier The unique identifier of the Page
   * @param consumer   A consumer that will run on the found Page
   */
  @Transactional
  public void using(final long identifier, final Consumer<Person> consumer) {
    Person page = this.entityManager.find(Person.class, identifier);
    if (page != null) {
      consumer.accept(page);
    } else {
      throw new PatientNotFoundException(identifier);
    }
  }

}
