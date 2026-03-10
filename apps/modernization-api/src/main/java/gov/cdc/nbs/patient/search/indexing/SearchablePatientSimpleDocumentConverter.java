package gov.cdc.nbs.patient.search.indexing;

import gov.cdc.nbs.patient.search.SearchablePatient;
import gov.cdc.nbs.search.SimpleDocument;

public class SearchablePatientSimpleDocumentConverter {

  public static SimpleDocument convert(final SearchablePatient searchable) {
    String identifier = String.valueOf(searchable.identifier());
    return new SimpleDocument(identifier, searchable);
  }

  private SearchablePatientSimpleDocumentConverter() {}
}
