package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.support.Active;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Optional;

@Component
public class TestPatient extends Active<Person> {

    private final EntityManager entityManager;

    public TestPatient(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Person> maybeManaged() {
        return this.maybeActive().map(this::asManaged);
    }

    public Person managed() {
        return this.asManaged(this.active());
    }

    private Person asManaged(final Person person) {
        return this.entityManager.find(Person.class, person.getId());
    }

}
