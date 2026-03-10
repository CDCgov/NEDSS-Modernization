package gov.cdc.nbs.event.search.investigation.indexing;

import gov.cdc.nbs.event.search.investigation.SearchableInvestigation;
import gov.cdc.nbs.search.ElasticsearchSimpleDocumentIndexer;
import gov.cdc.nbs.search.SimpleDocument;
import java.util.Collection;
import org.springframework.stereotype.Component;

@Component
public class SearchableInvestigationIndexer {

  private static final String INDEX = "investigation";
  private final ElasticsearchSimpleDocumentIndexer indexer;

  SearchableInvestigationIndexer(final ElasticsearchSimpleDocumentIndexer indexer) {
    this.indexer = indexer;
  }

  public void index(final SearchableInvestigation item) {
    this.indexer.index(INDEX, convert(item));
  }

  public void index(final Collection<SearchableInvestigation> items) {
    this.indexer.index(INDEX, items.stream().map(SearchableInvestigationIndexer::convert));
  }

  private static SimpleDocument convert(final SearchableInvestigation searchable) {
    String identifier = String.valueOf(searchable.identifier());
    return new SimpleDocument(identifier, searchable);
  }
}
