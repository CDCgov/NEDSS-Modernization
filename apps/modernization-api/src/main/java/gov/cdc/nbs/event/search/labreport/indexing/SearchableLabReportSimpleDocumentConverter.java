package gov.cdc.nbs.event.search.labreport.indexing;

import gov.cdc.nbs.event.search.labreport.SearchableLabReport;
import gov.cdc.nbs.search.SimpleDocument;

public class SearchableLabReportSimpleDocumentConverter {

  public static SimpleDocument convert(final SearchableLabReport searchable) {
    String identifier = String.valueOf(searchable.identifier());
    return new SimpleDocument(
        identifier,
        searchable
    );
  }

  private SearchableLabReportSimpleDocumentConverter() {
  }
}
