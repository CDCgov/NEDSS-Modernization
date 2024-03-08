package gov.cdc.nbs.event.search.labreport.indexing;

import org.springframework.stereotype.Component;

@Component
public class LabReportIndexer {

  private final SearchableLabReportResolver resolver;
  private final SearchableLabReportIndexer indexer;

  LabReportIndexer(
      final SearchableLabReportResolver resolver,
      final SearchableLabReportIndexer indexer
  ) {
    this.resolver = resolver;
    this.indexer = indexer;
  }

  public void index(final long lab) {
    this.resolver.resolve(lab).ifPresent(this.indexer::index);
  }

}
