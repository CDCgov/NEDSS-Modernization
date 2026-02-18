package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.event.report.lab.LabReportIdentifier;
import gov.cdc.nbs.event.search.labreport.indexing.SearchableLabReportIndexer;
import gov.cdc.nbs.event.search.labreport.indexing.SearchableLabReportResolver;
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
class SearchableLabReportMother {

  private final ElasticsearchIndexCleaner cleaner;
  final SearchableLabReportResolver resolver;
  final SearchableLabReportIndexer indexer;
  private final Available<SearchableLabReport> available;

  SearchableLabReportMother(
      final ElasticsearchIndexCleaner cleaner,
      final SearchableLabReportResolver resolver,
      final SearchableLabReportIndexer indexer,
      final Available<SearchableLabReport> available) {
    this.cleaner = cleaner;
    this.resolver = resolver;
    this.indexer = indexer;
    this.available = available;
  }

  @PostConstruct
  void shutdown() {
    this.cleaner.clean("lab_report");
  }

  void searchable(final LabReportIdentifier identifier) {
    this.resolver
        .resolve(identifier.identifier())
        .ifPresent(
            created -> {
              this.indexer.index(created);
              this.available.available(created);
            });
  }

  void searchable(final Stream<LabReportIdentifier> identifiers) {

    List<SearchableLabReport> reports =
        identifiers
            .map(identifier -> this.resolver.resolve(identifier.identifier()))
            .flatMap(Optional::stream)
            .toList();

    this.indexer.index(reports);
    this.available.include(reports);
  }
}
