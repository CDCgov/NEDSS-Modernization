package gov.cdc.nbs.patient.search.indexing;

import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.search.SearchablePatient;
import gov.cdc.nbs.search.support.ElasticsearchIndexCleaner;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@ScenarioScope
@Component
public class SearchablePatientMother {

  private final PatientMother mother;
  private final SearchablePatientResolver resolver;
  private final SearchablePatientIndexer indexer;
  private final ElasticsearchIndexCleaner cleaner;
  private final Available<SearchablePatient> available;

  SearchablePatientMother(
      final PatientMother mother,
      final SearchablePatientResolver resolver,
      final SearchablePatientIndexer indexer,
      final ElasticsearchIndexCleaner cleaner,
      final Available<SearchablePatient> available) {
    this.mother = mother;
    this.resolver = resolver;
    this.indexer = indexer;
    this.cleaner = cleaner;
    this.available = available;
  }

  @PreDestroy
  public void shutdown() {
    //  triggered when the bean is destroyed by the Spring IoC container
    this.cleaner.clean("person");
  }

  public void searchable() {
    PatientIdentifier created = this.mother.create();

    this.mother.withAddress(created);
    this.mother.withIdentification(created);
    this.mother.withRace(created);
    this.mother.withName(created);
    this.mother.withPhone(created);
    this.mother.withBirthInformation(created);
    this.mother.withGender(created);
    this.mother.withMortality(created);
    this.mother.withEthnicity(created);
    this.mother.withEmail(created);
  }

  public void searchable(final Stream<PatientIdentifier> identifiers) {

    List<SearchablePatient> patients =
        identifiers
            .map(identifier -> this.resolver.resolve(identifier.id()))
            .flatMap(Optional::stream)
            .toList();

    this.indexer.index(patients);
    this.available.include(patients);
  }
}
