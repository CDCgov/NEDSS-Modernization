package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
public class PatientSearchIndexer {

    private final EntityManager entityManager;
    private final ElasticsearchPersonRepository repository;

    public PatientSearchIndexer(
        final EntityManager entityManager,
        final ElasticsearchPersonRepository repository
    ) {
        this.entityManager = entityManager;
        this.repository = repository;
    }

    @Transactional
    public void index(final long patient) {
        Person person = this.entityManager.find(Person.class, patient);

        ElasticsearchPerson searchable = SearchablePatientConverter.toSearchable(person);

        this.repository.save(searchable);
    }

}
