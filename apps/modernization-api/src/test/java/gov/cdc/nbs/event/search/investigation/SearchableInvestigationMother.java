package gov.cdc.nbs.event.search.investigation;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.event.search.investigation.indexing.SearchableInvestigationIndexer;
import gov.cdc.nbs.event.search.investigation.indexing.SearchableInvestigationResolver;
import gov.cdc.nbs.search.support.ElasticsearchIndexCleaner;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
class SearchableInvestigationMother {

  private final ElasticsearchIndexCleaner cleaner;
  private final SearchableInvestigationResolver resolver;
  private final SearchableInvestigationIndexer indexer;
  private final Available<SearchableInvestigation> available;

  SearchableInvestigationMother(
      final ElasticsearchIndexCleaner cleaner,
      final SearchableInvestigationResolver resolver,
      final SearchableInvestigationIndexer indexer,
      final Available<SearchableInvestigation> available) {
    this.cleaner = cleaner;
    this.resolver = resolver;
    this.indexer = indexer;
    this.available = available;
  }

  @PostConstruct
  void shutdown() {
    this.cleaner.clean("investigation");
  }

  void searchable(final InvestigationIdentifier identifier) {
    this.resolver
        .resolve(identifier.identifier())
        .ifPresent(
            created -> {
              this.indexer.index(created);
              this.available.available(created);
            });
  }

  void searchable(final Stream<InvestigationIdentifier> identifiers) {

    List<SearchableInvestigation> reports =
        identifiers
            .map(identifier -> this.resolver.resolve(identifier.identifier()))
            .flatMap(Optional::stream)
            .toList();

    this.indexer.index(reports);
    this.available.include(reports);
  }
}
