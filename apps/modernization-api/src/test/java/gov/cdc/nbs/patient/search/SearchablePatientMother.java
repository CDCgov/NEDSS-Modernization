package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.search.indexing.PatientSearchIndexer;
import gov.cdc.nbs.support.search.ElasticsearchIndexCleaner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
class SearchablePatientMother {


  private final PatientMother mother;
  private final PatientSearchIndexer indexer;
  private final ElasticsearchIndexCleaner cleaner;
  private final EntityManager entityManager;

  SearchablePatientMother(
      final PatientMother mother,
      final PatientSearchIndexer indexer,
      final ElasticsearchIndexCleaner cleaner,
      final EntityManager entityManager
  ) {
    this.mother = mother;
    this.indexer = indexer;
    this.cleaner = cleaner;
    this.entityManager = entityManager;
  }

  void reset() {
    this.cleaner.clean("person");
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

    this.indexer.index(searchable.id());

    return person;
  }

  Person searchable(final PatientIdentifier identifier) {
    Person person = this.entityManager.find(Person.class, identifier.id());

    this.indexer.index(identifier.id());
    return person;
  }

}
