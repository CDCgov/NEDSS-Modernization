package gov.cdc.nbs.search;

import java.util.List;

public interface SearchResult<R> {
  List<R> content();

  int total();

  int page();

  int size();
}
