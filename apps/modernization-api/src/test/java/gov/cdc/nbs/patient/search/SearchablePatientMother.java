package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
import gov.cdc.nbs.support.util.RandomUtil;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
class SearchablePatientMother {


    private final PatientMother mother;
    private final EntityManager entityManager;

    private final ElasticsearchPersonRepository searchRepository;

    SearchablePatientMother(
        final PatientMother mother,
        final EntityManager entityManager,
        final ElasticsearchPersonRepository searchRepository
    ) {
        this.mother = mother;
        this.entityManager = entityManager;
        this.searchRepository = searchRepository;
    }

    void reset() {
        this.searchRepository.deleteAll();
    }

    Person searchable() {
        PatientIdentifier searchable = this.mother.create();

        this.mother.withAddress(searchable);
        this.mother.withIdentification(searchable);
        this.mother.withRace(searchable);
        this.mother.withName(searchable);
        this.mother.withPhone(searchable);
        this.mother.withBirthInformation(searchable);
        this.mother.withGender(searchable);
        this.mother.withMortality(searchable);
        this.mother.withEthnicity(searchable);
        this.mother.withEmail(searchable);

        Person person = this.entityManager.find(Person.class, searchable.id());
        //  ssn should be in identification
        person.setSsn(RandomUtil.getRandomSsn());

        this.searchRepository.save(SearchablePatientConverter.toSearchable(person));

        return person;
    }

    Person searchable(final PatientIdentifier identifier) {
        Person person = this.entityManager.find(Person.class, identifier.id());

        ElasticsearchPerson searchable = SearchablePatientConverter.toSearchable(person);
        this.searchRepository.save(searchable);

        return person;
    }

}
