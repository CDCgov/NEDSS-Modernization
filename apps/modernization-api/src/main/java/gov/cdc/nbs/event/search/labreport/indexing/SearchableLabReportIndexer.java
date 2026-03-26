package gov.cdc.nbs.event.search.labreport.indexing;

import gov.cdc.nbs.event.search.labreport.SearchableLabReport;
import gov.cdc.nbs.search.ElasticsearchSimpleDocumentIndexer;
import gov.cdc.nbs.search.SimpleDocument;
import java.util.Collection;
import org.springframework.stereotype.Component;

@Component
public class SearchableLabReportIndexer {

  private static final String INDEX = "lab_report";
  private final ElasticsearchSimpleDocumentIndexer indexer;

  SearchableLabReportIndexer(final ElasticsearchSimpleDocumentIndexer indexer) {
    this.indexer = indexer;
  }

  public void index(final SearchableLabReport item) {
    this.indexer.index(INDEX, convert(item));
  }

  public void index(final Collection<SearchableLabReport> items) {
    this.indexer.index(INDEX, items.stream().map(SearchableLabReportIndexer::convert));
  }

  private static SimpleDocument convert(final SearchableLabReport searchable) {
    String identifier = String.valueOf(searchable.identifier());
    return new SimpleDocument(identifier, searchable);
  }
}
