package gov.cdc.nbs.event.search.labreport.indexing;

import gov.cdc.nbs.event.search.labreport.SearchableLabReport;
import gov.cdc.nbs.search.ElasticsearchSimpleDocumentIndexer;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component
public class SearchableLabReportIndexer {

  private static final String INDEX = "lab_report";
  private final ElasticsearchSimpleDocumentIndexer indexer;

  SearchableLabReportIndexer(final ElasticsearchSimpleDocumentIndexer indexer) {
    this.indexer = indexer;
  }

  public void index(final SearchableLabReport item) {
    this.indexer.index(INDEX, SearchableLabReportSimpleDocumentConverter.convert(item));
  }

  public void index(final Collection<SearchableLabReport> items) {
    this.indexer.index(INDEX, items.stream().map(SearchableLabReportSimpleDocumentConverter::convert));
  }



}
